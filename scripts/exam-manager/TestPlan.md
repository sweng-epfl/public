# Test plan

In addition to unit tests, the following plan describes how to end-to-end test this script.
Since the script uses a bunch of external services whose API is subject to change, automated end-to-end testing of just the script wouldn't be helpful
as the point is not to tell if we made the "right" calls to external services but whether these calls had the expected effects.

## Setup

1. Get a GitHub token, save it to `github-token.txt`
2. Create a disposable email (or use GMail's `+` aliasing features, or another email, you own, etc.)
3. Create a GitHub org for testing, with "no permissions" as base permissions in the member privilege settings
4. Create a "staff" team in that org
5. Create a private repo in that org that contains a fake exam with one theory and one practice question, a .gitignore, GitHub Actions that passes, and a top-level readme
6. Create two assignments on Gradescope, to match the two fake questions
7. Create a `users.csv` file with one user, whose email is the created disposable email
8. Create a `config.json` with the appropriate values

## Test

Any step that doesn't start with "manually" or "check" is implicitly "with the exam manager".

1. Generate a Gradescope roster
2. Check that Gradescope accepts it
3. Publish the exam
4. Check that the user got an email inviting them
5. Manually decline the invite
6. Reinvite
7. Check that the user got an invite, accept it, and check that they cannot see the exam repo
8. Show the exam
9. Check that the user has write access to their exam repo, including GitHub Actions passing, the .gitignore, and the top-level readme
10. Hide the exam
11. Check that the user can no longer see it
12. Check that the "find unchanged" command returns nobody (since the exam is hidden)
13. Show the exam again
14. Check that the user can see it, without an extra invite
15. Check that the "find unchanged" command returns the user; you might need to wait a minute and use 1 as argument
16. Check that the "find failing CI" command returns nobody
17. Manually make some changes to both questions in the user's exam
18. Check that the "find unchanged" command returns nobody (since the user's repo changed)
19. Manually make a change to a file in the skeleton exam repo to break CI
20. Publish that file change (using the command marked as "dangerous")
21. Check that the user's exam repo has a new commit overwriting that file, without other changes
22. Check that the "find failing CI" command returns the user (since the change broke CI)
23. Freeze the exam
24. Check that the user can still see its exam repo, but cannot write to it
25. Try to publish again, and check that there is a warning that the user can still see the exam
26. Upload to Gradescope
27. Check that the data is on Gradescope, but only the changed files and only the changed part of the file for the theory question
28. Manually grade on Gradescope and download the files as instructed in the exam manager's documentation
29. Publish the grades
30. Check that the grade reports look good
31. Clean up
32. Check that the org has been cleaned up
33. Manually delete the local `repos/` folder, so the script is ready to use with a "real" config
