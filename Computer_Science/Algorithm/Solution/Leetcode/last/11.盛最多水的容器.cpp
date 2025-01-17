/*
 * @lc app=leetcode.cn id=11 lang=cpp
 *
 * [11] 盛最多水的容器
 */

#include <iostream>
#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    int maxArea(vector<int>& height) {
        int left = 0;
        int right = height.size()-1;
        int ans;
        while (left < right) {
            int s = (right - left) * min(height[left], height[right]);
            if (height[left] > height[right]) {
                right--;
            }
            else {
                left++;
            }
            ans = max(s, ans);
        }
        return ans;
    }
};
// @lc code=end

