/*
 * @lc app=leetcode.cn id=238 lang=java
 *
 * [238] 除自身以外数组的乘积
 */

// @lc code=start


class Solution {
    /**
     * 最简单粗暴的方法是直接计算前后缀数组, 然后再遍历一遍
     * 更快的方法是将计算出来后缀数组以后, 将前缀数组的值直接乘到后缀数组上
     * @param nums
     * @return
     */
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int preSum = 1;
        int[] sufSum = new int[n];

        // 计算后缀数组
        sufSum[n-1] = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            sufSum[i] = sufSum[i+1] * nums[i+1];
        }

        // 计算前缀数组
        for (int i = 0; i < nums.length; i++) {
            sufSum[i] *= preSum;
            preSum *= nums[i];
        }

        
        return sufSum;
    }
}
// @lc code=end

