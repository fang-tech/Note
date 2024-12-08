#include <iostream>
#include <cstdio>
#include <utility>
using namespace std;

const int N = 355;
const int M = 125;
int value[N];
int count[5];
int dp[N][M][2]; // [0]记录加和的值, [1]记录现在跳到的位置
int card[M], ca=0;
int n, m;
int sum = 0;

int main() {
    freopen("input.txt", "r", stdin);
    cin>>n>>m;
    for (int i=0;i<=n-1;i++) cin>>value[i];
    for (int i=1;i<=m;i++) {int t; cin>>t;count[t]++;}
    // 二进制拆分
    card[0] = 0;
    for (int i=1;i<=4;i++) {
        // 四种卡片, 拆分四次
        for (int j=1;count[i]>=j;j<<=1) {
            count[i] -= j;
            card[++ca] = j * i;
        }
        if (!count[i]) card[++ca] = count[i];
    }

    // 初始化dp数组, 0张卡片和距离为0的时候都为value[0]
    for (int i=0;i<=n-1;i++) {dp[i][0][0] = dp[0][i][0] = value[0]; dp[i][0][1] = dp[0][i][1] = 0;}

    for (int i=1;i<=n-1;i++) { // 与起点的距离
        for (int j=1;j<=ca;j++) { // 前n张卡片
            if (card[j] > i) dp[i][j][0] = dp[i][j-1][0], dp[i][j][1] = dp[i][j-1][1];
            else {
                int old = dp[i][j-1][0]; int _new = dp[i-card[j]][j-1][0] + value[dp[i-card[j]][j-1][1]+card[j]];
                if (old <= _new) {
                    dp[i][j][0] = _new;
                    dp[i][j][1] = dp[i-card[j]][j-1][1]+card[j];
                }else {
                    dp[i][j][0] = old;
                    dp[i][j][1] = dp[i][j-1][1];
                }
            }
        }
    }
    for (int i=0;i<=n-1;i++) {
        for (int j=0;j<=ca;j++) {
            cout << dp[i][j][0] << " ";
        }
        cout << endl;
    }

    for (int i=1;i<=n-1;i++) {
        for (int j=1;j<=ca;j++) {
            cout << dp[i][j][1] << " ";
        }
        cout << endl;
    }
    cout << dp[n-1][ca][0];
    return 0;
}