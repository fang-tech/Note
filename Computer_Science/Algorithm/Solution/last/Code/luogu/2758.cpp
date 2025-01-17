#include <iostream>
#include <cstdio>
#include <algorithm>
using namespace std;

const int N = 2000 + 5;
char w1[N], w2[N];
int n, m;
int dp[N][N];

int main (){
    // freopen("input.txt", "r", stdin);
    scanf("%s\n%s", w1+1, w2+1);
    int i=1;
    while (w1[i++] != '\0') n++;
    i = 1;
    while (w2[i++] != '\0') m++;

    for (int i=0;i<=n;i++) dp[i][0]=i;
    for (int i=0;i<=m;i++) dp[0][i]=i;

    for (int i=1;i<=n;i++) {
        for (int j=1;j<=m;j++) {
            if (w1[i] == w2[j]) dp[i][j] = dp[i-1][j-1];
            else dp[i][j] = min({dp[i-1][j]+1, dp[i][j-1]+1, dp[i-1][j-1]+1});
            // cout << dp[i][j] << " ";
        }
        // cout << endl;
    }
    
    cout << dp[n][m];
}