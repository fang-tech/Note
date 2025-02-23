/*
 * @lc app=leetcode.cn id=2439 lang=cpp
 *
 * [2439] 最小化数组中的最大值
 */
#include <algorithm>
#include <vector>

using namespace std;

// @lc code=start
class Solution {
public:
    
    bool check(vector<int>& nums, int target) {
        // 第一个数字无法执行减操作, 所以这个数字如果是非法的
        // 最终的结果一定是非法的
        if (nums[0] > target) return false;
        // 中间变量, 用于拓展数字范围, 防止在加法的过程中出现溢出的现象
        // 具体的含义是i+1位的数字给第i位数字留下来的需要加的数字
        long long extra = 0;
        // 需要从后往前遍历, 因为会出现不断将值往前叠加的情况
        for (int i = nums.size() - 1; i >= 1; i--) {
            extra += nums[i];
            if (extra > target) {
                extra = extra - target;
            } else {
                extra = 0;
            }
        }
        if (extra + nums[0] >  target) return false; 
        else return true;
    }

    int minimizeArrayValue(vector<int>& nums) {
        // false
        int left = -1;
        // true
        int right = *max_element(nums.begin(), nums.end()) + 1;
        while (left < right - 1) {
            int mid = left + (right - left) / 2;
            (check(nums, mid) ? right : left ) = mid;
        }

        return right;
    }
};
// @lc code=end

