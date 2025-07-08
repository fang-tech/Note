/*
 * @lc app=leetcode.cn id=53 lang=java
 *
 * [53] 最大子数组和
 */

// @lc code=start

class Solution {
    
    /**
     * f[i]: 以i结尾的最大子数组和
     * 两种情况: nums[i]就是一个数组的时候, 这个时候的f[i] = nums[i]
     *          num[i]和前面的子数组组成成最大值时候, f[i] = max(f[i-1], 0) + nums[i]
     */
    public int maxSubArray(int[] nums) {
        int[] f = new int[nums.length];
        f[0] = nums[0];
        int ans = nums[0];
        for (int i = 1; i < nums.length; i++) {
            f[i] = Math.max(f[i-1], 0) + nums[i];
            ans = Math.max(ans, f[i]);
        }
        return ans;
    }

    /**
     * 将问题转化成前缀和s[i] - s[j] 
     * 维护当前前缀和以及最小前缀和就能解决
     * @param nums
     * @return
     */
    public int maxSubArray1(int[] nums) {
        int ans = Integer.MIN_VALUE;
        int s = 0;
        int minS = 0;
        for (int num : nums) {
            s += num; // 当前前缀和
            ans = Math.max(ans, s - minS);
            minS = Math.min(s, minS); // 这个前缀和前面的最小的前缀和
        }
        return ans;
    }
}
// @lc code=end

