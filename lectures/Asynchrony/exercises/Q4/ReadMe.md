# Reliable document providers

You've just joined SwengDocs, a company that stores documents for its
customers in the cloud, and you've been assigned to the team that is responsible
for the document storage service.

The current SwengDocs architecture is quite simple: each document is stored
on one or multiple servers, and each document is identified by a unique ID.

The `DocumentService` is responsible for retrieving documents. However,
currently, it is not very reliable: if an error occurs while retrieving a
document, the whole operation fails. This is not acceptable, as the service
might be temporarily unavailable, or the document might be available on a
different server.

Your task is to improve the reliability of the `DocumentService` by implementing
two features:

1. **Retrying**: if an error occurs while retrieving a document, the service
   should retry the operation. Retries should stop only when the operation
   succeeds, or when the document can not be found (i.e., a
   `DocumentNotFoundException` is thrown by the `DocumentProvider`).
2. **Multiple providers**: the service should be able to retrieve documents
   from multiple providers, succeeding if any provider has the document. If
   the document was **not found** on all providers, the service should throw
   a `DocumentNotFoundException`. Otherwise, the service should retry the
   operation on the next provider if any provider fails with another error.

The `DocumentService` is already implemented, and you can find it in the
`DocumentService.java` file. You can also find the `DocumentProvider` interface
in the `DocumentProvider.java` file.

Your task is to perform minimal changes to the `DocumentService` class, and
implement the two features in the `DocumentService` class. You can add new
classes and methods as you see fit, but you should not change the signature
of the existing methods.

Additionally, a test suite is provided in the `DocumentServiceTest.java`
file, to help you verify your implementation and make sure it works as expected.
