/*
 * @lc app=leetcode.cn id=42 lang=java
 *
 * [42] 接雨水
 */

// @lc code=start

class Solution {
    public int trap(int[] height) {
        // 使用双指针维护左最高和右最高
        int left = 0, right = height.length - 1;
        int preMax = 0, sufMax = 0;// 前缀最高和后缀最高
        int ans = 0;
        while (left < right) {
            // 只计算左右指针相邻的那个格子
            // 在现有后缀最高大于前缀最高的时候, 这个时候, 
            // 左指针右边一格的格子, 它能接的雨水是由前缀决定的, 我们已经计算出来了它的前缀值
            // 这个时候就能计算出来它能解的雨水的值
            preMax = Math.max(preMax, height[left]);
            sufMax = Math.max(sufMax, height[right]);
            ans += preMax < sufMax
                    ? preMax - height[left++]
                    : sufMax - height[right--];
        }
        return ans;
    }

    // 使用后缀前缀数组
    public int trap1(int[] height) {
        // i位置能解的雨水, 取决于这个位置的前缀最矮和后缀最矮
        int n = height.length;
        int[] postArr = new int[n];
        int[] aheadArr = new int[n];
        // 计算前缀数组 
        aheadArr[0] = height[0]; postArr[n-1] = height[n-1];
        for (int i = 1 ; i < height.length; i++) {
            aheadArr[i] = Math.max(aheadArr[i-1], height[i]);
        }
        // 计算后缀数组
        for (int i = height.length - 2; i >= 0; i--) {
            postArr[i] = Math.max(postArr[i+1], height[i]);
        }

        // 接的雨水
        int ans = 0;
        for (int i = 0; i < height.length; i++) {
            ans += Math.min(postArr[i], aheadArr[i]) - height[i];
        }
        return ans;
    }
}
// @lc code=end

