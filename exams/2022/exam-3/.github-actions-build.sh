# !!!!!!!!!!!!!!!!!!!!!!
# DO NOT TOUCH THIS FILE
# !!!!!!!!!!!!!!!!!!!!!!

# Ensure all gradlew files are executable
find . -name gradlew | xargs chmod +x

# Build and run all tests
error=''
for q in Q4 Q5; do
  cd "$q"
    ./gradlew build --no-daemon
    if [ $? -ne 0 ]; then
      error="${error}- COULD NOT BUILD $q\n"
    fi
  cd -
done

if [ "$error" != '' ]; then
  printf '\n\n'
  printf '!!!!!!!!!!!\n'
  printf 'BUILD ERROR:\n'
  printf '%b' "$error"
  printf 'CHECK THE BUILD FAILURES ABOVE\n'
  printf '(Gradle is configured to fail if tests do not pass, to make sure you are aware of test failures)\n'
  printf '!!!!!!!!!!!\n'
  exit 1
fi
