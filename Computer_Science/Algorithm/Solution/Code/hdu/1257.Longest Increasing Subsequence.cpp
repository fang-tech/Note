#include <iostream>
#include <cstdio>
#include <cstring>
#include <algorithm>
using namespace std;
const int N = 1000;
int n;
int s[N];
int dp[N];

int main() {
    // freopen("input.txt", "r", stdin);
    while (cin>>n) {
        for (int i=1;i<=n;i++) cin>>s[i];
         // 初始化dp数组
        for (int i = 1; i <= n; i++) {
            dp[i] = 1;  // 每个数字单独都构成长度为1的LIS
        }
        for (int i=2;i<=n;i++) {
            dp[i] = max_element(dp+1, dp+i+1);
            for (int j=1;j<i;j++) {
                if (s[i] > s[j]) {
                    dp[i] = max(dp[i], dp[j] + 1);
                }
            }
        }

        int ans = 0;
        ans = *max_element(dp, dp+n+1);
        cout << ans << endl;
    }
    return 0;
}