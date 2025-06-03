package DP.买卖股票;
/*
 * @lc app=leetcode.cn id=122 lang=java
 *
 * [122] 买卖股票的最佳时机 II
 */

// @lc code=start

import java.util.Arrays;

class Solution {
    
    private int[] prices;
    private int[][] memo;

    public int maxProfit(int[] prices) {
        this.prices = prices;
        memo = new int[prices.length][2];
        for (int[] row : memo) Arrays.fill(row, -1);

        return dfs(prices.length-1, 0);
    }

    /**
     * @param i 第i天
     * @param hold 今天开始的时候是否持有股票
     * @return 第i天的时候在持有股票和不持有股票的情况下的能赚到的最多的钱
     */
    private int dfs(int i, int hold) {
        // (-1, 0) return 0, (-1, 1) return -INF(第一天开始的时候不可能持有股票)
        if (i < 0) return hold == 1 ? Integer.MIN_VALUE : 0;
        if (memo[i][hold] != -1) return memo[i][hold];
        if (hold == 1) 
            memo[i][hold] = Math.max(dfs(i-1, 0) - prices[i], dfs(i-1, 1));
        else 
            memo[i][hold] = Math.max(dfs(i-1, 1) + prices[i], dfs(i-1, 0));
        return memo[i][hold];
    }
}
// @lc code=end

