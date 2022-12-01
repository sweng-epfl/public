package provider;

import model.Document;

import java.util.concurrent.CompletableFuture;

/**
 * A document provider is a service that provides documents asynchronously.
 */
@FunctionalInterface
public interface DocumentProvider {

    /**
     * Fetches a document by its unique identifier. The returned future will
     * throw a {@link DocumentNotFoundException} if the document was not
     * found but the search was successful.
     *
     * @param id the unique identifier of the document.
     * @return a future that completes with the document.
     */
    CompletableFuture<Document> fetchDocument(String id);
}
