package service;

import model.Document;
import provider.DocumentNotFoundException;
import provider.DocumentProvider;

import java.util.concurrent.CompletionException;

/**
 * A {@link DocumentService} is a service that fetches documents by their
 * unique identifier, and returns their formatted content.
 * <p>
 * It supports multiple {@link DocumentProvider}s, which are tried until one
 * succeeds.
 */
public final class DocumentService {

    /**
     * The document providers to use.
     */
    private final DocumentProvider provider;

    /**
     * Creates a new document service that uses the given providers.
     *
     * @param providers the providers to use.
     */
    public DocumentService(DocumentProvider... providers) {
        // TODO : Handle multiple providers and retries properly ???
        this.provider = providers[0];
    }

    /**
     * Fetches a document by its unique identifier, and returns its formatted
     * content.
     *
     * @param id the unique identifier of the document.
     * @return the formatted content of the document.
     * @throws DocumentNotFoundException if the document was not found.
     */
    public String getDocument(String id) throws DocumentNotFoundException {
        try {
            return provider.fetchDocument(id)
                .thenApply(Document::formatted)
                .join();
        } catch (CompletionException ex) {
            if (ex.getCause() instanceof DocumentNotFoundException notFoundEx) {
                throw notFoundEx;
            }
            throw ex;
        }
    }
}
