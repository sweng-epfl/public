# Exercise 3: Continuous Integration with Github Actions

Github Actions is what we call a **continuous integration** service. It is directly integrated into GitHub, which makes it very practical to use.

A CI automates the build of your application: you can define several actions to run either when a button is pressed, or at every push / pull request on a repository. This is a very powerful tool that enables you to, for example, run all your unit tests at every push to make sure you didn't break anything - or at every PR to make sure the PR _won't_ break anything if you merge it. You can also deploy code automatically to a production server, build the binaries for a release...

## Forking the repo

To get started, fork [this](https://github.com/sweng-epfl/sample-app-without-ci) repository, which contains a sample Android application written in Kotlin. We will be setting up a Github Actions pipeline for it. You can find more information on how to fork a repository [here](https://docs.github.com/en/get-started/quickstart/fork-a-repo).

## Setting up your build

GitHub Actions are small YAML files located in a special directory that GitHub reads and understands.
You can define multiple actions on a project, to react differently to different events.

The action you will create for now will simply build the app and test it in an Android emulator. As the app has no tests, the "test" part will **do nothing** - so for now your action will just check that the app _compiles_. But if the app had tests, this action would also report if the tests fail.

Actions are simply files in the repository, so you can create them with any test editor. However, GitHub provides a nice enriched interface to create them, so we suggest you use it.

Go to your forked repository on GitHub and make sure you are logged in to your account. You should see an "Actions" tab, click it.

On the next page, click "Skip this and set up a workflow yourself".

GitHub provides a lot of workflows for common use-cases, but doesn't explicitly recommend anything for Android apps, so we will build it ourselves.

GitHub actions use **containers** that run in the Cloud (Microsoft Azure, as far as we know). Containers are like small VMs that can be quickly deployed and that run some software in isolation. In GitHub actions, you specify which kind of container you want to run (`ubuntu`, `macos`, ...), and then you import some action templates that provide you with tools to build your project. We will see how this works now.

On the page that opens, set the name of your action file at the top - something like "android.yml" would work just fine.

Clear the large code editor below, and copy/paste the following code:

```yaml
name: Android Workflow

on: [push, pull_request]

jobs:
  test:
    runs-on: macos-latest
    steps:
    - name: checkout
      uses: actions/checkout@v2

    - name: Make gradlew executable
      run: chmod +x ./gradlew

    - name: run tests
      uses: reactivecircus/android-emulator-runner@v2
      with:
        api-level: 29
        script: ./gradlew connectedCheck
```

We will now comment all the lines in this workflow.

 - First comes the `name` of your workflow. You can choose anything you like for this.
 - Then comes the list of events that can trigger the action. Here, we defined `[push, pull_request]`. This means this action will run every time you either pull or open/update a pull request. You can see more examples of triggers in the [GitHub Documentation](https://docs.github.com/en/free-pro-team@latest/actions/reference/events-that-trigger-workflows)
 - Then you have a list of `jobs`. In this build, we only have one `job` called `test`. Each job defines multiple parameters:
 	- First, you have the `runs-on` paremeter, which defines the base container to use. Here, we want to run on `macos` because it enables hardware acceleration, which we need to run the emulator. We specifiy `macos-latest` to use the latest version of macos, as we don't care about the specific version used in the container.
 	- Then, we have a list of `steps` to run in the build. They are ran one after the other. A typical `step` has a name, `uses` one or multiple templates, and specifies some parameters defined by the template.
 		+ The first action uses the standard template `actions/checkout@v2` (which corresponds to the tag `v2` on GitHub repository `actions/checkout` - actions are repositories themselves!). This action simply clones the repository and sets the current working repository in it.
 		+ Finally, you have the action running the tests. This action uses the template `reactivecircus/android-emulator-runner@v2`. This template sets-up an emulator for you, and then lets you execute any script you like. Here, our `script` is a command that builds the app and executes the tests on an emulator. The documentation of this action can be found in the [GitHub Actions Marketplace](https://github.com/marketplace/actions/android-emulator-runner). You will probably want to have a look at this at some point in SDP!

Now, you can commit the file using the dedicated button. After a few seconds, going back to the **Actions** tab you should see an action with the name you chose earlier. If everything goes fine, it should be running or scheduled to run soon. After a few minutes (~ 5-7mn) you should get a successfull result and a nice green check mark.

If the build fails, you can check the logs to see what went wrong and fix it. If you still can't get it to work, please ask a TA.

That's all for today!
