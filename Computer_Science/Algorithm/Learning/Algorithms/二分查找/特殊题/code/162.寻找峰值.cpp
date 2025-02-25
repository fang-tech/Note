/*
 * @lc app=leetcode.cn id=162 lang=cpp
 *
 * [162] 寻找峰值
 */
#include <vector>

using namespace std;
// @lc code=start
class Solution {
public:
    int findPeakElement(vector<int>& nums) {
        // true , 峰值及左边的元素
        int left = 0;
        // false , 峰值右边的元素
        int right = nums.size();
        while (left < right - 1) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[mid - 1]) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }
};
// @lc code=end

