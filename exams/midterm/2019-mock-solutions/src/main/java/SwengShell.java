// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CANNOT edit the names, parameters or return types of existing methods/constructors
// You CANNOT add or remove checked exceptions to existing methods/constructors
// You CANNOT delete existing methods/constructors
// You CAN change the bodies of existing methods/constructors
// You CAN add new methods/constructors
// You CAN add interface implementations
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SwengShell implements Shell {
    private FileSystemNode rootFs;
    private FileSystemNode currentFs;
    private String currentWorkingDirectory;

    public SwengShell(FileSystemNode root) {
        rootFs = root;
        currentFs = rootFs;
        currentWorkingDirectory = "/";
    }

    @Override
    public String printWorkingDirectory() {
        return currentWorkingDirectory;
    }

    @Override
    public void changeDirectory(String dirpath) throws NoSuchPathException {
        if (isAbsolutePath(dirpath)) {
            changeToRootDirectory();

            // Eliminate leading "/" so splitting by "/" doesn't give an empty token
            // as its first element.
            dirpath = dirpath.substring(1);

            // The user has asked for the root directory alone. We need to do this
            // here as the String.split() method in Java will produce an array
            // containing one element "" if it is provided with an empty string
            // (instead of producing an empty array).
            if (dirpath.length() == 0) {
                return;
            }
        }

        String[] pieces = dirpath.split("/");
        for (String piece : pieces) {
            changeRelativeDirectorySingle(piece);
        }
    }

    @Override
    public void createDirectory(String dirname) throws DirectoryAlreadyExistsException, DirectoryNotWriteableException {
        if (!isBasename(dirname)) {
            throw new IllegalArgumentException();
        }

        // Bug 5:
        // bug => currentFs.addDirectory(dirname);
        // fix =>
        //        if (isCurrentDirectoryWritable()) {
        //            currentFs.addDirectory(dirname);
        //        } else {
        //            throw new DirectoryNotWriteableException(currentWorkingDirectory);
        //        }
        if (isCurrentDirectoryWritable()) {
            currentFs.addDirectory(dirname);
        } else {
            throw new DirectoryNotWriteableException(currentWorkingDirectory);
        }
    }

    @Override
    public void createFile(String filename, int sizeInBytes) throws FileAlreadyExistsException, DirectoryNotWriteableException {
        if (!isBasename(filename)) {
            throw new IllegalArgumentException();
        }

        if (isCurrentDirectoryWritable()) {
            currentFs.addFile(filename, sizeInBytes);
        } else {
            throw new DirectoryNotWriteableException(currentWorkingDirectory);
        }
    }

    @Override
    public List<String> listWorkingDirectory() throws DirectoryNotReadableException {
        return listWorkingDirectory(false);
    }

    @Override
    public List<String> listWorkingDirectory(boolean showHidden) throws DirectoryNotReadableException {
        // Bug 6:
        // bug => return currentFs.getChildrenNames().stream().filter(hiddenMatches).collect(Collectors.toList());
        // fix =>
        //        if (isCurrentDirectoryReadable()) {
        //            Predicate<String> hiddenMatches = s -> !isHidden(s) || showHidden;
        //            return currentFs.getChildrenNames().stream().filter(hiddenMatches).collect(Collectors.toList());
        //        } else {
        //            throw new DirectoryNotReadableException(currentWorkingDirectory);
        //        }
        if (isCurrentDirectoryReadable()) {
            Predicate<String> hiddenMatches = s -> !isHidden(s) || showHidden;
            return currentFs.getChildrenNames().stream().filter(hiddenMatches).collect(Collectors.toList());
        } else {
            throw new DirectoryNotReadableException(currentWorkingDirectory);
        }
    }

    @Override
    public int sizeInBytes(String fileOrDirName) throws NoSuchFileException {
        if (!isBasename(fileOrDirName)) {
            throw new IllegalArgumentException();
        }

        // Bug 3:
        // bug => currentFs.getChild(fileOrDirName).getSizeInBytes() - 1
        // fix => currentFs.getChild(fileOrDirName).getSizeInBytes()
        return currentFs.getChild(fileOrDirName).getSizeInBytes();
    }

    @Override
    public void remove(String fileOrDirName) throws NoSuchFileException, DirectoryNotWriteableException, FileNotWriteableException, DirectoryNotEmptyException {
        if (!isBasename(fileOrDirName)) {
            throw new IllegalArgumentException();
        }
        String path = currentWorkingDirectory + "/" + fileOrDirName;

        FileSystemNode c = currentFs.getChild(fileOrDirName);
        boolean isWriteable = c.getPermissions().matches(".w");
        boolean isFile = c.isFile();
        boolean isEmptyDirectory = !isFile && c.getChildrenNames().size() == 0;

        if (isFile && !isWriteable) throw new FileNotWriteableException(path);
        if (!isFile && !isWriteable) throw new DirectoryNotWriteableException(path);

        // Bug 2:
        // bug => if (!isFile && isWriteable && isEmptyDirectory)
        // fix => if (!isFile && isWriteable && !isEmptyDirectory)
        if (!isFile && isWriteable && !isEmptyDirectory) throw new DirectoryNotEmptyException(path);

        if (isFile || isEmptyDirectory) {
            c.getParent().removeChild(fileOrDirName);
        }
    }

    @Override
    public String getPermissions(String fileOrDirName) throws NoSuchFileException {
        if (!isBasename(fileOrDirName)) {
            throw new IllegalArgumentException();
        }

        return currentFs.getChild(fileOrDirName).getPermissions();
    }

    @Override
    public void setPermissions(String fileOrDirName, Permissions perm) throws NoSuchFileException {
        if (!isBasename(fileOrDirName)) {
            throw new IllegalArgumentException();
        }

        currentFs.getChild(fileOrDirName).setPermissions(perm);
    }

    private boolean isAbsolutePath(String path) {
        return path != null && path.charAt(0) == '/';
    }

    private boolean isBasename(String path) {
        // A basename cannot contain any path separators
        return path != null && (path.indexOf('/') == -1);
    }

    private String getBasePath(String absolutePath) {
        // Strip leading "/" and base path (since we want to stop at parent node)
        int startIdx = 0;
        int endIdx = absolutePath.lastIndexOf('/');
        return absolutePath.substring(startIdx, endIdx);
    }

    private String removeDuplicatePathSeparators(String cwd) {
        return cwd.replaceAll("(/)(\\1)+", "$1");
    }

    private void changeRelativeDirectorySingle(String filename) throws NoSuchPathException {
        if (filename.equals("..")) {
            // The root node has no parent node, so it loops back onto itself
            if (currentFs != rootFs) {
                currentFs = currentFs.getParent();
                // Remove 1 path entry as we are going up.
                currentWorkingDirectory = removeDuplicatePathSeparators(getBasePath(currentWorkingDirectory));
            }
        } else {
            String newCwd = removeDuplicatePathSeparators(currentWorkingDirectory + "/" + filename);

            FileSystemNode child;
            try {
                child = currentFs.getChild(filename);
            } catch (NoSuchFileException e) {
                throw new NoSuchPathException(newCwd);
            }

            // Cannot change directory to a file.
            if (child.isFile()) throw new NoSuchPathException(newCwd);

            currentFs = child;
            currentWorkingDirectory = newCwd;
        }
    }

    private void changeToRootDirectory() {
        currentFs = rootFs;
        currentWorkingDirectory = "/";
    }

    private boolean isHidden(String filename) {
        // Bug 1:
        // bug => filename.endsWith(".")
        // fix => filename.startsWith(".")
        return filename.startsWith(".");
    }

    private boolean isCurrentDirectoryWritable() {
        return currentFs.getPermissions().matches(".w");
    }

    private boolean isCurrentDirectoryReadable() {
        return currentFs.getPermissions().matches("r.");
    }
}
