# Exercise: Profiling

This folder contains a sample app that removes the header from a file, see its source code.
It is, however, not very efficient.
Your goal is to profile it to see what's going on, and optionally to try to improve it.

You can use any profiler you like, such as IntelliJ's.

If you don't have one already, use VisualVM:
- Download it from [its website](https://visualvm.github.io/download.html)
- Extract the archive
- Run `visualvm.exe` (Windows) or `visualvm` (MacOS, Linux) in the `bin/` folder in the extracted files

Launch the app, such as with `gradlew.bat run` (Windows) or `./gradlew run` (MacOS, Linux).
It will print a message asking you to press a key to continue.
Before doing so, attach a profiler.

For instance, with VisualVM:
- Look at the "Local" section of the "Applications" tab on the left
- Right-click "App" and select "Open"
- Go to the "Profiler" tab on the right and click on "CPU"
  - The first time you do this, VisualVM will ask you to calibrate, accept it
  - You will see some lines printed on the console of your running app
- Enter a key in the console of the running app
- Wait for it to finish, then accept VisualVM's suggestion of capturing a snapshot
- Look at the snapshot

Which method is called the most? What could you do about it?
