/*
 * @lc app=leetcode.cn id=153 lang=cpp
 *
 * [153] 寻找旋转排序数组中的最小值
 */

#include <vector>

using namespace std;
// @lc code=start
class Solution {
public:
    int findMin(vector<int>& nums) {
        // min的左边
        int left = -1;
        // min及min的右边
        int right = nums.size();
        int x = nums.back();
        while (left < right - 1) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > x) // 同时说明了mid位置的元素一定不是最小值
                left = mid;
            else   
                right = mid;
        }
        return nums[right];
    }
};
// @lc code=end

