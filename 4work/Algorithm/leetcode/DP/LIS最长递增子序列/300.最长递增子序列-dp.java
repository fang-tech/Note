package DP.LIS最长递增子序列;

import java.util.Arrays;
import java.util.Collections;

/*
 * @lc app=leetcode.cn id=300 lang=java
 *
 * [300] 最长递增子序列
 */

// @lc code=start
class Solution {
    private int[] memo;
    private int[] nums;

    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        this.nums = nums;
        memo = new int[n];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, dfs(i));
        }
        return ans;
    }
    
    // dfs(i) : 以nums[i]为末尾元素的LIS长度
    // if (nums[x] < nums[i]) ans = max(ans, dfs(x) + 1)
    private int dfs(int i) {
        if (memo[i] != 0) return memo[i];
        for (int j = 0; j < i; j++) {
            if (nums[i] > nums[j]) 
                memo[i] = Math.max(memo[i], dfs(j));
        }
        // 最后一个元素, 也就是nums[i]这个元素一定被选择了
        return ++memo[i];
    }

    public static void main(String[] args) {
        int[] nums = new int[]{10,9,2,5,3,7,101,18};
        Solution s = new Solution();
        int ans = s.lengthOfLIS(nums);
        System.out.println(ans);
    }
}
// @lc code=end

