/*
 * @lc app=leetcode.cn id=34 lang=cpp
 *
 * [34] 在排序数组中查找元素的第一个和最后一个位置
 */

#include <iostream>
#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:

    int lower_bound(vector<int>& nums, int target) {
        // 找到数组中第一个>=target的数字
        // 第一种写法, [left, right]
        int left = 0;
        int right = nums.size() - 1;
        while (right >= left) {
            // 用于避免+法导致的爆int
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }
    int lower_bound1(vector<int>& nums, int target) {
        // 找到数组中第一个>=target的数字
        // 第二种写法, (left, right], 这个的问题在于是向下取整, 如果是向上取整就是正确的
        int left = -1;
        int right = nums.size() - 1;
        while (right != left) {
            // 用于避免+法导致的爆int
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left+1;
    }
    int lower_bound2(vector<int>& nums, int target) {
        // 找到数组中第一个>=target的数字
        // 第三种写法, (left, right)
        int left = -1;
        int right = nums.size();
        while (right > left+1) {
            // 用于避免+法导致的爆int
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return right;
    }



    vector<int> searchRange(vector<int>& nums, int target) {
        int start = lower_bound1(nums, target);
        // 因为找到的是>=的情况, 所以还要排除不是等于的情况
        if (start == nums.size() || nums[start] != target){
            return {-1, -1};
        }
        int end = lower_bound1(nums, target+1)-1;
        // 数组中一定存在target
        return {start, end};
    }
};
// @lc code=end
int main() {
    vector<int> nums = {5,7,7,8,8,10};
    Solution s;
    vector<int> ans = s.searchRange(nums, 8);
    for (int val : ans) {
        cout << val << " ";
    }
}

