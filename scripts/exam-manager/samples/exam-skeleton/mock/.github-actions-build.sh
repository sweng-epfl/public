# !!!!!!!!!!!!!!!!!!!!!!
# DO NOT TOUCH THIS FILE
# !!!!!!!!!!!!!!!!!!!!!!

# Build and run tests
./gradlew build --no-daemon
if [ $? -ne 0 ]; then exit 1; fi
