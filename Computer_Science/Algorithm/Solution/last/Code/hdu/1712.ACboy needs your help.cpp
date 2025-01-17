#include <algorithm>
#include <iostream>
#include <cstring>
#include <stdio.h>
using namespace std;

const int N = 1e2 + 5;
int A[N][N];
int n, m;
int dp[N];

int main() {
    // freopen("input.txt", "r", stdin);
    while (scanf("%d %d", &n, &m)==2 &&  !(n==m && n == 0)) {
        memset(dp, 0, sizeof(dp));
        for (int i=1;i<=n;i++) {
            for (int j=1;j<=m;j++) cin>>A[i][j];
        }
        
        // DP
        for (int i=1;i<=n;i++) { // 放入前i组物品
            for (int j=m;j>=0;j--) { // 背包的容量是j
                for (int k=1;k<=m;k++) { // 第i组物品的第k个物品, worth = A[i][k], value = k, 得出这组物品中最有情况
                    if (k <= j) dp[j] = max({dp[j], dp[j-k] + A[i][k]}); // 放得下这个物品的时候, 分为不放和放
                }
                // cout << dp[i][j] << " ";
            }
            // cout << endl;
        }

        printf("%d\n", dp[m]);
    }
    return 0;
}