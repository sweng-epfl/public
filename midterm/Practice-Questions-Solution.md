# Software Engineering

## Midterm Exam Solutions

# Part 1: Practice [60 points]

In this part of the exam we ask that you write real code. As is often the case in software engineering, the problem itself is fairly easy to reason about, and what really matters is that your code employs solid software engineering principles:

- Write correct and rock-solid code;
- Write tests for your code, aiming for over 90% line coverage; and
- Follow the SwEng coding principles, write readable and concise code, and use comments judiciously.

Do not modify the public interface of the src code that is given to you, not even to add checked exceptions. We will use many automated tests when grading, and if we cannot build and run your code, you will receive 0 points. You can add public methods to `...Test` classes if you need to do so for testing purposes.

You can run locally `./gradlew test` and `./gradlew jacoco`, after which you can find coverage results in `midterm/build/reports/jacoco/test/html/index.html`. You are free to use an editor or IDE of your choice, as long as the code and tests build using `./gradlew build`. On Windows, instead of `./gradlew` use `gradlew.bat`.

Please remember that we will only grade whatever is in your master branch.

We provide you with continuous integration on Jenkins during the exam ([https://jenkins.epfl.ch/sweng](https://jenkins.epfl.ch/sweng)) so you can easily check that your master branch builds and runs as expected. Do not issue a pull request, Jenkins will automatically retrieve the code from your master branch periodically. If you forget to commit a file, or if you push the wrong version or a version that breaks your previously working version, you may lose all points, so please check Jenkins after every push.

## Part 1.1: Chess Pieces [20 points]

The [code we provide you](midterm) consists of classes that implement movement rules for several chess pieces. The rules for how each piece moves on the chessboard are described in comments for each class, and are shown graphically [here](pieces_wiki.png). The code we provide assumes that column `a` is to the white player's left and row `1` is closest to the white player.

The provided code has the following shortcomings:

1. Several tests are missing, and thus coverage is poor;
2. There are two bugs in the code, which we will ask you to find and correct; and
3. There is no code for the queen chess piece.

### Part 1.1.1 [6 points]
Use the JaCoCo coverage plugin in Jenkins to find the code that is not covered by the provided tests. Write tests to cover this code, aiming to test the expected functionality of all classes. What we mean by _expected_ is documented for each subclass of `Piece`, and follows directly from the movement rules of chess pieces.

Besides functional testing, aim for a minimum of 90% line coverage of each class you implement and/or test.

To write your tests, follow the example in `PawnTests`, where much of the test functionality is implemented by the abstract class `PieceTest`. To get the maximum grade, you must use the `PieceTest` abstract class in the same way that `PawnTests` does. A working solution that does not do so will get a 20% point reduction for the tests.

Your tests must run by simply typing `./gradlew test` on the command line.

__Solution:__ The problem is that there are no tests for `Rook`, so the solution consists of writing [RookTests.java](midterm/src/test/java/ch/epfl/sweng/tests/RookTests.java). The main idea behind the design of the test is to check if the rook can move forward/backward on the horizontal and up/down on the vertical, and then check if moving diagonally throws an exception.

__Grading:__ 

 - _Implementation of tests_ __(3 pts)__: Testing of `Rook.isPieceMovementValid()` as follows:
   - 1 pt for testing one horizontal move of ≥ 1 step
   - 1 pt for testing one vertical move of ≥ 1 step
   - 1 pt for testing one illegal move
 - _Coverage_ __(3 pts)__: Coverage achieved by the tests your wrote, as follows:
   - 1 pt for coverage in the [40%, 70%) interval
   - 2 pts for coverage in the [70% - 90%) interval
   - 3 pts for coverage ≥ 90%
 - _Test code quality_ __(max -3 pts)__: We penalized mistakes including the following:
    - the test class does not extend `PieceTests<Rook>` (-20% of all points)
    - tests that exercise the code but do not actually check anything
    - unnecessary copy & paste of code, left-over comments from copy & paste
    - poor naming of variables and/or classes (including not using camelCase)
    - `Piece.equals()` not used when needed
    - unusual indentation and/or extra/missing whitespace

   Some issues that we did not penalize yet still constitute bad practice:

    - commenting out parts of a test to avoid compiling them
    - trying to perform multiple `illegalMoveTest` calls sequentially, even though the first one would cause the test to stop anyway
    - using `assertTrue` instead of `assertEquals` when testing equality
    - duplication of tests


### Part 1.1.2 [4 points]
There are two bugs in the logic of the code. We ask that you find and fix them both, by modifying a single line of code for each bug. One of the bugs already causes test failure, and you would naturally discover the second one after writing the missing tests. You are free to fix the first bug before or after solving Part 1.1.1.

__Solution:__ The first bug is made apparent by the tests we provided; to fix it, remove the stray `!` [here](midterm/src/main/java/ch/epfl/sweng/Piece.java#L48). The other bug is made apparent by `Rook` tests, and is fixed [here](midterm/src/main/java/ch/epfl/sweng/Rook.java#L25).

__Grading:__  For each of the two bugs, we awarded

  - 1 pt for fixing it (just finding it is not enough)
  - 1 pt for fixing it by only modifying a single line of code

### Part 1.1.3 [10 points]
Write a class `Queen` that enables the queen chess piece to move any number of steps, both forward and backward, along any of the four directions (vertical, horizontal, or one of the two diagonals).  Write tests for the new class in a way that satisfies the requirements of Part 1.1.1.

_Tip_: One could say that the queen piece's movements are a combination of two other pieces' movements.

__Solution:__ The main component of the solution is [Queen.java](midterm/src/main/java/ch/epfl/sweng/Queen.java).  We extend `Piece` to get all the logic involved in correctly handling `moveTo`. We fill out all the boilerplate required for each `Piece`: the public constructor just calls the `super` constructor and passes the arguments, the copy constructor calls the `super` constructor with its own data members as arguments, and `copy()` returns a copy of itself. The novel part is `isPieceMovementValid`, where we follow the tip given above and simply check if the move would be allowed for either `Bishop` or `Rook`. Since each of them contributes a single line of code, and chess rules are unlikely to change in the future, it doesn't make much sense to do a lot of code reuse here, though such solutions are fine too.

For [tests](midterm/src/test/java/ch/epfl/sweng/tests/QueenTests.java#L20), we set up the regular boilerplate to use the "automatic" tests coming from `PieceTests<P extends Piece>`,
and then check if various moves across the diagonals, horizontal, and vertical are correctly carried out. We use a nested loop to quickly go through many test cases.

__Grading:__ 

 - _Implementation_ __(5 pts)__:
   - 4 pts for correctness of `Queen.isPieceMovementValid()`
     - 2 pts for correctness of moves:
         - `Queen` can move horizontally
         - `Queen` can move vertically
         - `Queen` can move diagonally
     - 2 pts for properly excluding invalid moves
   - 1 pt for correctness of the rest of the class
 - _Tests_ __(5 pts)__:
   - 4 pts for testing `Queen.isPieceMovementValid()`
     - 1 pt for one horizontal move of ≥ 1 step
     - 1 pt for one vertical move of ≥ 1 step
     - 1 pt for one diagonal move of ≥ 1 step
     - 1 pt for one illegal move
   - 1 pt for acheving statement coverage ≥ 90% on `Queen`
 - _Code quality_ __(max -5 pts)__: We penalized mistakes such as:
   - the test class does not extend `PieceTests<Queen>` (-20% of all points)
   - tests that exercise the code but do not actually check anything
   - poor/inconsistent naming choices for files (e.g., `QueenTest` instead of `QueenTests`)
   - unusual indentation and/or extra/missing whitespace, poor variable name choices
   - variables defined and instantiated far away from where they are used

   Some issues that we did not penalize yet still constitute bad practice:

   - implementing `Queen` by composing a `Rook` and a `Bishop` is OK but more complex than necessary
   - tests that explicitly make multiple invocations to test a sequence of moves, instead of using a loop
   - extraneous methods that test functionality already tested elsewhere


## Part 1.2: Promoting a Piece (Reloaded) [40 points]

The rules of chess say that, once your pawn reaches your opponent's side of the chessboard, you can replace it with any piece you choose. In this part we ask you to implement a new kind of promotion that turns your pawn into a "superpiece", which has the following properties:

- during the owning player's turn, it can make two moves instead of one
- it can move like a queen, rook, bishop, or knight (whichever you choose) but not a pawn or king
- it can be born only through promotion

### Part 1.2.1 [15 points]

Implement the superpiece behavior in a class called `Superpiece`, with the following specifics:

- A `Superpiece` is a `Piece`, so it can merely `moveTo` a location if the player decides not to use its special two-moves ability.
- On top of that, `Superpiece` also provides two new overloads of `moveTo`⏤ `moveTo(Position intermediateDestination, Position finalDestination)` and `moveTo(char intermediateColumn, int intermediateRow, char finalColumn, int finalRow)`⏤ that extend the respective semantics of `moveTo(Position destination)` and `moveTo(char column, int row)` to double moves.
- Both double-move methods should throw an exception if either the intermediate or the final move is erroneous, and the position of the piece, along with any other properties of the object, should remain exactly as they were immediately prior to the invocation of the method. This property is called [strong exception guarantee](https://en.wikipedia.org/wiki/Exception_guarantees). It's OK for the second move to return the superpiece to its original position.

For full credit, implement `Superpiece` using the Decorator design pattern, making an instance of `Superpiece` be a decorated `Queen`, `Rook`, `Bishop`, or `Knight`. Other solutions that work are OK too, but will get a 50% reduction in the number of points.  

If your solution is non-obvious, please explain it in a comment at the top of `Superpiece.java`.

__Solution:__ We implement a sample solution in [Superpiece.java](midterm/src/main/java/ch/epfl/sweng/Superpiece.java). The general idea is to

1. Extend `Piece` to inherit all the logic driving `moveTo(Position)` and `moveTo(char, int)`
2. Make `Superpiece` a decorator for a `Piece` instance of the right type. This way, we can check if moves are correct by first trying them out on the decorated piece (in our solution, it's the class member called `underlyingPiece`). Then, if correct, move `Superpiece` itself.

Thus, our solution decorates the [`underlyingPiece`](midterm/src/main/java/ch/epfl/sweng/Superpiece.java#L8) field.  A move [is valid](midterm/src/main/java/ch/epfl/sweng/Superpiece.java#L42) if it's valid for the decorated underlying piece. This implements the single-move variants of `moveTo`.

If we were to implement the double-move `moveTo` without concern for the strong exception guarantee, we could have something like this:

```java
public void moveTo(Position firstPosition, Position secondPosition)
    throws InvalidMoveException, InvalidPositionException
{
    moveTo(firstPosition);
    moveTo(secondPosition);
}
```

The good part is that `moveTo` uses `isPieceMovementValid`, which in turn asks the underlying piece whether this is a correct move, so the result is correct with respect to whatever underlying piece is being decorated. The bad part is that we do not get a strong exception guarantee: Suppose the `Superpiece` is in some initial position, moving to `firstPosition` is correct, but moving to `secondPosition` is not. In this case, the superpiece ends up at `firstPosition` instead of its initial position.

The fix is to create a copy of the underlying decorated piece, and treat it as a "guinea pig" for testing purposes. If anything goes wrong, it's the copy that got moved around, while the `Superpiece` remained at its original position. This is explained with comments in the [method itself](midterm/src/main/java/ch/epfl/sweng/Superpiece.java#L54).

Finally, `Piece` implements the `equals(Object)` method, which compares the `position` and `color` members. Since our decorator introduces a new field, we override `equals` to also take this field into account (otherwise, a promoted white knight at A4 will be considered equal to a promoted white queen at A4).  A good shortcut for implementing `equals` is to go in Android Studio to _code -> generate -> equals and hashcode_.

You are free to implement the constructors for `Superpiece` in any way you wish. In our solution, we wanted to ease testing, so our `Superpiece` [is created](midterm/src/main/java/ch/epfl/sweng/Superpiece.java#L19) only if the arguments are valid.  It is also ok to implement a `public Superpiece(Piece promoted, PieceType promotedTo)` constructor that extracts both the color and position from `promoted`.

__Grading:__ 

 - _Implementation_ __(15 pts)__
   - 3 pts if `Superpiece` extends Piece and implements the single `moveTo()` method
   - 5.5 pts if `Superpiece` implements the double `moveTo()` method that takes positions as arguments
     - 1.5 pt for basic implementation
     - 1 pt if it throws an exception when the intermediate move is illegal
     - 1 pt if it throws an exception when the final move is illegal
     - 2 pts if it preserves the pre-`moveTo()` state whenever a move fails
   - 5.5 pts if `Superpiece` implements the double `moveTo()` method that takes columns and rows as arguments
     - 1.5 pt for basic implementation
     - 1 pt if it throws an exception when the intermediate move is illegal
     - 1 pt if it throws an exception when the final move is illegal
     - 2 pts if it preserves the pre-`moveTo()` state whenever a move fails
   - 1 pt if `Superpiece.equals()` is implemented
 - _Tests:_ graded for all of 1.2 as a whole (see 1.2.3)
 - _Code quality_ __(max -8 pts)__: We penalized mistakes that include the following:
   - `Superpiece` not using the Decorator pattern (-50% of all points)
   - non-obvious solution is not properly documented 
   - copy-pasted `moveTo` logic from `Piece` to `Superpiece`
   - using an abstract class for `Superpiece` instead of a Decorator
   - `Superpiece<PieceType>` shadows the original `PieceType`
   - `Superpiece` extends `Pawn`, `Queen`, etc.
   - unusual indentation, and/or extra/missing whitespace, poor variable name choices
   - ignoring the original exception stack trace when rethrowing an exception

   Some issues that we did not penalize yet still constitute bad practice:

    - implementing `moveTo(Position, Position)` in terms of `moveTo(char, int, char, int)`
    - unnecessary copies of pieces
    - strange inheritance schemes
	- making the wrong methods public
    - public static factory method `fromPiece` available from `Superpiece`

### Part 1.2.2 [5 points]

Modify the `Pawn` class to provide an additional method `Superpiece promote(PieceType targetPiece)` where `targetPiece` can take values `QUEEN`, `ROOK`, `BISHOP`, or `KNIGHT`. The code should check, based on the `color` and `position` fields, whether the pawn is eligible for such a promotion. If not, it should throw an `InvalidMoveException`. (_Note:_ This is the only case where we allow you to modify the public interface of a class we have provided: modify `Pawn` and `PawnTests` to include the `promote` method.)

__Solution:__ The new method needs to do two things:

1. Check if the `Pawn` is at the right position on the board.
2. Check if the argument `targetPiece` is correct (and not, for instance, a `KING`). 

We [check the former](midterm/src/main/java/ch/epfl/sweng/Pawn.java#L34) by looking at `getColor()` and then comparing our vertical position either to the start of the board or its end.  If the check fails, we throw an exception, otherwise we proceed to return a `Superpiece`. We check the correctness of `targetPiece` inside the constructor.

For [testing](midterm/src/test/java/ch/epfl/sweng/tests/PawnTests.java#L45), we need to check if the `Pawn` can be promoted only at the right position, and whether it checks the validity of the `PieceType` argument. We achieve this by setting up test cases for:

1. Correct promotion (for each color)
2. Invalid promotion (for each color)
3. Exception is thrown for an invalid `PieceType`

__Grading:__ 

 - _Implementation_ __(5 pts)__:
    - 1 pt for a basic implementation of the `promote()` method
    - 2 pts for checking the piece type of the `Superpiece` in `promote()`
    - 2 pts for correctly throwing `InvalidMoveException` from `promote()`
 - _Tests:_ graded for all of 1.2 as a whole (see 1.2.3)
 - _Code quality:_ not graded for this part

### Part 1.2.3 [20 points]

Write tests for `Superpiece`, both for its basic behavior coming from `Piece` and the behavior resulting from being a superpiece.  Feel free to write a single class or multiple ones, to reuse `PieceTest` or to write them from scratch, etc.  

Your tests must run by simply typing `./gradlew test` on the command line.

__Solution:__ The bulk of testing is for the various overloads of `moveTo`. We [first](midterm/src/test/java/ch/epfl/sweng/tests/SuperpieceTests.java#L33) check if the basic `moveTo(char, int)` works, [then](midterm/src/test/java/ch/epfl/sweng/tests/SuperpieceTests.java#L45) `moveTo(Position)`, [continue](midterm/src/test/java/ch/epfl/sweng/tests/SuperpieceTests.java#L51) with `moveTo(char, int, char, int)` and [end](midterm/src/test/java/ch/epfl/sweng/tests/SuperpieceTests.java#L60) with `moveTo(Position, Position)`, always asserting that the end position is correct.

We then set up a simple [test case](midterm/src/test/java/ch/epfl/sweng/tests/SuperpieceTests.java#L66) for an invalid movement in `moveTo(char, int)` and check if `moveTo(Position, Position)` works as well: first if the error is made in the [first step](midterm/src/test/java/ch/epfl/sweng/tests/SuperpieceTests.java#L76), then in the [second](midterm/src/test/java/ch/epfl/sweng/tests/SuperpieceTests.java#L88), always asserting that the position of the piece has not changed. Finally, we [check](midterm/src/test/java/ch/epfl/sweng/tests/SuperpieceTests.java#L101) if this also holds for `moveTo(char, int, char, int)`.

The tests following [this line](midterm/src/test/java/ch/epfl/sweng/tests/SuperpieceTests.java#L129) are not strictly necessary; however, being able to test the creation of `Superpiece` without having to first create a `Pawn` saves time.

__Grading:__ 

 - _Implementation of tests_ __(10 pts)__
    - 4 pts for testing the double `moveTo()` with positions as arguments
        - 1 pt if testing for legal double move
        - 1 pt if testing for exception thrown on illegal intermediate move
        - 1 pt if testing for exception thrown on illegal final move
        - 1 pt if testing for the preservation of state when a move fails
    - 4 pts for testing the double `moveTo()` with columns and rows as arguments
        - 1 pt if testing for legal double move
        - 1 pt if testing for exception thrown on illegal intermediate move
        - 1 pt if testing for exception thrown on illegal final move
        - 1 pt if testing for the preservation of state when a move fails
    - 2 pts for testing the single `moveTo()`

 - _Coverage_ __(10 pts)__:  Coverage achieved by the tests your wrote, as follows:
    - 0 pts for coverage < 10%
    - 2 pts for coverage in the interval [10%, 35%)
    - 4 pts for coverage in the interval [35%, 70%)
    - 6 pts for coverage in the interval [70%, 90%)
    - 10 pts for coverage ≥ 90%

 - _Test code quality_ __(max -10 pts)__: We penalized mistakes including the following:
    - tests that exercise the code but do not actually test anything
    - unnecessary copy & paste of code
    - left-over comments from copy & paste
    - poor encapsulation (e.g., comparing Pieces by fields instead of using `equals()`)
    - ignoring the original exception stack trace when rethrowing an exception
    - poor inheritance choices (e.g., `Superpiece` extends `Pawn`)
    - poor naming of variables and/or classes (including not using camelCase)
    - unnecessary use of generic types
    - unusual indentation, and/or extra/missing whitespace

## Grading

Your grade on this Practice part of the exam has three components: implementation correctness, testing, and code quality. We will assign 50% of the points to implementation and 50% to testing, and then subtract points for code that does not obey the SwEng quality standards we taught you. We have automated tests to check the correctness of your code, and these tests will automatically compute the number of points you get in this category; it is therefore crucial that your code compiles and runs properly. For coverage, keep in mind that, the more code you write, the more tests you need to produce in order to get good coverage. We will assess the quality of your code both using automated tools and manually.
