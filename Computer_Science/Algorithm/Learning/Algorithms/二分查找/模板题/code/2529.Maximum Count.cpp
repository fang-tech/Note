/*
 * @lc app=leetcode.cn id=2529 lang=cpp
 *
 * [2529] 正整数和负整数的最大计数
 */

#include <iostream>
#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    int lower_bound(vector<int>& nums, int target){
        int left = 0, right = nums.size() - 1;
        while (left <= right){
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return left;
    }

    int maximumCount(vector<int>& nums) {
        int pos = nums.size() -  lower_bound(nums, 1);
        int neg = lower_bound(nums, 0);
        return max(pos, neg);
    }
};
// @lc code=end

int main() {
    vector<int> nums = {-3,-2,-1,0,0,1,2};
    Solution s;
    cout << s.maximumCount(nums);
    return 0;
}

