import model.*;
import model.callback.CBDistantCredentialDatabase;
import model.compfuture.CFCredentialDatabase;
import java.util.concurrent.CompletableFuture;

public class CBDistantCredentialDatabaseTestAdvanced extends FutureBasedTests {
    @Override
    protected CFCredentialDatabase getFutureBasedDatabase() {
        CBDistantCredentialDatabase callbackDB = new CBDistantCredentialDatabase();
        return new CFCredentialDatabase() {
            @Override
            public CompletableFuture<StudentUser> addUser(String userName, String userPassword, int id) {
                CompletableFuture completableFuture = new CompletableFuture<>();
                callbackDB.addUser(userName, userPassword, id, new AdapterCallback(completableFuture));
                return completableFuture;
            }
            @Override
            public CompletableFuture<StudentUser> authenticate(String userName, String userPassword) {
                CompletableFuture completableFuture = new CompletableFuture<>();
                callbackDB.authenticate(userName, userPassword, new AdapterCallback(completableFuture));
                return completableFuture;
            }
        };
    }


    private static class AdapterCallback implements CredentialDatabaseCallback {
        CompletableFuture adaptedFuture;
        public AdapterCallback(CompletableFuture adaptedFuture) {
            this.adaptedFuture = adaptedFuture;
        }
        @Override
        public void onSuccess(StudentUser user) {
            adaptedFuture.complete(user);
        }
        @Override
        public void onError(DatabaseException exception) {
            adaptedFuture.completeExceptionally(exception);
        }
    }
}