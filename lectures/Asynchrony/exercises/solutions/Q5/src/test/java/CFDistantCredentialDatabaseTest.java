import model.compfuture.CFCredentialDatabase;
import model.compfuture.CFDistantCredentialDatabase;

public class CFDistantCredentialDatabaseTest extends FutureBasedTests {
    @Override
    protected CFCredentialDatabase getFutureBasedDatabase() {
        return new CFDistantCredentialDatabase();
    }
}