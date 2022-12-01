package service;

import model.Document;
import org.junit.jupiter.api.Test;
import provider.DocumentNotFoundException;
import provider.DocumentProvider;

import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.concurrent.CompletableFuture.failedFuture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DocumentServiceTest {

    /**
     * Returns a {@link DocumentProvider} which contains no documents.
     */
    private static DocumentProvider notFoundProvider() {
        return id -> failedFuture(new DocumentNotFoundException(id));
    }

    /**
     * Returns a {@link DocumentProvider} which always fails.
     */
    private static DocumentProvider failedProvider(Throwable throwable) {
        return id -> failedFuture(throwable);
    }

    /**
     * Returns a {@link DocumentProvider} which always returns the given document.
     */
    private static DocumentProvider successfulProvider() {
        return id -> completedFuture(new Document(id, "title", "content"));
    }

    /**
     * The formatted body for a document returned by a successful
     * {@link DocumentProvider}.
     *
     * @see DocumentServiceTest#successfulProvider()
     */
    private static final String DOCUMENT_FORMATTED = "title\n---\ncontent";

    /**
     * Wraps the given {@link DocumentProvider} in a flaky provider, which
     * requires five attempts to succeed.
     *
     * @param provider the provider to wrap.
     * @return the flaky provider.
     */
    private static DocumentProvider flakyProvider(DocumentProvider provider) {
        var retries = new AtomicInteger();
        return id -> {
            if (retries.incrementAndGet() >= 5) {
                return provider.fetchDocument(id);
            } else {
                return failedFuture(new RuntimeException());
            }
        };
    }

    // TESTS

    @Test
    void getDocumentWithoutProvidersThrows() {
        var service = new DocumentService();
        assertThrows(DocumentNotFoundException.class, () -> service.getDocument("id"));
    }

    @Test
    void getDocumentWithOneEmptyProviderThrows() {
        var service = new DocumentService(notFoundProvider());
        assertThrows(DocumentNotFoundException.class, () -> service.getDocument("id"));
    }

    @Test
    void getDocumentWithOneSuccessfulProviderReturnsDocument() throws DocumentNotFoundException {
        var service = new DocumentService(successfulProvider());
        var document = service.getDocument("id");
        assertThat(document, is(DOCUMENT_FORMATTED));
    }

    @Test
    void getDocumentWithOneFlakySuccessfulProviderReturnsDocument() throws DocumentNotFoundException {
        var service = new DocumentService(flakyProvider(successfulProvider()));
        var document = service.getDocument("id");
        assertThat(document, is(DOCUMENT_FORMATTED));
    }

    @Test
    void getDocumentWithOneFlakyFailedProviderThrows() {
        var service = new DocumentService(flakyProvider(notFoundProvider()));
        assertThrows(DocumentNotFoundException.class, () -> service.getDocument("id"));
    }

    @Test
    void getDocumentOneSuccessfulOneFailedProviderReturnsDocument() throws DocumentNotFoundException {
        var service = new DocumentService(failedProvider(new RuntimeException()), successfulProvider());
        var document = service.getDocument("id");
        assertThat(document, is(DOCUMENT_FORMATTED));
    }

    @Test
    void getDocumentMultipleNotFoundProvidersThrows() {
        var service = new DocumentService(notFoundProvider(), notFoundProvider());
        assertThrows(DocumentNotFoundException.class, () -> service.getDocument("id"));
    }
}
