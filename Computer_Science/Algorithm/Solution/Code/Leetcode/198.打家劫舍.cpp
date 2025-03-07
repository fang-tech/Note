/*
 * @lc app=leetcode.cn id=198 lang=cpp
 *
 * [198] 打家劫舍
 */

#include <climits>
#include <vector>
#include <algorithm>
using namespace std;
// @lc code=start
class Solution {
public:
    int rob(vector<int>& nums) {
        int n = nums.size();
        vector<int> dp(n + 2);
        for (int i = 0; i < n; i++) {
            dp[i+2] = max(dp[i+1], dp[i] + nums[i]);
        }
        return dp[n+1];
    }
};
// @lc code=end

