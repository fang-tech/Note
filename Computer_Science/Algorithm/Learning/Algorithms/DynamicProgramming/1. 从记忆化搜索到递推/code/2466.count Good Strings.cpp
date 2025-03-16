/*
 * @lc app=leetcode.cn id=2466 lang=cpp
 *
 * [2466] 统计构造好字符串的方案数
 */

// @lc code=start
#include <iostream>
#include <climits>
#include <vector>
using namespace std;
class Solution {
public:
    const int m = 1e9 + 7;
    vector<int> f;
    // dfs(n) : 走到字符串是这个长度的方案数量
    // 我们在最后构造到字符串的长度在low和high之间走的最后一步
    // 选择走zero : 则将问题缩小为dfs(n - zero)的方案数量
    // 选择走one : 则将问题缩小为dfs(n - one)的方案数量
    // int dfs(int i, int zero, int one) {
    //     if (i < 0) return 0;
    //     if (i == 0) return 1;

    //     int& res = memo[i];
    //     if (res != -1) return res;
    //     res = 0;

    //     res = (dfs(i - one, zero, one) + dfs(i - zero, zero, one)) % m;
    //     return res;
    // }

    int countGoodStrings(int low, int high, int zero, int one) {
        int ans = 0;
        f.resize(high + 1, 0);
        f[0] = 1;
        
        for (int i = 1; i <= high; i++) {
            if (i >= one) f[i] = (f[i] + f[i - one]) % m;
            if (i >= zero) f[i] = (f[i] + f[i - zero]) % m;
        }

        for (int i = low; i <= high; i++) 
            ans = (ans+ f[i]) % m;
        return ans;
    }
};
// @lc code=end
int main() {
    cout << INT_MAX;
    return 0;
}

