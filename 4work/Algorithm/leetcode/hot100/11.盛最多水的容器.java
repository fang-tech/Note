/*
 * @lc app=leetcode.cn id=11 lang=java
 *
 * [11] 盛最多水的容器
 */

// @lc code=start
class Solution {
    public int maxArea(int[] height) {
        int ans = 0;
        int right = height.length - 1, left = 0;
        while (left < right) {
            // 能接的水的容量取决于left和right之间短的一边
            // 移动长的一边, 会导致短板不变, 但是x轴缩短, 
            // 所以最大容量一定出现在移动长的一边的时候
            int cap = Math.min(height[left], height[right]) * (right - left);
            ans = Math.max(cap, ans);
            if (height[left] > height[right])
                right--;
            else
                left++;
        }
        return ans;
    }
}
// @lc code=end

