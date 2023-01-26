#include <jni.h>

jint Java_com_app_example_domain_MainRepository_addNumbers(JNIEnv* env, jobject obj, jint a, jint b) {
    return (a + b);
}