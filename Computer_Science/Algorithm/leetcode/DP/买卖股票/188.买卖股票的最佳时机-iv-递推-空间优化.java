/*
 * @lc app=leetcode.cn id=188 lang=java
 *
 * [188] 买卖股票的最佳时机 IV
 */

// @lc code=start

import java.util.Arrays;

class Solution {
    /**
     * 
     * @param k
     * @param prices
     * @return
     */
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        int[][] f = new int[k+2][2];

        // 初始化
        f[0][0] = Integer.MIN_VALUE / 2;
        // 所有i < 0的项, 根据hold初始化, hold == 0的可以省略
        for (int j = 1; j <= k+1;j++) {
            f[j][1] = Integer.MIN_VALUE / 2;
        }
        

        for (int i = 0; i < n; i++) {
            // 因为我们压缩了状态, f[i][j][hold]和f[i+1][j][hold]的值同时存在f[j][hold]中
            // 如果从前向后遍历, 
            // 如果不滚动数组, 计算f[i+1]中的值应该是只使用于f[i], 但是从前向后遍历
            // 就会就会出现计算f[i+1]中用到的f[i]已经被覆盖成f[i+1]中的值了, 所以需要从后往前遍历
            // 这个值已经被覆盖掉了 
            for (int j = k+1; j >= 1; j--) {
                f[j][0] = Math.max(f[j][0], f[j][1] + prices[i]);
                f[j][1] = Math.max(f[j][1], f[j-1][0] - prices[i]);
            }
        }

        return f[k+1][0];
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
    // public int dfs(int i, int j, int hold){
    //     if (j < 0) return Integer.MIN_VALUE / 2;
    //     if (i < 0) return hold == 1 ? Integer.MIN_VALUE / 2: 0;
    //     if (memo[i][j][hold] != -1) return memo[i][j][hold];

    //     int ans; 
    //     if (hold == 1) 
    //         ans = Math.max(dfs(i-1, j, 1), dfs(i-1, j-1, 0) - prices[i]);
    //     else 
    //         ans = Math.max(dfs(i-1, j, 0), dfs(i-1, j, 1) + prices[i]);
    //     memo[i][j][hold] = ans;
    //     return ans;
    // }
}
// @lc code=end

