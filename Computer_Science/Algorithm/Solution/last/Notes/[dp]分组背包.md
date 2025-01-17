# hdu 1712  [ACboy needs your help](https://acm.hdu.edu.cn/showproblem.php?pid=1712)

## 题目说明 : 

### 题目 

- 可以选N门课, 一共可以上M天课, A\[i][j]表示第n门课上m天得到的学分, 现在需要学分最大化

### 问题建模

- 分组0/1背包, 与原模型0/1背包对比, 区别在于原本的dp\[i][j]状态是选择前i个物品背包容量为j时的最大价值, 改为选择前i组物品背包容量为j时的最大价值, 然后在循环中, 需要在每个j中额外去遍历每组的物品, 以选择能达到最优情况的物品
- 因为在能放的下的时候, 放入物品肯定是这个时候的最优解, 所以是不需要比较不放入的时候, 一定是大于等于的

### code

```cpp
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
```

