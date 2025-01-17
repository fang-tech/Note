#include <iostream>
#include <cstdio>
#include <algorithm>
using namespace std;
const int INF = 0x3f3f3f3f;
const int N = 110;
int dp[N][N];
int matrixs[N];
int n;

int main() {
    //@test
    freopen("input.txt", "r", stdin);
    cin>>n;
    for (int i=0;i<n;i++) {cin>>matrixs[i];}
    for (int i=n-1;i>=1;i--) {
        for (int j=i;j<=n-1;j++) {
            if (i == j) {dp[i][j] = 0; continue;}
            // 初始化dp矩阵值为默认的序列值, 这个值如果是最小, 说明不添加括号就是最优解, 如果不是最小, 则会被比下去
            dp[i][j] = INF;
            for (int k=i;k<j;k++) {
                dp[i][j] = min(dp[i][j], dp[i][k]+dp[k+1][j]+matrixs[i-1]*matrixs[k]*matrixs[j]);
            }
        }
    }

    //@test
    // for (int i=1;i<=n-1;i++) {
    //     cout << i << "\t";
    //     for (int j=1;j<=n-1;j++) {
    //         cout << dp[i][j] << "\t";
    //     }
    //     cout << "\n";
    // }
    cout << dp[1][n-1];
    return 0;
}