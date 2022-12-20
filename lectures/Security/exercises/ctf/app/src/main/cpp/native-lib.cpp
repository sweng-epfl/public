#include <jni.h>
#include <string>

#define FLAG_SIZE 22
#define BUFFER_SIZE 420

extern "C" JNIEXPORT jstring JNICALL
Java_ch_epfl_sweng_ctf_repositories_ChallengesRepositoryImpl_nativeString(
        JNIEnv *env,
        jobject /* this */,
        jstring userInput,
        jstring flag) {
    char flag_buf[FLAG_SIZE];
    char buf[BUFFER_SIZE];
    buf[BUFFER_SIZE - 1] = '\0';
    const char *_flag = env->GetStringUTFChars(flag, nullptr);
    const char *_userInput = env->GetStringUTFChars(userInput, nullptr);
    strncpy(flag_buf, _flag, FLAG_SIZE);
    strncpy(buf, _userInput, BUFFER_SIZE);
    env->ReleaseStringUTFChars(userInput, _userInput);
    env->ReleaseStringUTFChars(flag, _flag);
    flag_buf[FLAG_SIZE - 1] = '\0';
    return env->NewStringUTF(buf);
}