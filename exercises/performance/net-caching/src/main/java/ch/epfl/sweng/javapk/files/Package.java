package ch.epfl.sweng.javapk.files;

import ch.epfl.sweng.javapk.util.Networking;
import ch.epfl.sweng.javapk.util.Terminal;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents an APK Package entry in a repository
 */
public final class Package {
    private final String name;
    private final String version;
    private final String architecture;
    private final String maintainer;
    private final String url;
    private final String description;
    private final Set<String> dependencies;
    private final PackageRepository repository;

    private Package(String name, String version, String architecture, String maintainer, String url, String description, Set<String> dependencies, PackageRepository repository) {
        this.name = name;
        this.version = version;
        this.architecture = architecture;
        this.maintainer = maintainer;
        this.url = url;
        this.description = description;
        this.dependencies = Collections.unmodifiableSet(dependencies);
        this.repository = repository;
    }

    /**
     * @return the name of the package, which doesn't contain spaces or special characters
     */
    public String getName() {
        return name;
    }

    /**
     * @return the version of the package
     */
    public String getVersion() {
        return version;
    }

    /**
     * @return the processor architecture for which this package was compiled (examples: `x86_64`, `aarch64`...)
     */
    public String getArchitecture() {
        return architecture;
    }

    /**
     * @return the name and email address of the current maintainer of this package
     */
    public String getMaintainer() {
        return maintainer;
    }

    /**
     * @return the website for this package
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the description of the package
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the dependencies of the package, i.e. a set of other packages that must be installed for this package to work
     */
    public Set<String> getDependencies() {
        return dependencies;
    }

    /**
     * @return a reference to the parent repository where this package is hosted
     */
    public PackageRepository getRepository() {
        return repository;
    }

    /**
     * Downloads a package to a given directory
     * @param targetDirectory the directory in which the package should be downloaded
     * @return a {@link File} handle to the file in which the package was downloaded
     * @throws IOException if the download fails for any reason
     */
    public File downloadPackage(File targetDirectory) throws IOException {
        String fileName = name + "-" + version + ".apk";
        URL url = new URL(repository.getBaseUrl() + "/" + fileName);
        File targetFile = new File(targetDirectory, fileName);
        Networking.downloadFile(url, targetFile, Terminal.progressBar("Downloading package " + name + " version " + version));
        System.out.println();

        return targetFile;
    }

    static class PackageBuilder {
        private String name;
        private String version;
        private String architecture;
        private String maintainer;
        private String url;
        private String description;
        private final Set<String> dependencies = new HashSet<>();

        public PackageBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public PackageBuilder setVersion(String version) {
            this.version = version;
            return this;
        }

        public PackageBuilder setArchitecture(String architecture) {
            this.architecture = architecture;
            return this;
        }

        public PackageBuilder setMaintainer(String maintainer) {
            this.maintainer = maintainer;
            return this;
        }

        public PackageBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public PackageBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public PackageBuilder setDependencies(Collection<String> dependencies) {
            this.dependencies.clear();
            this.dependencies.addAll(dependencies);
            return this;
        }

        public Package createPackage(PackageRepository rep) {
            if (name == null || version == null || architecture == null)
                throw new IllegalStateException("name, version and architecture must be specified");

            return new Package(name, version, architecture, maintainer, url, description, dependencies, rep);
        }
    }

}
