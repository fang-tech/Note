# P60.[排列序列](https://leetcode.cn/problems/permutation-sequence/description/)

## 问题描述

传入n, k, 求第k个全排列, 排列的数字有n个(1 ~ n)

## 题解

- dfs遍历 + 可行性减枝 + 回溯法, 遍历的过程中, 在第x层的时候(1<= x <= n)可以知道这个时候的子情况有${(n-x)}!$个, 因为遍历的时候是从 1 - n 递归的, 天然的就是从小的到大的排序. 易得最后的解一定是在叶子节点的位置, 故进入分支的时候, 可以计算这个分支能产生的解数, k 是否大于这个分支能产生的解数, 如果大于, 则必然不在这个分支的子树下, 则通过回溯进入同层的下一个分支
- 不过通过这个思路, 压根就不需要dfs了, 通过这个减枝方法, 能直接算出来的最后的排序

## code

```cpp
/*
 * @lc app=leetcode.cn id=60 lang=cpp
 *
 * [60] 排列序列
 */
#include <vector>
#include <iostream>
#include <string>
using namespace std;
// @lc code=start
const int N = 1e1 + 5;
class Solution {
private:
    int factorial(int n) {
        if (n <= 0) return 1;
        int sum = 1;
        for (int i = 1; i <= n; i++) {
            sum *= i;
        }
        return sum;
    }

    void mathCaculation(int n, int k, string& ans, int* used) {
        for (int i = n; i >= 1; i--) {
            int f = factorial(i-1);
            int x = k / f;
            if (k % f == 0) x = x - 1;
            int t = x % n;
            k = k - x*f;
            int now = 0;
            while (used[++now]);
            for (int j = 0; j < t; j++) {
                if (now > n) now = 1;
                while (used[++now]);
            }
            used[now] = 1;
            ans += to_string(now);
            // printf("now = %d, x = %d, k = %d, f = %d\n", now, x, k, f);
        }
    } 

    void dfs(int n, int k, vector<int> path, string& ans, int* used, int& counter) {
        if (counter == k) return;
        if (path.size() == n) {
            // 找到了一种排列
            counter++;
            // @test
            {
                // printf("第%d种排列为 : ",counter);
                for (int val : path) cout << val;
                cout << "\n" ;
            }
            if (counter == k) {
                for (int val : path) {
                    ans += to_string(val);
                }
            }
            return;
        }
        for (int i = 1; i <= n; i++) {
            if (used[i]) continue;
            path.push_back(i);
            used[i] = 1;
            dfs(n, k, path, ans, used, counter);
            used[i] = 0;
            path.pop_back();
        }
    }
public:
    string getPermutation(int n, int k) {
        int used[N] = {0};
        used[0] = 0;
        string ans;
        // int counter = 0;
        // vector<int> path;
        // dfs(n, k, path, ans, used, counter);
        mathCaculation(n, k, ans, used);
        return ans;
    }
};
// @lc code=end
int main() {
    Solution s;
    int n, k;
    n = 9; k = 531679428;
    string result = s.getPermutation(n,k);
    cout << "\n最终的结果为" << result;
    return 0;
}
```

