package DP.买卖股票;
/*
 * @lc app=leetcode.cn id=122 lang=java
 *
 * 122 买卖股票的最佳时机 II
 */

// @lc code=start

class Solution {
    

    public int maxProfit(int[] prices) {
        int n = prices.length;

        int f0 = 0; int f1 = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int new_f0 = Math.max(f0, f1 + prices[i]);
            f1 = Math.max(f1, f0 - prices[i]);
            f0 = new_f0;
        }
        return f0;
    }

}
// @lc code=end

