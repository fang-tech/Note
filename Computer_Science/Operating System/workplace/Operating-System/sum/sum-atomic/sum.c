#include "thread.h"
#include <assert.h>

#define N 100000000
#define UNLOCK 1
#define LOCK 0

int status = LOCK;

void lock() {
retry:
    int got = atomic_xchg(&status, LOCK);
    if (got != UNLOCK) {
        goto retry;
    }
}

void unlock() {
    atomic_xchg(&status, UNLOCK);
}

long sum = 0;

void T_sum() {

    for (int i = 0; i < N; i++) {
        sum++;
    }

}

int main() {
    while (1) {
        create(T_sum);
        create(T_sum);

        join();

        printf("sum = %ld\n", sum);
        printf("2*n = %ld\n", 2L * N);

        assert(sum != 2*N);
        sum = 0;
    }
}
