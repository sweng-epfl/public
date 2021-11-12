# !!!!!!!!!!!!!!!!!!!!!!
# DO NOT TOUCH THIS FILE
# !!!!!!!!!!!!!!!!!!!!!!

# Ensure all gradlew files are executable
find . -name gradlew | xargs chmod +x

# Build and run all tests
cd Q4
  ./gradlew build --no-daemon
cd -
cd Q5
  ./gradlew build --no-daemon
cd -
