# Mobile Platforms

This lecture's purpose is to give you a high-level picture of what the universe of mobile applications and devices is like. You will read about:

* Differences between desktops and mobile devices w.r.t. applications, security, energy, and other related aspects
* Challenges and opportunities created by mobile platforms
* Brief specifics of the Android stack and how applications are structured
* A few ideas for offering users a good experience on their mobile
* The ecosystem that mobile apps plug into

## From desktops to mobiles

Roughly every decade, a new, lower priced computer class forms, based on a new programming platform, network, and interface. This results in new types of usage, and often the establishment of a new industry. This is known as [Bell's Law](https://en.wikipedia.org/wiki/Bell%27s_law_of_computer_classes).

With every new computer class, the number of computers per person increases drastically. Today we have clouds of vast data centers, and perhaps an individual computer, like our laptop, that we use to be productive. On top of that come several computer devices per individual, like phones, wearables, and smart home items, which we use for entertainment, communication, quality of life, and so on.

It is in this context that mobile software development becomes super-important.

We said earlier that, no matter what job you will have, you will write code. We can add to that: you will likely write code for mobile devices. There are more than 15 billion mobile devices operating worldwide, and that number is only going up. As Gordon Bell said, this leads to new usage patterns.

We access the Internet more often from our mobile than our desktop or laptop. Most of the digital content we consume we do so on mobiles. We spend hours a day on our mobile, and the vast majority of that time we spend in apps, not on websites.

A simple example of a major change in how we use computing and communication is social media. Most of the world's population uses it. It changes how we work. Even the professional workforce is increasingly dependent on mobiles, for this reason.

### Mobile vs. desktop: Applications

There are many differences between how we write applications for a mobile device vs. a desktop computer. On a desktop, applications can do pretty much whatever they want, whereas, on a mobile, each app is super-specialized. On the desktop, users explicitly starts applications; on mobile, the difference between running or not is fluid: apps can be killed anytime, and they need to be ready to restart.

On a desktop, you typically have multiple applications active in the foreground, with multiple windows on-screen. The mobile experience is difference: a user's interaction with an app doesn't always begin in the same place, but rather the user's journey often begins non-deterministically. As a result, a mobile app has a more complex structure than a traditional desktop application, with multiple types of components and entry points.

The execution model on mobiles is more cooperative than on a desktop. For example, a social media app allows you to compose an email, and does so by reusing the email app. Another example is something like WhatsApp, which allows you to take pictures (and does so by asking the Photo app to do it). In essence, apps request services from other apps, and they build upon the functionality of others, which is fundamentally difference from the desktop application paradigm.

### Mobile vs. desktop: Operating environment

One of the biggest differences is in the security model. Think of your parents' PC at home or in an Internet café: there are potentially multiple users that don’t trust each other, each have specific file permissions, every application by default inherits all of a user's permissions, all applications are trusted to run with user's privileges alongside each other.  The operating system prevents one application from overwriting others, but does not protect the I/O resources (e.g., files). One could fairly say that security is somewhat of an afterthought on the desktop.

A mobile OS has considerably stronger isolation. The assumption here is that users might naively install malicious apps, and the goal is to protect users’ data (and privacy) even when they do stupid things. So, each mobile app is sandboxed separately.

When you install a mobile app, it will ask the device for necessary permissions, like access to contacts, camera, microphone, location information, SMS, WiFi, user accounts, body sensors, etc.

A mobile device is more constrained, e.g., you don't really get "root" access. It provides strong hardware-based isolation and powerful authentication features (like face recognition). E.g., for iPhone's FaceID, the face scan pattern is encrypted and sent to a secure hardware "enclave" in the CPU, to make sure that stored facial data is inaccessible to any party, including Apple.

Power management is a first-class citizen in a mobile OS. While PCs use high-power CPUs and GPUs with heatsinks, smartphones and tablets are typically not plugged in, so they cannot provide sustained high power. On a mobile, the biggest energy hog is typically the screen, whereas in a desktop it is the CPU and GPU.

Desktop OSes tend to be generic, whereas mobile OSes specialize for a given set of mobile devices, so they can have strict hardware requirements. This leads to less backward compatibility, and so the latest apps will not run on older versions of the OS, and new versions of a mobile OS won't run on older devices.

There is also orders-of-magnitude less storage on mobiles, and orders-of-magnitude less memory. Plus, you cannot easily expand / modify storage and memory.

Mobile networking tends to be more intermittent than in-wall connectivity. An Ethernet connections gives you Gbps, while a WiFi connection gives you Mbps.

On a desktop, the input comes typically from a keyboard and a mouse, whereas mobiles have small touch keyboards, voice commands, complex gestures, etc. Output is also limited on mobiles, so often they communicate with other devices to achieve their output.

We are witnessing today a convergence between desktop and mobile OSes, which will gradually make desktops disappear, and computing will become increasingly more embedded.

### Challenges and opportunities

This new world of mobile presents both opportunities and challenges.

Users are many more, and are more diverse. They have widely differing computer skills. Developers need to focus on ease of use, internationalization, and accessibility.

Platforms are more diverse (think phones, wearables, TVs, cars, e-book readers). Different vendors take different approaches to dealing with this diversity: Apple has the "walled garden" approach, while Android is more like the wild west: Android is open-source, so OEMs (original equipment manufacturers) can customize Android to their devices, so naturally Android runs on tens of thousands of models of devices today.

Mobiles get interesting new kinds of inputs, from multi-touch screens, accelerometers, GPS.  But they also face serious limitations in terms of screen size, processor power, and battery capacity.

Mobile devices need to (and can be) managed more tightly. Today you can remote-lock a device, you can locate the device, and with software like MDM (mobile device management) you can do everything on the device remotely.  Some mobile carriers even block users from installing certain apps on their devices.

App stores or Play stores are digital storefronts for content consumed on device. Today, the success of a mobile platform depends heavily on its app store, which becomes a central hub for content to be consumed on that particular mobile platform. The operator of the store controls apps published in the store (e.g., disallow sexually explicit content, violence, hate speech, anything illegal), which means that the developer needs to be aware of rules and laws in the places where app will be used. Operators typically use automated tools and human reviewers to check apps for malware and terms-of-service violations.

## How does a mobile operating environment work?

A mobile device aims to achieve seemingly contradictory goals: On the one hand, it aims for greater integration than a desktop, to create a more cooperative ecosystem in which to enable apps to provide services to each other. On the other hand, it aims for greater isolation, it wants to offer a stronger sandbox, to protect user data, and to restrict apps from interacting in more complex ways

Each mobile OS makes its own choices for how to do this. In this lecture, we chose to focus on Android, for three reasons:

* it is open-source, so it's easier to understand what it really does
* over 70% of mobiles use Android, there are more than 2.5 billion Android users and more than 3 billion active Android devices, so this is very real
* we will use it in the follow-on course [Software Development Project](https://dslab.epfl.ch/teaching/sweng/proj)

Android is based on Linux, and things like multi-threading and low-level memory management are all provided by the Linux kernel.  There is a layer, called the hardware abstraction layer (HAL), that provides standard interfaces to specific hardware capabilities, such as the camera or the Bluetooth module. Whenever an application makes a call to access device hardware, Android loads a corresponding library module from the HAL for that hardware component.

Above the HAL, there is the Android runtime (ART) and the Native C/C++ libraries.

The ART provides virtual machines for executing DEX (Dalvik executable) bytecode, which is specially designed to have a minimal memory footprint. Java code gets turned into DEX bytecode, which can run on the ART.

Android apps can be written in Kotlin, Java, or even C++. The Android SDK (software development kit) tools compile code + resource files into a so-called Android application package (APK), which is an archive used to install the app. When publishing to the Google Play app store, one generates instead an Android app bundle (ABB), and then Google Play itself generates optimized APKs for the target device that is requesting installation of the app. This is a more practical approach than having the developer produce individual APKs for each device.

Each Android app lives in its own security sandbox. In Linux terms, each app gets its own user ID. Permissions for all the files accessed by the app are set so that only the assigned user ID can access them. It is possible for apps to share data and access each other's files, in which case they get the same Linux user ID; the apps however must be from the same developer (i.e., signed with the same developer certificate).

Each app runs in its own Linux process. By default, an app can access only the components that it needs to do its work and no more (this follows the [principle of least privilege](https://en.wikipedia.org/wiki/Principle_of_least_privilege)). An app can access the device's location, camera, Bluetooth connection, etc. as long as the phone user has explicitly granted it these permissions. Each app has its own instance of the ART, with its own VM to execute DEX bytecode. In other words, apps don't run inside a common VM.

In Android, the UI is privileged. Foreground and UI threads get higher priority in CPU scheduling than the other threads. Android uses process containers (a.k.a., [Linux cgroups](https://en.wikipedia.org/wiki/Cgroups)) to allocate a higher percentage of the CPU to them.  When users switch between apps, Android keeps non-foreground apps (e.g., not visible to the user) in a cache (in Linux terms, the corresponding processes do not terminate) and, if the user returns to the app, the process is reused, which makes app switching faster.

Many core system components (like the ART and HAL) require native libraries written in C/C++. Android provides Java APIs to expose the functionality of these native libraries to apps. This Java APIs framework is layered on top of the ART and native C/C++ libraries. Apps written in C/C++ can use the native libraries directly (this requires the Android native development kit, or NDK).

Within the Java API framework, there are a number of Android features provided through APIs written in Java that provide key building blocks for apps. For example, the View System is used for building the UI. E.g., the Resource Manager is used by apps for accessing localized strings or graphics. E.g., the Activity Manager handles the lifecycle of apps and provides a common navigation back stack (more on this below). And, as a final example, Content Providers enable apps to access data from other apps (e.g., a social media app access the Contacts app) or to share their own data.

On top of this entire stack are the apps. Android comes with core apps for email, SMS, calendaring, web browsing, contacts, etc. but these apps have no special status, then can be replaced with third-party apps. The point of shipping them with Android is to provide key capabilities to other apps out of the box (e.g., allow multiple apps to have the functionality of sending an SMS without having to write the code for it).

An Android app is a collection of components, with each component providing an entry point to the app. There are 4 main components:

* An _Activity_ provides an entry point with a UI, for interacting with the user. An activity represents a single screen with a user interface. For example, an email app has an activity to show a list of new emails, another activity to compose an email, another activity to read emails, and so on -- activities work together to form the email app, but each one is independent of the others. Unlike in desktop software, other apps can start any one of these activities if the email app allows it, e.g., the Camera app may start the compose-email activity in order to send a picture by email.
* A _Service_ is a general-purpose entry point without a UI. This essentially keeps an app running in the background (e.g., play music in the background while the user is in a different app, or fetch data over the network without blocking user interaction with an activity). A service can also be started by another component.
* A _Broadcast Receiver_ enables Android to deliver events to apps out-of-band, e.g., an app may set a timer and ask Android to wakes it up at some point in the future; this way, the app need not run non-stop until then. So broadcasts can be delivered even to apps that aren't currently running. Broadcast events can be initiated by the OS (e.g., the screen turned off, battery is low, picture was taken) or can be initiated by apps (e.g., some data has been downloaded and is available for other apps to use). Broadcast receivers have no UI, but they can create a status bar notification to alert the user.
* A _Content Provider_ manages data that is shared among apps. Such data can be local (e.g., file system, SQLite DB) or remote on some server. Through a content provider, apps can query / modify the data (e.g., the Contacts data is a content provider: any app with the proper permissions can get contact info and can write contact info).

Remember that any app can cause another app’s component to start. For instance, if WhatsApp wants to take a photo with the camera, it can ask that an activity be started in the Camera app, without the WhatsApp developer having to write the code to take photos--when done, the photo is returned to WhatsApp, and to the user it appears as if the camera is actually a part of WhatsApp.

To start a component, Android starts the process for target app (if not already running), instantiates the relevant classes (this is because, e.g., the photo-taking activity runs in the Camera process, not WhatsApp's process). You see here an example of multiple entry points: there is no `main()` like in a desktop app.

Then, to activate a component in another app, you must create an _Intent_ object, which is essentially a message that activates either a specific component (explicit intent) or a type of component (implicit intent).  For activities and services, the intent defines the action to perform (e.g., view or send something) and specifies the URI of the data to act on (e.g., this is an intent to dial a certain phone number). If the activity returns a result, it is returned in an Intent (e.g., let the user pick a contact and return a URI pointing to it).  For broadcast receivers, the intent defines the announcement being broadcast (e.g., if battery is low it includes the BATTERY_LOW string), and a receiver can register for it by filtering on the string. Content providers are not activated by intents but rather when targeted by a request from what's called a Content Resolver, which handles all interaction with the content provider.

## A mobile app: Structure and lifecycle

The centerpiece of an Android app is the _activity_. Unlike the kinds of programs you've been writing so far, there is no `main()`, but rather the underlying OS initiates code in an _Activity_ by invoking specific callback methods.

An activity provides the window in which the app draws its UI. One activity essentially implements one screen in an app, e.g., 1 activity for a Preferences screen, 1 activity for a Select Photo screen, etc.

Apps contain multiple screens, each of which corresponds to an activity. One activity is specified as the main activity, i.e., the first screen to appear when you launch the app. Each activity can then start another activity, e.g., the main activity in an email app may provide the screen that shows your inbox, then you have screens for opening and reasing a message, one for writing, etc.

As a user navigates through, out of, back into an app, the activities in the app transition through diff states. Transitioning from one state to another is handled by specific callbacks that must be implemented in the activity. The activity learns about changes and reacts to when the user leaves and re-enters the activity: For example, a streaming video player will likely pause the video and terminate the network connection when the user switches to another app; when the user returns to the app, it will reconnect to the network and allow the user to resume the video from the same spot.

To understand the lifecycle of an activity, please see [the Android documentation](https://developer.android.com/guide/components/activities/activity-lifecycle).

Another important element are _fragments_. These provide a way to modularize an activity's UI and reuse modules across different activities. This provides a productive and efficient way to respond to various screen sizes, or whether your phone is in portrait or landscape mode, where the UI is composed differently but from the same modules.

A fragment may show the list of emails, and another fragment may display an email thread. On a phone, you would display only one at a time, and upon tap switch to the next activity. On a tablet, with the greater screen real estate, you have room for both. By modularizing the UI into fragments, it's easy to adapt the app to the different layouts by rearranging fragments within the same view when the phone switches from portrait to landscape mode.

To understand fragments, please see [the Android documentation](https://developer.android.com/guide/fragments).

You don't have to use fragments within activities, but such modularization makes the app more flexible and also makes it easier to maintain over time. A fragment defines and manages its own layout, has its own lifecycle, and can handle its own input events. But it cannot live on its own, it needs to be hosted by an activity or another fragment.

Android beginners tend to put all their logic inside Activities and Fragments, ending up with "views" that do a lot more than just render the UI. Remember what we said about MVVM and how that maps to how you build an Android app. In the exercise set, we will ask you to go through an MVVM exercise. Android provides a nice ViewModel class to store and manage UI-related data.

## User experience (UX)

User experience design (or "UX design") is about putting together an intuitive, responsive, navigable, usable interface to the app. You want to look at the app through the users' perspective to derive what can give them an easy, logical and positive experience.

So you must ask who is your audience: Old or young people? Intellectuals or blue-collar workers? Think back to the lecture on personas and user stories.

The UI should enable the user to get around the app easily, and to find quickly what they're looking for. To achieve this, several elements and widgets have emerged from the years of experience of building mobile apps. The [hamburger icons](https://en.wikipedia.org/wiki/Hamburger_button) are used for drop-down menus with further details--they avoid clutter. Home buttons give users a shortcut to home base. Chat bubbles offer quick help through context-sensitive messages.

Personalization of the UI allows adapting to what the user is interested in, keeping the unrelated content away. For example, in the [EPFL Campus app](https://pocketcampus.org/epfl-en) you can select which features you see on the home screen, and so the home screen of two different users is likely to look different.

In order to maximize readability, emphasize simplicity and clarity, choose adequate font size and image size. Avoid having too many elements on the screen, because that leads to confusion. Offer one necessary action per screen. Use [micro UX animations](https://uxplanet.org/ui-design-animations-microinteractions-50753e6f605c), which are little animations that appear when a specific action is performed or a particular item is hovered or touched; this can increase engagement and interactivity. E.g., hovering over a thumbnail shows details about it. E.g., hover zoom for maximizing the view of a specific part of something.  Keep however the animations minimal, avoid flashiness, make them subtle and clear as to their intent.

Keep in mind _thumb zones_. People use their mobile when they’re standing, walking, riding a bus, so the app should allow them to hold the device and view its screen and provide input in all these situations. So make it easy for the user to reach with their thumb the stuff they do most often. Leverage gesture controls to make it easy for a user to interact with apps--e.g., “holding” an item, “dragging” it to a container, and then “releasing”--but beware to do what users of that device are used to doing, not exotic stuff.

Think of augmented reality and voice interaction, depending on the app, they can be excellent ways to interact with the device.

## The mobile ecosystem

A modern mobile app, no matter how good it is, can hardly survive without plugging into its ecosystem.  

Most mobile apps are consists of the phone side of the app and the cloud side. Apps interact with cloud services typically over REST APIs, which is an architectural style that builds upon HTTP and JSON to offer CRUD (copy-read-update-delete) operations on objects that are addressed by a URI. You use HTTP methods to access these resources via URL-encoded parameters.

Aside from the split architecture, apps also interact with the ecosystem through Push notifications. These are automated messages sent to the user by the server of the app that’s working in the background (i.e., not currently open). Each OS (e.g., Android, iOS) has its own push notification service with a specific API that apps can use, and they each operate a push engine in the cloud.

The app developer enables their app with push notifications, at which point the app can start receiving incoming notifications. When you open the app, unique IDs for both the app and the device are created by the OS and registered with the OS push notification service. IDs are passed back to the app and also sent to the app publisher. The in-cloud service generates the message (either when a publisher produces it, or in response to some event, etc.) which can be targeted at users or a group of users and gets sent to the corresponding mobile. On Android, an incoming notification can create an Activity.

A controversial aspect of the ecosystem is the extent to which it track the device and the user of the device. We do not discuss this aspect here in detail, it is just something to be aware of.

A key ingredient of "plugging into" the ecosystem is the network. Be aware that your users may experience different bandwidth limitations (e.g., 3G vs. 5G), and the Internet doesn't work as smoothly everywhere in the world as we're used to. In addition to bandwidth issues, there is also the risk of experiencing a noticeable disconnection from the Internet. E.g., a highly interactive, graphic-heavy app will not be appropriate for apps that target Latin America or Africa, or users in rural areas, because today there are still many areas with spotty connectivity.

Perhaps a solution can be offered by the new concept of fog computing (as opposed to cloud computing), also called edge computing. It is about the many "peripheral" devices that connect to the periphery of a cloud, and many of these devices will generate lots of raw data (e.g., from sensors). Rather than forward this data to cloud-based servers, one could do as much processing as possible using computing units nearby, so that processed rather than raw data is forwarded to the cloud. As a result, bandwidth requirements are reduced. "The Fog" can support the Internet of Things (IoT), including phones, wearable health monitoring devices, connected vehicle and augmented reality devices. IoT devices are often resource-constrained and have limited computational abilities to perform, e.g., cryptography computations, so a fog node nearby can provide security for IoT devices by performing these cryptographic computations instead.

## Summary

In this lecture, you learned:

* Several ways in which a desktop app differs from a mobile app
* How security, energy, and other requirements change when moving from a desktop to a mobile
* How activities and fragments work in Android apps
* The basics of good UX
* How mobile apps can leverage their ecosystem

You can now check out the [exercises](exercises/).
