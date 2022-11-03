package ch.epfl.sweng.javapk;

import ch.epfl.sweng.javapk.files.PackageRepository;
import ch.epfl.sweng.javapk.files.Package;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public final class JavaPK {
    private Configuration configuration;
    private final List<PackageRepository> repositories = new ArrayList<>();

    public static void main(String[] args) {
        new JavaPK().launch(args);
    }

    private void parseArguments(String[] args) {
        String operation = args[0].toLowerCase();
        args = Arrays.copyOfRange(args, 1, args.length);

        switch (operation) {
            case "download":
            case "i":
            case "install":
                if (args.length == 0) {
                    System.out.println("install: missing package name.");
                    help();
                } else {
                    install(args[0]);
                }
                break;
            case "s":
            case "find":
            case "search":
                if (args.length == 0) {
                        System.out.println("search: missing query.");
                    help();
                } else {
                    search(String.join(" ", args));
                }
                break;
            case "info":
            case "show":
                if (args.length == 0) {
                    System.out.println("show: missing package name.");
                    help();
                } else {
                    info(args[0]);
                }
                break;
            default:
                System.out.println("Unknown command.");
                help();
        }
    }

    public void launch(String[] args) {
        // Load configuration
        this.configuration = new Configuration();

        for (String repo : configuration.getRepositories()) {
            PackageRepository rep = new PackageRepository(configuration.getMirror(), repo, configuration.getArchitecture());
            repositories.add(rep);
        }

        if (args.length == 0) {
            // Interactive mode, to make it easier to run directly from gradle
            help();
            System.out.print("Please input your command: ");
            Scanner sc = new Scanner(System.in);
            parseArguments(sc.nextLine().trim().split(" "));
        } else {
            parseArguments(args);
        }
    }

    private void help() {
        System.out.println("JavAPK: the Java APK Package Manager");
        System.out.println("To install a package, use `javapk install <package name>`");
        System.out.println("To search for a package, use `javapk search <package name>`");
        System.out.println("To print information about a package, use `javapk info <package name>`");
        System.out.println("In interactive mode, do not put `javapk`");
    }

    private Package findPackage(String packageName) {
        for (PackageRepository repository : repositories) {
            for (Package pkg : repository.getPackageList()) {
                if (pkg.getName().equalsIgnoreCase(packageName)) {
                    return pkg;
                }
            }
        }
        return null;
    }

    private void install(String packageName) {
        Package found = findPackage(packageName);

        if (found != null) {
            try {
                File target = found.downloadPackage(new File("."));
                System.out.println("Package downloaded to " + target.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Could not download package.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Package not found: '" + packageName + "'");
        }
    }

    private void info(String packageName) {
        Package found = findPackage(packageName);

        if (found != null) {
            System.out.println("Package information for " + packageName + ":");
            System.out.println("Version: " + found.getVersion());
            System.out.println("Description: " + found.getDescription());
            System.out.println("Maintainer: " + found.getMaintainer());
            System.out.println("Repository: " + found.getRepository());
            System.out.println("Dependencies: " + found.getDependencies());
            System.out.println("Website: " + found.getUrl());
        } else {
            System.out.println("Package not found: '" + packageName + "'");
        }
    }

    private void search(String query) {
        List<Package> match = new ArrayList<>();

        for (PackageRepository repository : repositories) {
            for (Package pkg : repository.getPackageList()) {
                if (pkg.getName().contains(query)) {
                    match.add(pkg);
                }
            }
        }

        System.out.println("Packages found for query " + query + ": ");

        for (Package pkg : match) {
            System.out.println("[" + pkg.getRepository().getRepository() + "] " + pkg.getName() + " " + pkg.getVersion() + ": " + pkg.getDescription());
        }
    }

}

