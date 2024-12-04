#include <iostream>
#include <cstdio>
#include <algorithm>
using namespace std;

const int N = 100;
int dp[N][N];
int n;
char operation[N];
int bools[N];

int caculate(int l, int r, char operation) {
    if (l > 1) l = 1;
    if (r > 1) r = 1;

    if (operation == '^') return left^right;
    if (operation == '&') return left&right;
    if (operation == '|') return left|right;
}

int main() {
    cin>>n;
    for (int i=1;i<=n;i++) {
        char ch;
        cin>>ch;
        if (ch == 'F') bools[i]=0;
        else bools[i]=1;
    }
    for (int i=1;i<n;i++) cin>>operation[i];

    for (int i=n;i>=1;i--) {
        for (int j=i;j<=n;j++) {
            if (i == j) dp[i][j] = bools[i];
            if (j == i+1) dp[i][j] = caculate(bools[i], bools[j], operation[i]);
            for (int k=i;k<j;k++) {
                if (caculate(dp[i][k], dp[k+1][j], operation[k])) {
                    dp[i][j] *
                }
            }
        }
    }
    return 0;
}