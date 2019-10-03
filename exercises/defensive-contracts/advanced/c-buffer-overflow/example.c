#include <stdio.h>
#include <string.h>

#define MY_STRING_LEN       (7)
#define MY_STRING_SHORT_LEN (2)

int main(void) {
    char my_string[MY_STRING_LEN + 1] = "Welcome";
    char my_string_short[MY_STRING_SHORT_LEN + 1];

    printf("my_string       = %s\n", my_string);
    printf("my_string_short = %s\n", my_string_short);

    // strcpy(my_string_short, my_string);
    strncpy(my_string_short, my_string, MY_STRING_SHORT_LEN);

    printf("my_string       = %s\n", my_string);
    printf("my_string_short = %s\n", my_string_short);
}
