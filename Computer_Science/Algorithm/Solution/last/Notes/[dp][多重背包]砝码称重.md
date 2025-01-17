## 题解

### 题目说明

有w为1, 2, 3, 5, 10, 20的5种物品, 每个物品有$a_i$个, 求最后能组合成的数字有多少种

### 题解

通过二进制拆分法得出new_w数组, 通过new_n来记录新产生的物品有多少种, 需要去重, 然后通过dp[i]表示加入了前i个数字后能产生的最多组合数量, 这里的背包就没有容量限制了 

### 问题

做这道题碰到的一个很严重的问题就是我设计不来整个题目的结构, 这里是如何获取答案的结构, 在哪个地方获取答案, DP能帮助我得到什么, 最优子结构是什么, 如何通过这个得到的东西, 计算出来我最后的答案, 现在的想法过于僵化, 限于最简单的0/1背包问题

## code

```cpp
#include <iostream>
#include <algorithm>
#include <cstdio>
using namespace std;
int a[8];
int dp[1005];
int mass[8] = {0,1,2,3,5,10,20,0};
int new_n;
int new_w[100];

int main() {
    // freopen("input.txt", "r", stdin);
    for(int i=1;i<=6;i++) cin>>a[i];

    // 二进制拆分
    for (int i=1;i<=6;i++) {
        for (int j=1;j<a[i];j<<=1) {
            a[i] -= j;
            new_w[++new_n] = j*mass[i];
        }
        if (a[i]) {
            new_w[++new_n] = a[i]*mass[i];
        }
    }

    // 0/1背包, 计算所有可能得出的重量
    // dp[i] => 能否得出的重量, i : 重量
    dp[0] = 1;
    for (int i=1;i<=new_n;i++) {
        for (int j=1000;j>=new_w[i];j--) {
            if (dp[j-new_w[i]]) dp[j] = 1;
        }
    }
    int ans = 0;
    for (int i=1;i<=1000;i++) {
        if (dp[i]) ans++;
    }
    cout<<"Total="<<ans;
    return 0;
}
```

## 复杂度分析

### 时间复杂度

$O(1000log_2m_i)$, $m_i$为总共的砝码数量