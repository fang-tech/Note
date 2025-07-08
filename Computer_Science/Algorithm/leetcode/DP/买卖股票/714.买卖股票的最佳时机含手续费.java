/*
 * @lc app=leetcode.cn id=714 lang=java
 *
 * [714] 买卖股票的最佳时机含手续费
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        int[] f = new int[2];
        f[0] = 0; f[1] = Integer.MIN_VALUE / 2;

        for (int i = 0; i < n; i ++) {
            int new_f0 = Math.max(f[0], f[1] + prices[i] - fee);
            f[1] = Math.max(f[1], f[0] - prices[i]);
            f[0] = new_f0;
        }

        return f[0];
    }
}
// @lc code=end

