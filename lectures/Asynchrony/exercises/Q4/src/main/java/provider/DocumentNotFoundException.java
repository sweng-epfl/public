package provider;

/**
 * An exception that is thrown when a document was not found.
 */
public class DocumentNotFoundException extends Exception {

    /**
     * Creates a new {@link DocumentNotFoundException}.
     *
     * @param id the unique identifier of the document that was not found.
     */
    public DocumentNotFoundException(String id) {
        super("Document with " + id + " does not exist.");
    }
}
