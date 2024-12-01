#include <iostream>
#include <cstdio>
#include <algorithm>
#include <string>
#include <cstring>
using namespace std;

const int N = 1000 + 5;
int n,m;
string X, Y;
int dp[N][N];

int main() {
    // freopen("input.txt", "r", stdin);
    while (cin>>X>>Y) {
        memset(dp, 0, sizeof(dp));
        n = X.length(); m = Y.length();
        for (int i=1;i<=n;i++) {
            for (int j=1;j<=m;j++) {
                if (X[i-1] == Y[j-1]) dp[i][j] = dp[i-1][j-1] + 1;
                else {
                    dp[i][j] = max(dp[i][j-1], dp[i-1][j]);
                }
            }
        }
        printf("%d\n", dp[n][m]);
    }
}