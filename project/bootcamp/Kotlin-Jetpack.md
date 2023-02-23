# Kotlin and Jetpack Compose

When building an Android app, developers can opt for either Java or Kotlin as their programming language.
Although both options are equally viable, Google recommends using Kotlin due to its advantages over Java.
Additionally, to work hand in hand with Kotlin, Google has created Jetpack Compose, a modern toolkit to build Android user interfaces. Jetpack Compose offers a more streamlined and effective approach to developing and handling UI components. This guide aims to provide an overview of both Kotlin and Jetpack Compose, so that you and your team can make an informed decision about which option to utilize.

## Kotlin

Kotlin is a modern, statically-typed programming language that was designed to be interoperable with Java. It was developed by JetBrains in 2011 and has been steadily gaining popularity since then. Kotlin offers several benefits over Java, such as:

* Concise syntax: Kotlin is much more concise than Java, meaning that developers can write less code to accomplish the same tasks.

* Null safety: Kotlin has a built-in null safety system that prevents developers from encountering null pointer exceptions.

* Interoperability with Java: Kotlin can be used alongside Java in the same project (but we recommend using Kotlin exclusively if you choose to use it).

Kotlin has many other features that make it a more attractive option than Java, such as support for generics, extension functions, data class, and better multi-threading.

If you already know Java and a functional language such as Scala, you should be able to pick up Kotlin quickly while working on your Android app, without having to learn it "from scratch".

## Example Kotlin vs Java

### Main function
```kotlin
fun main() {
    println("Hello, World!")
}
```

```java
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

### Variables
```kotlin
var name = "John"
val age = 21
```

```java
String name = "John";
int age = 21;
```

### Functions
```kotlin
fun add(a: Int, b: Int): Int {
    return a + b
}
```

```java
public int add(int a, int b) {
    return a + b;
}
```

### Classes
```kotlin
data class Person(val name: String, val age: Int)
```

```java
public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
    
    @Override
    public String toString() {
        return "Person(name=" + name + ", age=" + age + ")";
    }
    
    @Override
    public boolean equals(Object o) {
        return true;
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    public Person copy(String name, int age) {
        return new Person(name, age);
    }
}
```

## Jetpack Compose

Jetpack Compose is a modern toolkit for building Android user interfaces. It was introduced by Google in 2019 and has been steadily gaining popularity since then. Jetpack Compose uses a declarative approach to building UI, which means that developers describe what they want the UI to look like, and the toolkit takes care of the rest.
This leads to more concise code, so you can write less code to accomplish the same tasks, and you can tell exactly what the UI will look like just by looking at the code.

### Example

Without Jetpack Compose, we need to create an XML file and a Java file to create a simple increment button.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="0" />

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Click me" />
        
</LinearLayout>
```

```java
public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                textView.setText(String.valueOf(count));
            }
        });
    }
}
```

In Kotlin, we can create a simple increment button like this. No need for XML files or any other UI files.

```kotlin
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }
    Button(onClick = { count++ }) {
        Text(text = "Click me")
    }
    Text(text = count.toString())
}
```

## Task

Re-do the individual bootcamp, but using Kotlin and Jetpack Compose!

## Documentation

If you are interested in learning more about Kotlin and Jetpack Compose, check out the following resources: 
 * [Jetpack Compose Official Documentation](https://developer.android.com/jetpack/compose)
 * [Jetpack Compose Getting Started Tutorial](https://developer.android.com/jetpack/compose/tutorial)
