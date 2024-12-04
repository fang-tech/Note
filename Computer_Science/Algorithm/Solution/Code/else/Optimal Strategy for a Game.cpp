// 有n堆硬币, 价值数组v[n], n为偶数, 
// 每次只能在现在的硬币堆的第一堆和最后一堆拿硬币, 
// 现在你是先手拿硬币的人, 给出你能拿到的最大价值
// dp[i][j] => 第i堆硬币到第j堆硬币你先手拿硬币能拿到的最大价值
// 输入 :n
//       v[n]

#include <iostream>
#include <cstdio>
#include <algorithm>
using namespace std;

const int N = 100;
int v[N];
int dp[N][N];
int n;

int main() {
    //@test
    freopen("input.txt", "r", stdin);
    cin >> n;
    for (int i=1;i<=n;i++) cin>>v[i];

    for (int i=n;i>=1;i--) {
        for (int j=i;j<=n;j++) {
            if (i == j) dp[i][j] = v[i];
            else if (j == i + 1) dp[i][j] = max(v[i], v[j]);
            else dp[i][j] = max({v[j]+min(dp[i+1][j-1], dp[i][j-2]),  // 拿了j
            v[i]+min(dp[i+1][j-1], dp[i+2][j])}); // 拿了i
        }
    }
    //@test
    for(int i=1;i<=n;i++) {
        for (int j=1;j<=n;j++) {
            cout << dp[i][j] << "\t";
        }
        cout << endl;
    }
    cout << dp[1][n];
    return 0;
}