# Building code with interfaces

## Common interfaces

1.
    List, Set, Queue, Vector
    - [x] Collection
    - [ ] MouseListener
    - [x] Iterable
    - [ ] Key

Collection is an interface that represents a group of objects of the same type. Data structures implementing the Collection interface must define functions to modify the group (such as `add`, `clear`, `remove`) and query it (`contains`, `isEmpty`, `size`). List, Set and Queue are subinterfaces of Collection in the Java standard library, while Vector is a concrete class implementing it.

MouseListener is an interface describing what to do when the user inputs an action with his mouse. Obviously, it doesn't apply to any child implementation.

Iterable is an interface allowing a class to support the for-each construct. An object implementing Iterable must define `hasNext()` and `next()` such that for-each can be desugared :
    
```java
    for (ObjectType obj : iterable) {
        // something
    }

    is translated into

    while(iterable.hasNext()) {
        ObjectType obj = iterable.next();
    }
```

List, Set, Queue and Vector are Iterable collections. In fact, the Java Collection interface inherits the Iterable interface.

Key is a Java interface for cryptographic keys.

2.
    Set, Path, List, a representation of an interpreted code (for instance a Python script)
    - [ ] Comparable
    - [x] Iterable
    - [ ] Future
    - [ ] Closeable

Comparable is an interface that describes total ordering among objects of the same type. There is no natural ordering between arbitrary collections such as Set or List.

Iterable obviously applies to collections (Set, List). Path is an interface describing the location of a file on a system which can be iterated on (each next object is a part of the path, for instance `/home/user/folder/file` would split into `home`, `user`, ...). One could also represent an interpreted code as an iterable sequence of instructions and write an interpreter that would ask for the next instruction and execute it until there is none left.

Future represents an asynchronous computation that may either run, succeed or fail. CompletableFuture is the Java > 8 monadic implementation of such an asynchronous task.

Closeable is an interface describing a source or destination of data that can be closed.

3.
    Any object which we want to deep copy (as opposed to shallow copy)
    - [x] Cloneable
    - [ ] Serializable
    - [ ] Appendable
    - [ ] Observable

Any class implementing Cloneable defines a `clone` method such that calling `Object.clone()` on it creates a field-for-field copy (so that the fields exist twice in different memory locations), as opposed to reference copy (also called shallow copy) that simply creates a new reference pointing to the same memory location.

Serializable is an interface that describes object which instances can be serialized into strings or byte buffers, and deserialized back into identical instances.

Appendable describes objects in which char sequences can be appended such as StringBuilder.

Observable defines objects that are able to notify other objects (called Observers) of an internal state change. Often used in the MVC model to notify a view that some data has changed (more on this in the lectures on Design Patterns).

4.
    FileInputStream, Scanner, StringWriter, a network resource accessor
    - [ ] Runnable
    - [ ] Iterable
    - [ ] Map
    - [x] Closeable

Runnables are objects which instances can be executed on a (different) thread, for instance parallelizable code.

Map describes data structures that associate a key to a value.

Closeable is the answer here since Streams (which can be infinite or from unreliable sources), Scanner (an object parsing a source of primitives into strings), StringWriter (a stream collecting strings) and distant network resources need to be closed to prevent resource leaks.

5.
    Any object that has a natural total order (making a collection of many such objects sortable). For instance numbers have a numerical order, strings have a lexographical order, ...
    - [ ] Collection
    - [ ] Serializable
    - [x] Comparable
    - [ ] Closeable

A class implementing Comparable allows collections of this object type to be automatically sorted by `Collections.sort()`. Such a class must implement `compareTo` such that 2 instances x and y yield a relation x.compareTo(y) <= 0 means x <= y.


## Writing interface contracts

### Situation 1

David and Bob can first agree on an interface that could resemble this :

```java
    public interface AuthenticationClient {
        /**
         * Checks credentials against the server
         * @return true if login is successful
         */
        public boolean login(string username, string password);

        /**
         * Registers a new user
         * @throws NetworkException if the server / network fails
         */
        public void register(string email, string username, string password) throws NetworkException;

        /**
         * Asks server for a user password reset
         * @return an URL with a token to be sent via email to the user
         */
        public string resetPassword(string username);
    }
```

