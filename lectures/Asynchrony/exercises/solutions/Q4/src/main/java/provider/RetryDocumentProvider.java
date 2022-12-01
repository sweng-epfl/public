package provider;

import model.Document;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;
import static java.util.concurrent.CompletableFuture.failedFuture;

/**
 * A document provider that retries requests indefinitely until a document is
 * found, or the request fails for another reason.
 */
public final class RetryDocumentProvider implements DocumentProvider {

    /**
     * The {@link DocumentProvider} which is wrapped by this provider.
     */
    private final DocumentProvider wrapped;

    /**
     * Creates a new retry document provider that wraps the given provider.
     *
     * @param wrapped the provider to wrap.
     */
    public RetryDocumentProvider(DocumentProvider wrapped) {
        this.wrapped = requireNonNull(wrapped);
    }

    /**
     * Returns true iff this exception is a {@link DocumentNotFoundException},
     * or was caused by a {@link DocumentNotFoundException}.
     */
    private boolean isDocumentNotFoundException(Throwable throwable) {
        var current = throwable;
        while (current != null && current != current.getCause()) {
            if (current instanceof DocumentNotFoundException) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<Document> fetchDocument(String id) {
        return wrapped.fetchDocument(id)
            .exceptionallyCompose(e -> {
                if (isDocumentNotFoundException(e)) {
                    return failedFuture(e);
                }
                return fetchDocument(id);
            });
    }
}
