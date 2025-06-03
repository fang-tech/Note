package DP.买卖股票;
/*
 * @lc app=leetcode.cn id=309 lang=java
 *
 * [309] 买卖股票的最佳时机含冷冻期
 */

import java.util.Arrays;

// @lc code=start
/**
 * 这道题目对比不含最佳时机冷冻期的就需要多向前递归一步, 
 * 如果我今天要买股票, 那我昨天就不能卖股票, 并且我前天手上应该没有股票
 * 也就是中间就会空出来一天没有任何操作, 也就是直接跳过一天
 * 和打家劫舍比较像
 */
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] f = new int[3][2];
        f[0][0] = 0; f[0][1] = Integer.MIN_VALUE;
        f[1][0] = 0; f[1][1] = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            f[(i+2)%3][0] = Math.max(f[(i+1)%3][0], f[(i+1)%3][1] + prices[i]);
            f[(i+2)%3][1] = Math.max(f[(i+1)%3][1], f[i%3][0] - prices[i]);
        }

        return f[(n+1)%3][0];
    }
}
// @lc code=end

