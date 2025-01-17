/*
 * @lc app=leetcode.cn id=1658 lang=cpp
 *
 * [1658] 将 x 减到 0 的最小操作数
 */

#include <vector>
#include <numeric>
using namespace std;
// @lc code=start
class Solution {
public:
    /**
     * 正难则反, 将问题转化为求数组中最长的和为sum - x的连续子数组
     */
    int minOperations(vector<int>& nums, int x) {
        int target = accumulate(nums.begin(), nums.end(), 0) - x;
        if (target < 0) return -1;
        int left = 0;
        int sum = 0;
        int ans = -1;
        int n = nums.size();
        for (int right = 0; right < n; right++) {
            sum += nums[right];
            while (sum > target) {
                sum -= nums[left++];
            }
            if (sum == target) {
                ans = max(ans, right - left + 1);
            }
        }
        return ans < 0 ? -1 : n - ans;
    }
};
// @lc code=end


