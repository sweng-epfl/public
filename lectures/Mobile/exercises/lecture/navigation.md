# News list

<p align="center">
<img src="https://user-images.githubusercontent.com/6318990/203812394-39525232-f9e9-4b61-a180-311484f61f15.jpg" width="200">
</p>

## Navigate to the news list

In this step, you will add a click listener to the button so that when the user clicks it, the app will open the news list activity.
The [Android documentation on sending users to another activity](https://developer.android.com/guide/components/intents-filters#ExampleExplicit)
contains some of the information you need in order to do this. Reading documentation is an important
part of being a software engineer.

## Creating the news list

Then, you need to create a list of news stories. To do this, we will be using an Android view
called `RecyclerView`. This view is a container for displaying large data sets that can be scrolled
very efficiently by reusing a limited number of views. It is a very powerful view and you will be
using it a lot in your career as an Android developer.

To create a `RecyclerView`, you need to add a `RecyclerView` to your layout file. You can do this by
editing `app/res/layout/activity_news.xml` as follows:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools" android:id="@+id/container"
    android:layout_width="match_parent" android:layout_height="match_parent"
    tools:context=".NewsActivity">

    <androidx.recyclerview.widget.RecyclerView android:id="@+id/news_list"
        android:layout_width="0dp" android:layout_height="0dp"
        app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
        app:layout_constraintBottom_toBottomOf="parent" app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

This will add a `RecyclerView` to your layout. You will notice that we have also added
a `layoutManager`
attribute to the `RecyclerView`. This attribute is used to specify the layout manager that will be
used to position the items in the `RecyclerView`. A layout manager is responsible for measuring and
positioning items within a `RecyclerView`. There are many different layout managers available, but
for now we will use the `LinearLayoutManager` which will position the items in a vertical list.

Note that we have also added an `id` attribute to the `RecyclerView`. This is used to reference the
`RecyclerView` from our code.

Launch your app. You will notice that the screen is blank. This is because we have not added any
items to the `RecyclerView`. We will do this in the next step.

## Preparing our news list

To display items in the `RecyclerView`, we need to create an adapter. An adapter is a bridge between
a `RecyclerView` and the underlying data for that view. The adapter provides access to the data
items. The adapter is also responsible for providing a `View` for each currently visible item in the
data set.

To create an adapter, we will first create a class which represents a single item in the list. This
class will be called `Story`, and may for instance be defined as follows:

```java
public class Story {

    public final int id;
    public final String title;
    public final String url;

    public Story(int id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    // Don't forget to implement equals() and hashCode()!
}
```

Then, we can create the adapter. This adapter will be called `StoriesAdapter`. It will be a subclass
of `ListAdapter` which is a convenience class for presenting lists of data in a `RecyclerView`.

```java
public final class StoriesAdapter extends ListAdapter<Story, StoriesAdapter.ViewHolder> {

    public StoriesAdapter() {
        super(new StoryItemCallback());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(android.R.layout.simple_list_item_2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(getItem(position).title);
        holder.subtitle.setText(getItem(position).url);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView subtitle;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(android.R.id.text1);
            subtitle = itemView.findViewById(android.R.id.text2);
        }
    }
}

public final class StoryItemCallback extends DiffUtil.ItemCallback<Story> {

    @Override
    public boolean areItemsTheSame(@NonNull Story oldItem, @NonNull Story newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Story oldItem, @NonNull Story newItem) {
        return oldItem.equals(newItem);
    }
}

```

The adapter is responsible for creating a `ViewHolder` for each item in the list. The `ViewHolder`
is a wrapper around a `View` which is used to display a single item in the list. The adapter will
reuse the `ViewHolder` for multiple items in the list.

The `onCreateViewHolder` method is called when a new `ViewHolder` needs to be created. In this
example, we use an existing layout from the Android framework called `simple_list_item_2` with two
text fields.

In `onBindViewHolder`, we bind the data from the `Story` to the `ViewHolder`. In this example, we
simply set the title and URL of the story to the text fields in the `ViewHolder`.

Finally, we provide a `DiffUtil.ItemCallback` which is used to determine whether two items in the
list are the same. This is used to optimize the `RecyclerView`'s `ListAdapter` by only updating the
items that have changed.

## Displaying the items

Now, we need to set the adapter on the `RecyclerView`. We can do this in the `onCreate` method of
our `NewsActivity`:

```java
public final class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        RecyclerView recyclerView = findViewById(R.id.news_list);
        var adapter = new StoriesAdapter();

        recyclerView.setAdapter(adapter);

        // Display a bunch of stories.
        adapter.submitList(generateStories());
    }


    private static List<Story> generateStories() {
        return List.of(
                new Story(0, "title 0", "https://epfl.ch"),
                new Story(1, "title 1", "https://epfl.ch"),
                new Story(2, "title 2", "https://epfl.ch"),
                new Story(3, "title 3", "https://epfl.ch"));
    }
}
```

We use the `id` attribute of the `RecyclerView` that we defined earlier to find the `RecyclerView`
in the layout. Then, we set the adapter on the `RecyclerView`. Finally, we generate a list of
stories and submit it to the adapter.

Run the app again. This time, you should see a list of stories displayed on the screen!

# Outro

## Clean up

This is a good time to clean up your code. Make sure there are no compiler warnings. Remove unneeded
code where necessary. Run *Code > Optimize imports* and *Code > Reformat code* on all your files, as
well as *Analyze > Inspect code* to improve the quality of your code.

Repeat this process on a regular basis as your code base changes.

## About some common warnings

* **Add backup properties** and **Firebase App Indexing**: These would be useful in a real app, but
  not here. Ignore the warnings.
* **Missing return value** in the Gradle script: This appears to be an Android Studio bug. Ignore
  the warning.
* **Unused property** in gradle.properties: Ignore the warning.
* **Typos** in words like sweng or epfl: Ignore them. (but fix real typos!)
* **Unused parameter** in the *onClick* handler: Android requires that parameter, even if you're not
  using it. Suppress the warning (for the method only!)
* **Obsolete stuff** in the tests: Suppress the warnings.

If you encounter some other warning, and believe it to either be a false positive, or something that
cannot be fixed without too much effort compared to the app's complexity, please ask the staff.

## <a name="FAQ"></a> FAQ

### My Android app shows a blank screen when adding controls

You might find
useful [this post](https://stackoverflow.com/questions/51126834/why-cant-i-see-text-in-activity-main-xml-when-i-create-a-new-android-studio-pro)
.

### When running my app, Android Studio indefinitely installs APK

Here are a few hints:

- The build may be corrupted; try running Build > Clean Project, Rebuild Project.
- Your emulator may be stuck. Try rebooting it, and if it stays frozen, delete the virtual device
  and recreate it (Tools > AVD Manager).
- If you or your friend has an Android phone, you can check whether the emulator is at fault by
  connecting your phone to the computer, authorize it and run the app on the phone directly (you may
  need to enable the phone's developer options).
