# 题解

### 题目说明

存在数字序列X(23, 231, 4, 51 ,51 2 1....), 现在我们需要找出最长递增序列的长度

### 题解

很像使用单调栈的感觉, 比较, 一直出栈, 直到找到合适的位置

- 状态方程 : DP\[i] => 以第i个数结尾的递增子序列的的最长长度, 而不是**以第i个数字结尾的序列的最长递增子序列的长度**
- 状态转移方程
  - DP\[i] = max(DP\[i], DP[j] + 1), 1<= j < i <= n, 并且只需要在s[i] > s[j]的时候进行
  - 只有s[i] > s[j] 的时候, 我们才能将这个元素添加到这个最长递增子序列的末尾, 然后长度++

### 题目分析

-  最优子结构 : 
  - 求解以s[i]结尾的最长递增子序列的长度的时候, 如果我们知道了以所有s[j]结尾的最长递增子序列的长度 (j < i && s[i] > s[j]), 那么这个时候以s[i]结尾的递增子序列可以由以s[j]结尾的递增子序列拓展而来
- 重叠子问题 : (求解的思路是一样的, **DP本质上就是暴力的记忆化**)
  - 如果暴力求解, 那么计算以s[i]为结尾的最长递增子序列的时候需要计算前面的所有s[j]为结尾的最长递增子序列的长度 (s[j] < s[i]才要计算) 

### 对称问题

给定一个序列，求最少需要多少个递减子序列才能覆盖整个序列。

## code

```cpp
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
    freopen("input.txt", "r", stdin);
    while (cin>>n) {
        for (int i=1;i<=n;i++) cin>>s[i];
         // 初始化dp数组
        for (int i = 1; i <= n; i++) {
            dp[i] = 1;  // 每个数字单独都构成长度为1的LIS
        }
        for (int i=2;i<=n;i++) {
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
```

