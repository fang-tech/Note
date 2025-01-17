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
        int left = 0;
        int right = nums.size() - 2;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[mid+1]) {
                left = mid + 1; 
            } else {
                right = mid - 1;
            }
        }

        return left;
    }
};
// @lc code=end

