/*
 * @lc app=leetcode.cn id=2302 lang=cpp
 *
 * [2302] 统计得分小于 K 的子数组数目
 */

#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    /**
     * 时间复杂度 : O(n)
     * 题目的关键在于怎么有窗口内的子数组得出这个时候有几个子数组是满足条件的
     */
    long long countSubarrays(vector<int>& nums, long long k) {
        int left = 0;
        long long sum = 0;
        long long score = 0;
        long long ans = 0;
        for (int right = 0; right < nums.size(); right++) {
            sum += nums[right];
            score = sum * (right - left + 1);
            
            // 时刻维持着窗口内的分数是严格小于k的
            while (score >= k) {
                sum -= nums[left++];
                score = sum * (right - left + 1);
            }
            // 窗口内的子数组可以划分为窗口内元素个数个子数组
            if (score < k) {
                ans += right - left + 1;
            }
        }
        return ans;
    }
};
// @lc code=end


