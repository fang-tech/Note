/*
 * @lc app=leetcode.cn id=2779 lang=cpp
 *
 * [2779] 数组的最大美丽值
 */

#include <vector>
#include <algorithm>
using namespace std;
// @lc code=start
class Solution {
public:
    int maximumBeauty(vector<int>& nums, int k) {
        int left = 0;
        int ans = 0;
        sort(nums.begin(), nums.end());
        for (int right = 0; right < nums.size(); right++) {
            if (nums[right] - nums[left] <= 2*k) {
                ans = max(ans ,right - left + 1);
            } else {
                left++;
            }
        }
        return ans;
    }
};
// @lc code=end

