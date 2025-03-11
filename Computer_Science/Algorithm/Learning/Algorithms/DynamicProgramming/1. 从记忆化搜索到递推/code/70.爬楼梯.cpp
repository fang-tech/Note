/*
 * @lc app=leetcode.cn id=70 lang=cpp
 *
 * [70] 爬楼梯
 */

// @lc code=start
class Solution {
public:

    int climbStairs(int n) {
        int f0 = 0, f1 = 1;
        for (int i = 0; i < n; i++) {
            int f_new = f0 + f1;
            f0 = f1;
            f1 = f_new;
        }

        return f1;
    }
};
// @lc code=end

