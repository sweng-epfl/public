# Software Engineering

## Midterm Exam

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

### Part 1.1.1
Use the JaCoCo coverage plugin in Jenkins to find the code that is not covered by the provided tests. Write tests to cover this code, aiming to test the expected functionality of all classes. What we mean by _expected_ is documented for each subclass of `Piece`, and follows directly from the movement rules of chess pieces.

Besides functional testing, aim for a minimum of 90% line coverage of each class you implement and/or test.

To write your tests, follow the example in `PawnTests`, where much of the test functionality is implemented by the abstract class `PieceTest`. To get the maximum grade, you must use the `PieceTest` abstract class in the same way that `PawnTests` does. A working solution that does not do so will get a 20% point reduction for the tests.

Your tests must run by simply typing `./gradlew test` on the command line.

### Part 1.1.2
There are two bugs in the logic of the code. We ask that you find and fix them both, by modifying a single line of code for each bug. One of the bugs already causes test failure, and you would naturally discover the second one after writing the missing tests. You are free to fix the first bug before or after solving Part 1.1.1.

### Part 1.1.3
Write a class `Queen` that enables the queen chess piece to move any number of steps, both forward and backward, along any of the four directions (vertical, horizontal, or one of the two diagonals).  Write tests for the new class in a way that satisfies the requirements of Part 1.1.1.

_Tip_: One could say that the queen piece's movements are a combination of two other pieces' movements.

## Part 1.2: Promoting a Piece (Reloaded) [40 points]

The rules of chess say that, once your pawn reaches your opponent's side of the chessboard, you can replace it with any piece you choose. In this part we ask you to implement a new kind of promotion that turns your pawn into a "superpiece", which has the following properties:

- during the owning player's turn, it can make two moves instead of one
- it can move like a queen, rook, bishop, or knight (whichever you choose) but not a pawn or king
- it can be born only through promotion

### Part 1.2.1

Implement the superpiece behavior in a class called `Superpiece`, with the following specifics:

- A `Superpiece` is a `Piece`, so it can merely `moveTo` a location if the player decides not to use its special two-moves ability.
- On top of that, `Superpiece` also provides two new overloads of `moveTo`⏤ `moveTo(Position intermediateDestination, Position finalDestination)` and `moveTo(char intermediateColumn, int intermediateRow, char finalColumn, int finalRow)`⏤ that extend the respective semantics of `moveTo(Position destination)` and `moveTo(char column, int row)` to double moves.
- Both double-move methods should throw an exception if either the intermediate or the final move is erroneous, and the position of the piece, along with any other properties of the object, should remain exactly as they were immediately prior to the invocation of the method. This property is called [strong exception guarantee](https://en.wikipedia.org/wiki/Exception_guarantees). It's OK for the second move to return the superpiece to its original position.

For full credit, implement `Superpiece` using the Decorator design pattern, making an instance of `Superpiece` be a decorated `Queen`, `Rook`, `Bishop`, or `Knight`. Other solutions that work are OK too, but will get a 50% reduction in the number of points.  

If your solution is non-obvious, please explain it in a comment at the top of `Superpiece.java`.

### Part 1.2.2

Modify the `Pawn` class to provide an additional method `Superpiece promote(PieceType targetPiece)` where `targetPiece` can take values `QUEEN`, `ROOK`, `BISHOP`, or `KNIGHT`. The code should check, based on the `color` and `position` fields, whether the pawn is eligible for such a promotion. If not, it should throw an `InvalidMoveException`. (_Note:_ This is the only case where we allow you to modify the public interface of a class we have provided: modify `Pawn` and `PawnTests` to include the `promote` method.)

### Part 1.2.3

Write tests for `Superpiece`, both for its basic behavior coming from `Piece` and the behavior resulting from being a superpiece.  Feel free to write a single class or multiple ones, to reuse `PieceTest` or to write them from scratch, etc.  

Your tests must run by simply typing `./gradlew test` on the command line.

## Grading

Your grade on this Practice part of the exam has three components: implementation correctness, testing, and code quality. We will assign 50% of the points to implementation and 50% to testing, and then subtract points for code that does not obey the SwEng quality standards we taught you. We have automated tests to check the correctness of your code, and these tests will automatically compute the number of points you get in this category; it is therefore crucial that your code compiles and runs properly. For coverage, keep in mind that, the more code you write, the more tests you need to produce in order to get good coverage. We will assess the quality of your code both using automated tools and manually.