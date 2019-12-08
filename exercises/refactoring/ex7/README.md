# Code Climate

Sometimes when writing code it is hard to take a step back and assess it's quality. We each have our own coding style and coding habits, and it may be difficult to identify smells we might have introduced. One way to address this issue is to have someone else review our code. However, this can quickly become tedious and time consuming. Also, what if their notion of "good code" is different than yours ? A possible solution can be to use a tool like _Code Climate_. It is an automated code review tool, which checks source code for compliance with a predefined set of rules or best practices.

Setting up automated code review with Code Climate Quality is easy: First, you need an account on [Code Climate Quality](https://codeclimate.com/quality/). Start the Free Quality Trial. You may be asked to sign in to GitHub, if not yet done. Authorize Code Climate to access your GitHub account. Click next. In the `Free for open source` panel, add a repository. This should trigger an analysis of your code.

## Enable Code Climate features on GitHub
Get back to the Code Climate dashboard of your repository, the **Repo Settings** tab, and click **GitHub**. You will install two Code Climate Quality features to make full use of their capabilities through GitHub.

- Set up the feature `Pull request comments`. You may need to enter your password. Select your bootcamp repository, and save. Go back to **Repo Settings/GitHub**. Enable `Summary comments` and `Inline issue comments`, and save the changes.
- Install the feature `Pull request status updates`.

With these two features enabled, you will be able to get Code Climate reviews directly in your pull requests. This way, you won't need to use Code Climate dashboard anymore.

#### Badges

If you want everyone to see how clean and awesome your code is, you can add badges to the `README.md` file of your repo. They can be found in the **Repo Settings** tab, in Code Climate under **Badges**. Make sure you use the Markdown version.

It should look something like this: [![Maintainability](https://api.codeclimate.com/v1/badges/a99a88d28ad37a79dbf6/maintainability)](https://codeclimate.com/github/codeclimate/codeclimate/maintainability)

#### Additional plugins

There are a few plugins worth using along with Code Climate. For Java projects for example, the following two are useful:
- [Checkstyle](https://docs.codeclimate.com/docs/checkstyle) help you adhere to a coding standard. This would be quite useful during your project, each member of the team having their own coding style.
- [SonarJava](https://docs.codeclimate.com/docs/sonar-java) is a code analyzer for Java projects. In addition to Code Climate analyses, it performs hundreds of checks to find code smells, bugs and security vulnerabilities.

You can see the list of available plugins in the **Repo Settings** tab.

## Code Climate integration with Travis CI

> :warning: For this part make sure your project is setup with Travis CI that you configured your gradle project with the JaCoCo plugin as described [here](https://github.com/sweng-epfl/public/blob/master/bootcamp/AndroidBootcamp.md#configure-gradle-with-jacoco-plugin) or [here](https://docs.gradle.org/current/userguide/jacoco_plugin.html).

So far, Travis CI builds your project and runs your test suite while Code Climate performs automated code review. It goes without saying that both tasks are independent for now.

Code Climate can actually make use of the test suite run by Travis CI, namely code coverage metrics. In order to do so, you need to generate a test report, in this case using JaCoCo. Then the test report needs somehow to be sent to Code Climate. Travis CI provides ready-to-use test reporters for that purpose.

Go to your Travis CI repository's dashboard. Click the **More options** button on the right. Select **Settings**. You need to add a new environment variable:

- Name: `CC_TEST_REPORTER_ID`
- Value: Go to your Code Climate repository's dashboard. Access the **Repos Settings**. Find **Test coverage**. Copy and paste the `TEST REPORTER ID`. Leave unset the `Display value in build log` toggle button.

You now need to edit your `travis.yml` file. For example, for an android project you should add the following snippet:

```yml
  # This should be in the `before_script` entry
  # Set up Code Climate test reporter
  - curl -L https://codeclimate.com/downloads/test-reporter/test-reporter-latest-linux-amd64 > ./cc-test-reporter
  - chmod +x ./cc-test-reporter
  - ./cc-test-reporter before-build
script:
  - ./gradlew build connectedCheck jacocoTestReport
after_script:
  # Report test coverage to Code Climate
  - export JACOCO_SOURCE_PATH=app/src/main/java/
  - ./cc-test-reporter format-coverage ./app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml --input-type jacoco
  - ./cc-test-reporter upload-coverage
```

From now on, Travis CI builds generate a test report from your test suite. Once generated, the test report is sent to Code Climate API. Code coverage metrics of your project are available on Code Climate dashboard. Also, all your GitHub pull requests will display your code coverage percentage alongside Code Climate checks.

> :information_source: You can grab a code coverage badge from Code Climate dashboard and add it to your `README`.

## Enable GitHub required status checks
Travis CI and Code Climate are really cool tools, but they become useless if one tries to bypass them. To avoid this you can enforce required status checks before a branch is merged in a pull request or before commits on a local branch can be pushed to the protected remote branch. To achieve it, you need to access your GitHub repository settings. Go to **Branches**. Under **Branch protection rules**, add a rule. Write `master` in the **Apply rule to** input field; this means that the rule will be applied to all branches. Check the following:

- `Require status checks to pass before merging`
- `Require branches to be up to date before merging`
  - `codeclimate`
  - `continuous-integration/travis-ci`
- `Include administrators`

From now on, you will need to make the checks pass in order to merge your pull requests.

## Configuring the checks

In some cases, Code Climate's checks can be a bit harsh. Luckily, some configuration options are available. You can turn of some checks in the **Repo settings > Maintainability**. This of course only lets you completely enable or disable checks, which is a bit counterproductive. A much better way is to create a `.codeclimate.yml` file, in which you can tune your configuration much more precisely ! A detailed explanation with examples can be found in the Code Climate [docs](https://docs.codeclimate.com/docs/advanced-configuration).