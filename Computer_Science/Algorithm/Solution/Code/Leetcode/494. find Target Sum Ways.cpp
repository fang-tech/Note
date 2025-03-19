/*
 * @lc app=leetcode.cn id=494 lang=cpp
 *
 * [494] 目标和
 */

#include <iostream>
#include <vector>
#include <numeric>
using namespace std;
// @lc code=start
class Solution {
public:


    /**
     * dfs(i, t), 从0选到第i个元素的时候, 目标和是t的时候的方案数
     * dfs(i ,t) = dfs(i-1, t) + dfs(i-1, t-nums[i])
     * 第i-1个元素不选的时候的方案数 + 第i-1个元素选的时候的方案数
     */
    vector<vector<int>> memo;
    int dfs(vector<int>& nums, int i, int target) {
        if (i < 0) return target == 0;
        
        int& res = memo[i][target];
        if (res != -1) return res;
        res = 0;
        
        // 背包中还有容量
        if (target - nums[i] > 0)  
            res = dfs(nums, i-1, target) + dfs(nums, i-1, target-nums[i]);
        else // 背包中没有容量了, 这个物品不能选
            res = dfs(nums, i-1, target);
        return res;
    }

    int findTargetSumWays(vector<int>& nums, int target) {
        /**
         * 选作是正数的数和是p,非正数的和就是s - p
         * 则 p - (s - p) = t
         * 2p = t + s
         * 从数组中选择一系列数, 让它们的和为(t + s) / 2, 这个时候的(t + s) / 2 就是目标值
         */
        int sum = accumulate(nums.begin(), nums.end(), 0);
        memo.resize(nums.size(), vector<int>((sum + target) / 2 + 1, -1));
        if ((sum + target) % 2) {
            return 0;
        } else {
            return dfs(nums, nums.size()-1, (sum + target) / 2);
        }
    }
};
// @lc code=end
int main() {
    Solution s;
    int target = 3;
    vector<int> nums = {1,1,1,1,1};
    cout << s.findTargetSumWays(nums, target);
    return 0;
}

