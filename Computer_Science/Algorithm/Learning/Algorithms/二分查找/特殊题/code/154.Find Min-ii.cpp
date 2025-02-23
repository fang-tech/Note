/*
 * @lc app=leetcode.cn id=154 lang=cpp
 *
 * [154] 寻找旋转排序数组中的最小值 II
 */
#include <vector>
#include <iostream>

using namespace std;
// @lc code=start
class Solution {
public:
    int findMin(vector<int>& nums) {
        // 末尾的元素
        int end = nums.back();
        int repeat = nums.size() - 1;
        while (repeat >= 1 && nums[repeat] == nums[repeat-1]) repeat--;
        // 最小值及最小值左边的元素
        int left = -1;
        // 最小值右边的元素
        int right = nums.size() - 1;
        while (left < right - 1) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= end && mid < repeat){
                left = mid;
            } else {
                right = mid;
            }
        }
        return nums[right];
    }
};
// @lc code=end
int main() {
    vector<int> nums = {1,1};
    Solution s;
    cout << s.findMin(nums);
    return 0;
}

