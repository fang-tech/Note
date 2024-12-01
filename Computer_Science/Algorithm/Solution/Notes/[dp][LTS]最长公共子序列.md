## 题解

### 题目说明

有两个序列X, Y, 求两个序列的最长子序列

### 题解

首先考虑暴力的情况下需要多少时间复杂度, X有$2^n$个子序列, 则时间复杂度为$m*2^n$. DP\[i][j], i为x序列的index, j为Y序列的index , 则新增的元素X[i]

- 如果X[i] == Y[j], 则DP\[i][j] = DP\[i-1][j-1] + 1
- 如果不等于, 则DP\[i][j] = max {DP\[i][j-1], DP\[i-1][j]}

## code

```cpp
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
```

