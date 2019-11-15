# Software Engineering - Midterm Exam

## Part 2: Practice [70 points]

In this part of the exam you need to write real code. As is often the case in software engineering, the problem itself is fairly easy to reason about, and what really matters is that your code employs solid software engineering principles. You should write code that is:

- Correct and robust
- Readable and concise
- Judiciously commented

You should use the same development environment as for the [bootcamp](https://github.com/sweng-epfl/public/blob/master/bootcamp/Readme.md). To validate your installation, you can run `java -version` from the command line; you are good to go as soon as the command outputs something like this:

	java version "12.0.2"
	Java(TM) SE Runtime Environment (build 12)
	Java HotSpot(TM) 64-Bit Server VM (build 23.2-b04, mixed mode)

The version number need not be identical to the one above, but it must start with `12`.

> :information_source: If you don't have java 12 yet, you can get it from [https://jdk.java.net/archive/](https://jdk.java.net/archive/). Please note that this exam can not be done in Java 13. 

You can run locally `./gradlew build`, after which you can find coverage results in `build/reports/jacoco/test/html/index.html`. You are free to use an editor or IDE of your choice, as long as the code and tests build using `./gradlew build`. On Windows, use `gradlew.bat` instead of `./gradlew`.

Please remember that we will only grade whatever is in your `master` branch. We will use `./gradlew build` to build your code.

We provide you with continuous integration at [Travis](https://travis-ci.com). It automatically retrieves the code from your `master` branch. If you forget to commit a file, or if you push the wrong version or a version that breaks your previously working version, you may lose all points, so please check Travis after every push.

This practical part of the exam has 2 questions with the number of points indicated next to each one. The preamble section introduces the setting of the problem.

## Preamble

> Welcome at SwengJet, dear new intern! We are a new innovative airline, and we need you to help us be the best airline in the world.
> We heard that you took the EPFL Software Engineering course, so we expect you to help us improve our software practices. 

## Question 1: Airline Booking Sytem (40 pts)

Your first task at SwengJet is to help them finish their airline booking system. Almost everything is ready, but there is still one important feature missing: assigning passengers to seats on the plane.

SwengJet wants their passengers to be able to pick any seat they want when booking, and you'll have to help the company implement this feature. To enable their web team to work on the user interface while the feature was not fully implemented, SwengJet already wrote an interface that you'll have to implement. Obviously, you cannot delete or add new methods, as it would require the web team to do all their work again. Moreover, the web team assumed you would do all the user input sanitization in your code, so you'll have to ensure that the input to their methods is correct. The interface definition contains exceptions that you'll have to throw in the corresponding conditions. You will have to throw exactly these exceptions, as the web team expects them to display the correct error message to the user.

The interface can be found in [`ch.epfl.sweng.midterm.booking.BookingSystem`](src/main/java/ch/epfl/sweng/midterm/booking/BookingSystem.java). Your implementation should go in [`ch.epfl.sweng.midterm.booking.SwengBookingSystem`](src/main/java/ch/epfl/sweng/midterm/booking/SwengBookingSystem.java).

### About seat numbering

SwengJet only flies SwengBus S321 NEO planes. In the SwengJet configuration, these planes have 26 rows (numbered 1 to 26), and in each row, seats are numbered from A to F (always uppercase), left to right.

Therefore, here are some valid seat numbers:

 - 14C (14th row, 3rd seat)
 - 22A (22nd row, 1st seat)
 - 1F (1st row, 6th seat)

Here are some invalid seat numbers: 321C, 27A, 12K, 14c, 1f, A20, F1, A73, Z0, 1, A, é1, 12!

Your code must check that the seat number provided by the customer is correct.

### Question 1.1: Testing (20 points)

As this SwengJet team works in a full TDD fashion, you must first write the tests before writing the implementation.

In the test class [`ch.epfl.sweng.midterm.booking.BookingSystemTest`](src/test/java/ch/epfl/sweng/midterm/booking/BookingSystemTest.java), write all the tests you think are necessary to ensure that the behavior of `SwengBookingSystem` is consistent with the documentation of its interface.

To get full points, your tests should precisely capture the behavior of the interface, as described in the documentation of its methods and the description in the question. The tests should pass if the implementation respects the specification of the interface, and should fail otherwise. The correct behavior includes throwing the right exceptions at the right time.

> :warning: We will run your tests on our source code as well. Therefore, make sure your tests don't test methods that are not defined in the interfaces. If your tests don't compile against our code, you will loose points.

> :information_source: To make sure your tests will run on our code, we run a special script on Travis every time you push. If your build fails because of it, it means your tests will not run on our code, so be careful!

### Question 1.2: Implementation (20 points)

You can now work on the implementation. We suggest that you use a map `Map<String, Person>` to map from the `String` seat number to the `Person` occupying that seat. Your implementation should pass all your tests.

To get full points, your implementation should correspond precisely to the specifications of the interface as described in the interface documentation and the description of the question. You are not allowed to change the definition of the interface.

> :warning: You should never change the interfaces that are provided to you, as we will run tests based on the signature of these interfaces. To make sure our tests can run on your code, you should execute the [SignaturesChecker](src/test/java/ch/epfl/sweng/midterm/SignaturesChecker.java) test suite. If it doesn't pass, then you have a problem.

---

## Question 2: Plane software (30 pts)

You did a great job on the Booking team, and now the Accounting unit asks you to fix their fuel computation needs. Planes refuel at each airport, and SwengJet wrote a program that computes the minimum amount of fuel they need to buy from the airport in order to get safely to its next destination. Alas, this code has bugs and computes a larger amount of fuel than needed. Your task is to fix this code.

You can find the interface in [`ch.epfl.sweng.midterm.planes.Plane`](src/main/java/ch/epfl/sweng/midterm/planes/Plane.java) and the implementation in [`ch.epfl.sweng.midterm.planes.SwengPlane`](src/main/java/ch/epfl/sweng/midterm/planes/SwengPlane.java). 

### Question 2.1: Fuel consumption (20 pts)

#### About fuel consumption

The consumption of the plane depends on different factors. For example, it's usually higher when taking off and landing. Given this, the `additionalFuelNeeded` method takes a `Function<Integer, Double>` that, given an integer `i`, returns the fuel consumption of the plane to go from kilometer `i` to kilometer `i+1`.

Moreover, regulators require that a plane always has 10% more fuel than what it actually needs. For example, if a plane requires 1'000 liters of fuel to go from Geneva to Zurich, then SwengJet will actually have to ensure that the plane has 1'100 liters of fuel (+10%).

Finally, when landing, the plane usually has some excess fuel from its previous flight. It is important to take this remaining fuel into account when refilling. To take the previous example: we need 1,000 liters to go from Geneva to Zurich, and an additional +10% because of the law. This amounts to 1'100 liters. However, if we still have 500 liters in the fuel tank, then we only need to add 1'100 - 500 = 600 liters. The `additionalFuelNeeded` method must therefore return the amount of fuel that needs to be added to the plane for it to be authorized to fly (600 litres in this example).

#### Question 2.1.1: Testing (10 pts)

Sadly, the lead engineer in the financial division didn't take Software Engineering, and the team didn't write any test for this method. You think that writing tests would probably have helped avoid the plane fuel consumption bug, so you decide that before doing anything else you will write the tests. 

In the test class [`ch.epfl.sweng.midterm.planes.PlaneFuelTest`](src/test/java/ch/epfl/sweng/midterm/planes/PlaneFuelTest.java), write the tests you think are appropriate.

Methods `Plane.addFuel(double)` and `Plane.getRemainingFuel()` may help you write your tests. These two methods are already tested, so you can assume that they work correctly.

When writing your test(s), you will need to write your own consumption function. When writing this function, you must always return a result >= 0, since negative consumption doesn't make sense. 

To get full points, we expect your tests to pass if and only if the implementation of `additionalFuelNeeded` is correct, i.e., if `additionalFuelNeeded` returns the same result as if one manually applied the formula described in the _About fuel consumption_ section.

> :warning: We will run your tests on our source code as well. When writing your tests, make sure you don't test methods that are not defined in the interfaces. If your tests don't compile against our code, you will loose points.

> :information_source: To make sure your tests will run on our code, we run a special script on Travis every time you push. If your build fails because of it, it means your tests will not run on our code, so be careful!

#### Question 2.1.2: Fix the bugs (10 points)

Now that you wrote tests, you should quickly be able to see what's wrong. Fix the bugs in the method and make sure that your tests now pass!

To get full points, you have to fix the bugs and make sure that `additionalFuelNeeded` returns the same result as if one manually applied the formula described in *About fuel consumption*. You should also ensure that your implementation is robust.

> :warning: You should never touch the interfaces that are provided to you, as we will run tests based on the signature of these interfaces. To make sure our tests can run on your code, you should execute the [SignaturesChecker](src/test/java/ch/epfl/sweng/midterm/SignaturesChecker.java) test suite. If it doesn't pass, then you have a problem.

### Question 2.2: Cruising speed (10 points)

This question can be done independently from question 2.1. (i.e., if you didn't fix the bugs in question 2.1, you can still do this question).

The mechanical team heard from the other teams at SwengJet that you helped them fix their problems. They really want to ask you to have a look at a problem with the plane speed calculations. The rest of the software engineering team couldn't figure it out and you're their last hope. Your next assignment is to help them hunt down some bugs.

#### About cruising speed

**Before** every flight, the lead mechanical engineer must compute the plane's intended approximate speed at every kilometer of the flight. This functionality is implemented in the `computePlaneSpeedFunction` method which returns a `Function<Integer, Double>` that can compute the plane's speed at a given kilometer. This function is determined by the initial (constant) weight of the plane and the total travelling distance.

The flight's travel is broken up into three parts:
 - Take-off, during which the speed of the plane increases linearly from 0 to its cruising speed
 - Cruising, during which the speed of the plane is constant (= cruising speed)
 - Landing, during which the speed of the plane decreases linearly from its cruising speed to 0

The returned `Function<Integer, Double>` must implement these three parts.

The cruising speed depends on the initial weight of the plane:
 - If the initial plane weight exceeds 75'000 kg, its cruising speed is 740km/h ;
 - If the initial plane weight is lower than 60'000 kg, its cruising speed is 940km/h ;
 - If the initial plane weight is between 60'000 kg and 75'000 kg, its cruising speed is a linear function of its weight (for example, a 67'500 kg plane cannot go faster than 840km/h).

Finally, the weight of the plane is determined as the sum of the following elements:
 - The weight of the empty plane ;
 - The weight of the passengers ;
 - The weight of the luggage ;
 - The weight of the fuel (the remaining fuel of the plane in liters multiplied by the weight of 1 liter of fuel)

You can find the current implementation of this computation in the `computePlaneWeight` method.

#### Your task

The lead mechanical engineer has observed that the plane's speed function doesn't match the one described in _About cruising speed_. Your task is to write tests to expose any bugs. Your tests should fail on the current implementation (as it has bugs), but pass on a correct implementation. You are not asked to fix the bugs, but merely to expose their presence through testing. You must write these tests in the test class [`ch.epfl.sweng.midterm.planes.PlaneSpeedTest`](src/test/java/ch/epfl/sweng/midterm/planes/PlaneSpeedTest.java).

Hint: the lead mechanical engineer suspects that part of the problem comes from the plane weight that is not computed correctly.

To get full points, we expect your tests to pass if and only if the implementation of `computePlaneSpeedFunction` is correct, i.e., if `computePlaneSpeedFunction` returns the same result as if one applied manually the formulas described in the _[About cruising speed](#About-cruising-speed)_ section.

> :warning: We will run your tests on our source code as well. When writing your tests, make sure you don't test methods that are not defined in the interfaces. If your tests don't compile against our code, you will loose points.

> :information_source: To make sure your tests will run on our code, we run a special script on Travis every time you push. If your build fails because of it, it means your tests will not run on our code, so be careful!
