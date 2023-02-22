# Google authentication

You will need to add a way to login to your app. There are different ways to do so, here we explore to use of One Tap, which enables login with Google credentials.
You can always filter Google accounts, such as choosing to only allow accounts with a certain email domain.

## Before you start

In the following tutorials, except the Google one, you will see that you need to use Firebase and add your project, keys, dependencies, and so on. Please notice that there is another bootcamp for Firebase, and that some steps may be identical to the ones describe in the Firebase bootcamp. You should communicate with the teammate that is responsible for it, to be sure you do not do twice the work, which could create some problems later on. The third tutorial features Jetpack Compose, you can check the bootcamp for it for more details. 

## Tutorials

There are a lot of available tutorials on login pages for Android apps. Here you can find three of them. The first one is the recommended tutorial that would be best for most teams: directly from Firebase documentation, it is concise and you shouldn't have much trouble to follow it and complete it. The second one may be a bit more complete, however it is not specifically using Firebase as a backend so you'll need to complete this part. The third tutorial features a lot of explanation and code, it uses Jetpack Compose, so you should follow it only if you intend in using Jetpack Compose for your app. Pay attention however that copy-pasting is not the same as understanding/training. You could also find existing code containing a working login page, but you would have missed an important opportunity to tackle and understand the problem yourself. 



[Firebase - Authenticate with Google on Android](https://firebase.google.com/docs/auth/android/firebaseui)

[Google - One Tap sign-up/sign-in for Android](https://developers.google.com/identity/one-tap/android/overview?hl=fr)

[Medium - How to authenticate to Firebase using Google One Tap in Jetpack Compose?](https://medium.com/firebase-developers/how-to-authenticate-to-firebase-using-google-one-tap-in-jetpack-compose-60b30e621d0d)

## Testing

You should isolate authentication behind an interface so that your tests for other components do not have to involve authentication directly, and can also test edge cases in users.

Try to have tests for the real authentication if possible, but keep in mind it's a small amount of code so you could choose not to cover it. You might struggle when it comes to testing the One Tap UI. This is because the One Tap component is not directly accessible. You can use [UI Automator](https://developer.android.com/training/testing/other-components/ui-automator) to "let you interact with visible elements on a device, regardless of which Activity is in focus". [Another tutorial](https://google-developer-training.github.io/android-developer-fundamentals-course-concepts-v2/unit-2-user-experience/lesson-6-testing-your-ui/6-1-c-ui-testing/6-1-c-ui-testing.html#auto) on testing using UI Automator might come in handy. 


Now you have a nice way to login using Google! :) 