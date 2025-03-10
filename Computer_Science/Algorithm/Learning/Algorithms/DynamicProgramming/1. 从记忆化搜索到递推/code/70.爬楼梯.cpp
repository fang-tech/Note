/*
 * @lc app=leetcode.cn id=70 lang=cpp
 *
 * [70] 爬楼梯
 */

#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    vector<int> f;

    int climbStairs(int n) {
        f.resize(n + 2);
        f[0] = 0, f[1] = 1;
        for (int i = 0; i < n; i++) {
            f[i+2] = f[i] + f[i+1];
        }
        return f[n+1];
    }
};
// @lc code=end

