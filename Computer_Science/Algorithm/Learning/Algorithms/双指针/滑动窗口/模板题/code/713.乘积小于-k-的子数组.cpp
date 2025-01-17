/*
 * @lc app=leetcode.cn id=713 lang=cpp
 *
 * [713] 乘积小于 K 的子数组
 */

#include <iostream>
#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    int numSubarrayProductLessThanK(vector<int>& nums, int k) {
        if (k <= 1) {
            return 0;
        }
        int left = 0;
        int n = nums.size();
        int ans = 0;
        int prof= 1;
        for (int right = 0; right < n; right++) {
            prof *= nums[right];
            while (prof >= k) { // 这里如果没有对于非法的k的排除的话, 就会一直出不去循环
                prof /= nums[left++];
            }
            ans += right - left + 1;
        }
        return ans;
    }
};
// @lc code=end

