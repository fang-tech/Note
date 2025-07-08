/*
 * @lc app=leetcode.cn id=188 lang=java
 *
 * [188] 买卖股票的最佳时机 IV
 */

// @lc code=start

import java.util.Arrays;

class Solution {

    private int[] prices;
    private int[][][] memo;

    public int maxProfit(int k, int[] prices) {
        this.prices = prices;
        int n = prices.length;
        memo = new int[n][k+1][2];
        for (int[][] rows : memo) {
            for (int[] row : rows) {
                Arrays.fill(row, -1);
            }
        }
        return  dfs(n-1, k, 0);
    }

    /**
     * 对比普通的买卖股票的最佳时机, 加上了j状态记录交易次数至多为j次的时候
     * 这里便于状态转移, 将买卖拆分成两次操作
     * 如果j < 0的时候就不能再买卖了
     * @param i 今天是第几天
     * @param j 交易的次数: 也就是卖的次数, 或者是买的次数
     * @param hold 今天开始的时候是否持有股票
     * @return
     */
    public int dfs(int i, int j, int hold){
        if (j < 0) return Integer.MIN_VALUE / 2;
        if (i < 0) return hold == 1 ? Integer.MIN_VALUE / 2: 0;
        if (memo[i][j][hold] != -1) return memo[i][j][hold];

        int ans; 
        if (hold == 1) 
            ans = Math.max(dfs(i-1, j, 1), dfs(i-1, j-1, 0) - prices[i]);
        else 
            ans = Math.max(dfs(i-1, j, 0), dfs(i-1, j, 1) + prices[i]);
        memo[i][j][hold] = ans;
        return ans;
    }
}
// @lc code=end

