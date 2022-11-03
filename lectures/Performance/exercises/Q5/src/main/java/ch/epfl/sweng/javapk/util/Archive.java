package ch.epfl.sweng.javapk.util;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public final class Archive {
    /**
     * Reads a specific file from a .tar.gz archive
     * @param archive a reference to the .tar.gz archive file
     * @param filename the name of the specific file to read in the archive
     * @return a byte array with the content of the file
     * @throws IOException if the archive cannot be read or if the file doesn't exist in the archive
     */
    public static byte[] readFileFromArchive(File archive, String filename) throws IOException {
        try (TarArchiveInputStream is = new TarArchiveInputStream(new GZIPInputStream(new FileInputStream(archive)))) {
            TarArchiveEntry entry;

            while ((entry = is.getNextTarEntry()) != null) {
                if (is.canReadEntryData(entry) && entry.getName().equals(filename)) {
                    if (entry.isDirectory()) {
                        throw new IOException("File is a directory.");
                    }

                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    IOUtils.copy(is, os);

                    return os.toByteArray();
                }
            }

            throw new IOException("No such file in archive.");
        }
    }
}