This allows both of them to start working on their part independently. Bob will then implement a concrete AuthenticationClient for instance

```java
    public class AuthManager implements AuthenticationClient {
        // This class will be a Singleton
        private static AuthManager instance;
        // Prevent outside creation
        private AuthManager() {}
        // Factory to create or retrieve the instance
        public AuthenticationClient getInstance() {
            if (AuthManager.instance == null) {
                instance = new AuthManager();
            }
            return instance;
        }

        /**
            Here the concrete implementations of login,
            register and resetPassword that actually
            make requests to the server and process
            the answers
        */
        public boolean login(string username, string password) {
            // do actual server stuff
        }

        public void register(string email, string username, string password) throws NetworkException {
            // do actual server stuff
        }

        public string resetPassword(string username) {
            // do actual server stuff
        }
    }
```

But since David already has the interface beforehand, he can write his login screen such that the login button can already call the authentication client.

```java
    public class LoginScreen {
        // The login button
        private Button button = new Button("Login");
        // The credentials UI
        private TextInput username = new TextInput();
        private TextInput password = new TextInput();
        // The authentication client (TODO : initialize when concrete class is completed)
        private AuthenticationClient auth;

        public LoginScreen() {
            button.onClick(() => {
                boolean loggedIn = auth.login(username.getText(), password.getText());
                // do something if loggedIn
            })
        }
    }
```

David can already test his GUI and mock the AuthenticationClient if needed. Once Bob has effectively written the concrete AuthManager, David now simply needs to instanciate the concrete class in his code. He can now replace `private AuthenticationClient auth;` by

```java
    private AuthenticationClient auth = AuthManager.getInstance();
```

### Situation 2

Since our characters will probably need to hold some state (such as hit points and mana left, position, ...), it makes sense to model `Character` as an abstract parent class. So your implementation may look like this (your actual class definition does not really matter, this exercise is more about abstracting an interface out of an implementation idea) :

```java
public abstract class Character {
    private int health;
    private int mana;
    private Position position;

    public abstract Spell castQSpell();
    public abstract Spell castWSpell();
    public abstract Spell castESpell();
    public abstract Spell castRSpell();
    public abstract void attack(Character other);
    public abstract void doUselessDance();

    public void moveTo(Position position) {
        this.position = position;
    }
    public void takeDamage(int damage) {
        this.health -= damage;
    }
}
```

Now that Alice and Charles have agreed on the behavioral contract, Alice may implement her different characters. She could have `Leonu extends Character` and implement `castWSpell()` to be a shield for instance.

Meanwhile Charles is working on the game coherence at the network level. Charles could be working on a `GameEventsManager` that spawns the characters at the start of the game :

```java
    class GameEventsManager {
        private List<Character> redTeam;
        private List<Character> blueTeam;

        // set bases position
        Position redBase = new Position(1,1);
        Position blueBase = new Position(100, 100);
        Random generator = new Random();

        public GameEventsManager(List<Character> redTeam, List<Character> blueTeam) {
            this.redTeam = redTeam;
            this.blueTeam = blueTeam;

            spawnCharacters(redTeam, redBase);
            spawnCharacters(blueTeam, blueBase);

            // ... something else
        }

        private void spawnCharacters(List<Character> team, Position base) {
            for (Character c : team) {
                double xoffset = generator.nextDouble();
                double yoffset = generator.nextDouble();
                c.moveTo(new Position(base.x + xoffset, base.y + yoffset));
            }
        }
    }
```

## Programming to an interface

By using `List` instead of `ArrayList`, the variable declaration is of a more generic type. This ensures that you can only call methods defined by the interface as opposed to ArrayList-specific methods like `ensureCapacity()`. 
This is usually much more powerful as it allows you to change the actual implementation of the list you are using (for instance, you may find that a LinkedList is much more efficient for your particular data set later on). You can then swap your implementation as shown below. 

```java
List<String> myList = new LinkedList<>();
```

If you hadn't programmed to the interface, you would have to walk through the entire code and remove methods specific to the `ArrayList` as they may no longer be supported by the `LinkedList`.  