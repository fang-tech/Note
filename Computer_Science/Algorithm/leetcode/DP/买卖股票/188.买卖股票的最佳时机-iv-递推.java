/*
 * @lc app=leetcode.cn id=188 lang=java
 *
 * [188] 买卖股票的最佳时机 IV
 */

// @lc code=start

import java.util.Arrays;

class Solution {
    /**
     * 为什么是k+2?
     *  虽然i和j在递归的代码中都是 < 0, 但是实际的范围是不一样的
     *  i 属于 [0, len-1], <0的情况是发生了越界访问, 有len个元素
     *  j 属于 [0, k], <0的情况是没有意义的情况, 有k+1个元素
     *  所以j加上-1这个值, 就有k+2中情况, 实际的范围是[-1, k]
     * @param k
     * @param prices
     * @return
     */
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        int[][][] f = new int[n+1][k+2][2];

        // 初始化
        // 所有j == -1的项都需要初始化为-INF
        for (int i = 0; i <= n; i++) {
            f[i][0][0] = f[i][0][1] = Integer.MIN_VALUE / 2;
        }

        // 所有i < 0的项, 根据hold初始化, hold == 0的可以省略
        for (int j = 1; j <= k+1;j++) {
            f[0][j][1] = Integer.MIN_VALUE / 2;
        }
        

        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= k+1; j++) {
                f[i+1][j][0] = Math.max(f[i][j][0], f[i][j][1] + prices[i]);
                f[i+1][j][1] = Math.max(f[i][j][1], f[i][j-1][0] - prices[i]);
            }
        }

        return f[n][k+1][0];
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

