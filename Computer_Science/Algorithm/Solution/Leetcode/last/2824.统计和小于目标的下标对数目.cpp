/*
 * @lc app=leetcode.cn id=2824 lang=cpp
 *
 * [2824] 统计和小于目标的下标对数目
 */

#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;
// @lc code=start
class Solution {
public:
    int countPairs(vector<int>& nums, int target) {
        int n = nums.size();
        int ans = 0;
        sort(nums.begin(), nums.end());
        int l = 0;
        int r = n - 1;
        while (r > l) {
            int s = nums[r] + nums[l];
            if (s < target) {
                ans += r - l;
                l++;
            } else {
                r--;
            }
        }
        return ans;
    }
};
// @lc code=end

