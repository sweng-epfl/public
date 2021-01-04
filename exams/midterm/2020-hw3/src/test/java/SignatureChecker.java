import homework03.CLIImpl;
import homework03.ClientImpl;
import homework03.DatabaseImpl;
import homework03.ServerImpl;
import homework03.util.CLI;
import homework03.util.Client;
import homework03.util.Database;
import homework03.util.Server;
import org.junit.jupiter.api.Test;
import internal.reflect.utils.TypeToken;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static internal.reflect.utils.ReflectionCheckers.*;

public class SignatureChecker {
    private static final TypeToken<Consumer<String>> stringConsumerType = new TypeToken<>() {
    };
    private static final TypeToken<Consumer<Integer>> integerConsumerType = new TypeToken<>() {
    };
    private static final TypeToken<Consumer<Set<String>>> stringSetConsumerType = new TypeToken<>() {
    };
    private static final TypeToken<Consumer<Exception>> exceptionConsumerType = new TypeToken<>() {
    };

    @Test
    void checkDatabaseHasCorrectMethods() throws ReflectiveOperationException {
        assertEquals(3, Database.class.getMethods().length, "You added method(s) to the Database interface.");

        List.of(Database.class, DatabaseImpl.class).forEach(clazz -> {
            try {
                checkMethod(clazz, "put", checkArgInPositionHasType(2, stringConsumerType).and(checkArgInPositionHasType(3, exceptionConsumerType)), String.class, String.class, Consumer.class, Consumer.class);
                checkMethod(clazz, "get", checkArgInPositionHasType(1, stringSetConsumerType).and(checkArgInPositionHasType(2, exceptionConsumerType)), String.class, Consumer.class, Consumer.class);
                checkMethod(clazz, "getBookingsCount", checkArgInPositionHasType(1, integerConsumerType).and(checkArgInPositionHasType(2, exceptionConsumerType)), String.class, Consumer.class, Consumer.class);
            } catch (ReflectiveOperationException ex) {
                fail(ex);
            }
        });
    }

    @Test
    void checkServerHasCorrectMethods() throws ReflectiveOperationException {
        assertEquals(2, Server.class.getMethods().length, "You added method(s) to the Server interface.");

        List.of(Server.class, ServerImpl.class).forEach(clazz -> {
            try {
                checkMethod(clazz, "book", checkArgInPositionHasType(2, stringConsumerType).and(checkArgInPositionHasType(3, exceptionConsumerType)), String.class, String.class, Consumer.class, Consumer.class);
                checkMethod(clazz, "getBookings", checkArgInPositionHasType(1, stringSetConsumerType).and(checkArgInPositionHasType(2, exceptionConsumerType)), String.class, Consumer.class, Consumer.class);
            } catch (ReflectiveOperationException ex) {
                fail(ex);
            }
        });

        checkHasConstructor(ServerImpl.class, Database.class);
    }

    @Test
    void checkClientHasCorrectMethods() throws ReflectiveOperationException {
        assertEquals(2, Client.class.getMethods().length, "You added method(s) to the Client interface.");

        List.of(Client.class, ClientImpl.class).forEach(clazz -> {
            try {
                checkMethod(clazz, "book", checkArgInPositionHasType(1, stringConsumerType).and(checkArgInPositionHasType(2, exceptionConsumerType)), String.class, Consumer.class, Consumer.class);
                checkMethod(clazz, "getBookings", checkArgInPositionHasType(0, stringSetConsumerType).and(checkArgInPositionHasType(1, exceptionConsumerType)), Consumer.class, Consumer.class);
            } catch (ReflectiveOperationException ex) {
                fail(ex);
            }
        });

        checkHasConstructor(ClientImpl.class, String.class, Server.class);
    }

    @Test
    void checkCLIHasCorrectMethods() throws ReflectiveOperationException {
        assertEquals(1, CLI.class.getMethods().length, "You added method(s) to the CLI interface.");

        List.of(CLI.class, CLIImpl.class).forEach(clazz -> {
            try {
                checkMethod(clazz, "getQueryAndExecute");
            } catch (ReflectiveOperationException ex) {
                fail(ex);
            }
        });

        checkHasConstructor(CLIImpl.class, Client.class);
    }

}