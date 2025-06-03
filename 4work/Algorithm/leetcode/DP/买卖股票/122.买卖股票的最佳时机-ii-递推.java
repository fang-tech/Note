package DP.买卖股票;
/*
 * @lc app=leetcode.cn id=122 lang=java
 *
 * [122] 买卖股票的最佳时机 II
 */

// @lc code=start

class Solution {
    
    private int[][] f;

    public int maxProfit(int[] prices) {
        int n = prices.length;

        f = new int[n+1][2];
        f[0][0] = 0; f[0][1] = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            f[i+1][0] = Math.max(f[i][0], f[i][1] + prices[i]);
            f[i+1][1] = Math.max(f[i][1], f[i][0] - prices[i]);
        }
        return f[n][0];
    }
}
// @lc code=end

