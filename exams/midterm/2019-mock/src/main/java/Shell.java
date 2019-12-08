// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

import java.lang.String;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.util.List;

public interface Shell {
    public String printWorkingDirectory();
    public void changeDirectory(String dirname) throws NoSuchPathException;
    public void createDirectory(String dirname) throws DirectoryAlreadyExistsException, DirectoryNotWriteableException;
    public void createFile(String filename, int sizeInBytes) throws FileAlreadyExistsException, DirectoryNotWriteableException;
    public List<String> listWorkingDirectory() throws DirectoryNotReadableException;
    public List<String> listWorkingDirectory(boolean showHidden) throws DirectoryNotReadableException;
    public int sizeInBytes(String fileOrDirName) throws NoSuchFileException;
    public void remove(String fileOrDirName) throws NoSuchFileException, DirectoryNotWriteableException, FileNotWriteableException, DirectoryNotEmptyException;
    public String getPermissions(String fileOrDirName) throws NoSuchFileException;
    public void setPermissions(String fileOrDirName, Permissions perm) throws NoSuchFileException;
}
