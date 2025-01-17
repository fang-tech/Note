#include <iostream>
#include <cstdio>
#include <algorithm>
using namespace std;

const int N = 105;
int n;
int dp[N][2], a[N]; // dp[N][0] : 含下降, dp[N][1] : 全上升

int main() {
    freopen("input.txt", "r", stdin);
    cin>>n;
    for (int i=1;i<=n;i++) cin>>a[i];

    for (int i=1;i<=n;i++) {
        dp[i][1] = 1;
        for (int j=i-1;j>=1;j--) {
            if (a[i] > a[j]) dp[i][1] = max(dp[i][1], dp[j][1]+1);
        }
    }

    for (int i=1;i<=n;i++) {
        dp [i][0] = 1;
        for (int j=i-1;j>=1;j--) {
            if (a[i] < a[j]) dp[i][0] = max(dp[i][0], max(dp[j][0], dp[j][1])+1);
        }
    }

    int ans = 0;
    for (int i=1;i<=n;i++) {
        // cout << dp[i][0] << " " << dp[i][1] << "\t";
        ans = max(ans, max(dp[i][0], dp[i][1]));
    }


    cout << n - ans;
}