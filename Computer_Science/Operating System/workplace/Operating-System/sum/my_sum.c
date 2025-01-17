#include "thread.h"
#include <assert.h>

#define OK 1
#define DENIED 0;

int status = OK;

void lock() {
retry :
    if (status != OK) {
        goto retry;
    }
    status = DENIED;
}

void unlock() {
    status = OK;
}

const int N = 1000000;
long sum = 0;

void T_sum(){
    asm volatile(
        "lock addq $1, %0" : "+m"(sum)
    );
}

int main(){
    while (1)  {
        create(T_sum);
        create(T_sum);

        join();

        printf("sum = %ld\n", sum);
        printf("2 * N = %d\n", 2*N);

        assert(sum != 2*N);
        sum = 0;
    }

    return 0;
}