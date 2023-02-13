# Creating an Android app

We will kickstart your career as an Android software engineer by creating a Hacker News client. This
application will let you navigate to a list of news stories.

<p align="center">
<img src="https://user-images.githubusercontent.com/6318990/203811715-eddbfdf4-a5ad-491e-8d86-b8eb836fd6f7.jpg" width="200">
</p>

## Create the main screen

First, you need to build the screen on which the users can click a button to navigate to the news
list.

Edit the **app/res/layout/activity_main.xml** file and add a *Button*
to your activity by dragging them from the component Palette. Each component has a set of attributes
that can be displayed by clicking on it. In the Attributes of the *Button* component, modify the *
text* attribute as a hint for the user (e.g., "Show news"). You can then style the button as you
like.

One part is important, though: set the `id` attribute of the button to a meaningful value, such
as `mainGoButton`. You will use this ID to access the components from your source code. To do it,
select the component and modify the id attribute in the Attributes tab (usually at the very top of
the list). Alternatively, add **android:id="..."** to the XML code of the component.

Launch your app. This will start an emulator and show the awesome activity you just created.

## Create the news list screen

Now, let's create the screen on which the news will be displayed.

To do so, add a second empty activity called **NewsActivity** to your app. In the next exercise,
you'll see how to navigate to this activity using intents, and how to display a list of news stories
using a `RecyclerView`.
