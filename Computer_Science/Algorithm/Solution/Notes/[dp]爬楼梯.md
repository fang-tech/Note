# P70 [爬楼梯](https://leetcode.cn/problems/climbing-stairs/description/)

## 自顶向下 记忆化递归

```cpp
#include <iostream>
#include <stdio.h>
using namespace std;
// @lc code=start
const int N = 50;
int f[N];
class Solution {
private:
    int fib(int n) {
        if (n==1 || n==2) return n;
        if (f[n] == 0) f[n] = fib(n-1) + fib(n-2);
        return f[n];
    }
public:
    int climbStairs(int n) {
        return fib(n);
    }
};
```

## 自底向上 制表递推

```cpp
#include <iostream>
#include <stdio.h>
using namespace std;
// @lc code=start
const int N = 50;
class Solution {
private:
int dp[N];
public:
    int climbStairs(int n) {
        dp[1] = 1; dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }
};
```

### 滚动数组

```cpp
#include <iostream>
#include <stdio.h>
using namespace std;
// @lc code=start
const int N = 50;
class Solution {
private:
int dp[3];
public:
    int climbStairs(int n) {
        if (n==1 || n==2) return n;
        dp[0] = 1; dp[1] = 2;
        for (int i = 3; i <= n; i++) {
            dp[2] = dp[1] + dp[0];
            dp[0] = dp[1]; dp[1] = dp[2];
        }
        return dp[2];
    }
};
```

