package provider;

import model.Document;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.failedFuture;

/**
 * A document provider that combines multiple providers into one. If all the
 * providers fail to find a document, the returned future will throw a
 * {@link DocumentNotFoundException}.
 * <p>
 * If any of the provider succeeds, the returned future will complete
 * with the first document found.
 * <p>
 * {@link DocumentNotFoundException}s will be suppressed if the document
 * was found by another provider, or any other exception was thrown.
 */
public final class CompositeDocumentProvider implements DocumentProvider {

    /**
     * The providers to combine.
     */
    private final DocumentProvider[] providers;

    /**
     * Creates a new document provider that combines the given providers.
     *
     * @param providers the providers to combine.
     */
    public CompositeDocumentProvider(DocumentProvider... providers) {
        this.providers = Arrays.copyOf(providers, providers.length);
    }

    /**
     * Combines two exceptions, preferring the exceptions which aren't
     * a {@link DocumentNotFoundException}.
     *
     * @param e1 the first exception.
     * @param e2 the second exception.
     * @return the combined exception.
     */
    private static Throwable combineExceptions(Throwable e1, Throwable e2) {
        if (e1 instanceof DocumentNotFoundException) return e2;
        return e1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<Document> fetchDocument(String id) {
        return Arrays.stream(providers)
            .map(provider -> provider.fetchDocument(id))
            .reduce((a, b) -> a.exceptionallyCompose(e1 ->
                    b.exceptionallyCompose(e2 ->
                        failedFuture(combineExceptions(e1, e2))
                    )
                )
            )
            .orElse(failedFuture(new DocumentNotFoundException(id)));
    }
}
