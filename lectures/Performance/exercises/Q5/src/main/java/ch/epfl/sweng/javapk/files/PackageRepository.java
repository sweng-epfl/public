package ch.epfl.sweng.javapk.files;

import ch.epfl.sweng.javapk.util.Terminal;
import ch.epfl.sweng.javapk.util.Archive;
import ch.epfl.sweng.javapk.util.Networking;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A repository of multiple APK packages.
 */
public class PackageRepository {
    private final String baseUrl;
    private final String repository;

    /**
     * Construct this PackageRepository
     * @param mirrorUrl the URL of the mirror of this repository. It can contain `%repository%` as a placeholder for the repository name, and `%architecture%` as a placeholder for the processor architecture used
     * @param repository the name of the repository (for example: `core`, `community`...)
     * @param architecture the processor architecture to use (for example: `x86_64`, `aarch64`, ...)
     */
    public PackageRepository(String mirrorUrl, String repository, String architecture) {
        this.repository = repository;
        this.baseUrl = mirrorUrl.replace("%repository%", repository).replace("%architecture%", architecture);
    }

    /**
     * Downloads and parses the package list from the repository
     */
    private List<Package> downloadPackageList() throws IOException {
        URL url = new URL(baseUrl + "/APKINDEX.tar.gz");
        File f = new File("." + repository + "-tmp.tar.gz");

        System.out.print("Synchronization with repository " + repository + "...");
        Networking.downloadFile(url, f, Terminal.progressBar("Synchronizing packages in repository " + repository));
        System.out.println();

        // Read the list and delete the file
        List<Package> packages = parsePackageList(f);
        f.delete();

        return packages;
    }

    /**
     * Parses a line from the APKINDEX index file
     */
    private void parsePackageInList(String line, Package.PackageBuilder builder) {
        String[] parts = line.split(":");

        if (parts.length < 2) {
            System.out.println("Ignoring malformed line in package list: `" + line + "`");
            return;
        }

        String field = parts[0];
        String value = String.join(":", Arrays.copyOfRange(parts, 1, parts.length));

        switch (field) {
            case "A":
                builder.setArchitecture(value);
                break;
            case "P":
                builder.setName(value);
                break;
            case "T":
                builder.setDescription(value);
                break;
            case "U":
                builder.setUrl(value);
                break;
            case "V":
                builder.setVersion(value);
                break;
            case "m":
                builder.setMaintainer(value);
                break;
            case "D":
            case "r":
                builder.setDependencies(List.of(value.split(" ")));
                break;
            default:
                // Do nothing for other fields, simply ignore them
        }
    }

    /**
     * Parses a APKINDEX.tar.gz file as a list of packages
     */
    private List<Package> parsePackageList(File file) throws IOException {
        byte[] data = Archive.readFileFromArchive(file, "APKINDEX");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data)));

        List<Package> packages = new ArrayList<>();
        String line;
        Package.PackageBuilder builder = null;

        while ((line = reader.readLine()) != null) {
            if (line.isBlank()) {
                // Package separator
                try {
                    if (builder != null) {
                        packages.add(builder.createPackage(this));
                    }
                } catch (IllegalStateException ex) {
                    System.out.println("Error while parsing package list: missing fields in package.");
                }
            } else {
                if (builder == null)
                    builder = new Package.PackageBuilder();

                parsePackageInList(line, builder);
            }
        }

        try {
            if (builder != null) {
                packages.add(builder.createPackage(this));
            }
        } catch (IllegalStateException ex) {
            System.out.println("Error while parsing package list: missing fields in package.");
        }

        reader.close();
        return packages;
    }

    /**
     * @return the name of this repository (example: `core`, `community`, ...)
     */
    public String getRepository() {
        return repository;
    }

    /**
     * @return the URL prefix to download a file in this repository
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Get the list of packages in the repository
     * @return the list of all packages in the repository
     */
    public List<Package> getPackageList() {
        try {
            return downloadPackageList();
        } catch (IOException e) {
            System.out.println("Error while downloading package list: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
}
