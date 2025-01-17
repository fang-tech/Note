#include <iostream>
#include <cstdio>
#include <algorithm>
using namespace std;

int n, C; // 物品种类数, 最大容量
const int N = 100005;
int w[N], c[N], m[N];
int new_w[N], new_c[N]; // 二进制拆分后的新的价值和体积数组
int new_n; // 新生成的价值和体积数组的大小
int dp[N];

int main() {
    // freopen("input.txt", "r", stdin);
    cin>>n>>C;
    for (int i=1;i<=n;i++) cin>>w[i]>>c[i]>>m[i];

    // 二进制拆分
    for (int i=1;i<=n;i++) {
        for (int j=1;j<=m[i];j<<=1) {
            m[i] -= j;
            new_w[++new_n] = j*w[i];
            new_c[new_n] = j*c[i];
        }
        if (m[i]) {
            new_w[++new_n] = m[i]*w[i];
            new_c[new_n] = m[i]*c[i];
        }
    }

    // 对获取的到新的"物品"进行DP
    for (int i=1;i<=new_n;i++) { // 前i个物品
        for (int j=C;j>=new_c[i];j--) { // 容量为j
            dp[j] = max(dp[j], dp[j-new_c[i]] + new_w[i]);
        }
    }
    
    printf("%d\n", dp[C]);
    return 0;
}