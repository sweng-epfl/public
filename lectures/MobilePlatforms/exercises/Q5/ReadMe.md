# Android Persistence

<p align="center">
<img src="https://user-images.githubusercontent.com/6318990/203812830-f5879db6-ce7e-478f-8376-0c5f7f9873fe.jpg" width="200">
</p>

When developing an Android application, you will often need to store data as an offline cache. This
can be done in a variety of ways, but the most common way is to use a database. There are many
different types of databases, but the most common one is the SQLite database. SQLite is a relational
database that is built into Android. It is a very simple database that is easy to use and is quite
fast. It is also very small, so it is a good choice for storing small amounts of data.

A common library for working with SQLite databases is
the [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room.html)
. Room provides an abstraction layer over SQLite to allow you to more easily work with objects,
provides compile-time checks of SQL queries, and can return observable objects that will notify you
when the database has changed.

In this exercise, you will extend the Hacker News app you created in the previous exercise to store
the stories in a database.

## Step 1: Adding Room to the project

If you go to the `build.gradle` file for the app module, you will see that the Room library has been
added as a dependency:

```groovy
dependencies {
    def room_version = "2.4.3"
    implementation "androidx.room:room-runtime:$room_version"
    annotationProcessor "androidx.room:room-compiler:$room_version"
    testImplementation "androidx.room:room-testing:$room_version"
    // ...
}
```

Note that Room uses the `annotationProcessor` configuration to add the Room compiler to the project.
This annotation processor will generate code for you based on the annotations you use in your
project, and also run the Room compiler to check your queries.

## Step 2: Study the app changes

The app has been slightly modified to provide an offline mode.
The [`StoriesViewModel`](app/src/main/java/ch/epfl/sweng/presentation/StoriesViewModel.java) class
now supports a `refresh` and a `clearAll` method. The `refresh` method will fetch the stories from
the API and store them in the database. The `clearAll` method will delete all the stories from the
database.

The `refresh` and `clearAll` methods are called by the `StoriesFragment` when the user clicks on the
refresh and clear buttons, or when the fragment is created.

## Step 3: Implement offline mode

You should now implement the offline mode in the app. This means that you should perform the
following changes:

- Add a Room `@Entity` that represents the stories you want to store for offline use.
- Add a Room `@Dao` interface that provides methods to access the stories in the database, as well
  as some methods to delete the stories.
- Add a Room database which, which will be provided to / constructed in `StoriesViewModel`.
- Update the appropriate methods in `StoriesViewModel`.
