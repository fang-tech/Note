/*
 * @lc app=leetcode.cn id=33 lang=cpp
 *
 * [33] 搜索旋转排序数组
 */
#include <vector>
#include <iostream>

using namespace std;

// @lc code=start
class Solution {
public:
    // 对应着right, 当前元素是target或者target的右侧
    bool is_blue(vector<int>& nums, int target, int d) {
        int end = nums.back();
        int x =  nums[d];
        if (target > end) {
            return x <= end || x >= target;
        }
        return x <= end && x >= target;
    }

    int search(vector<int>& nums, int target) {
        // 红色和蓝色
        int left = -1, right = nums.size();
        while (left < right - 1) {
            int mid = left + (right - left) / 2;
            (is_blue(nums, target, mid) ? right : left) = mid;
        }

        return nums[right] == target ? right : -1;
    }
};
// @lc code=end
int main() {

    Solution s;
    vector<int> nums = {4,5,6,7,0,1,2};
    int target = 0;
    cout << s.search(nums,target);
    return 0;
}

