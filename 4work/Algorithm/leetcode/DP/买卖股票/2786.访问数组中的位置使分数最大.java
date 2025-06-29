/*
 * @lc app=leetcode.cn id=2786 lang=java
 *
 * [2786] 访问数组中的位置使分数最大
 */

// @lc code=start

import java.util.Arrays;

class Solution {
    
    private int x;
    private int[] nums;
    private long[][] memo;

    /**
     * 最开始的源问题是从0开始访问的, 能获取的最大得分, 访问0获得的得分固定是nums[0]
     * 将0访问完以后, 就是从1开始访问, 能获得最大得分值, 访问1获得的得分就需要视上一个访问的数字来决定
     * 同时对于每个数我们都有选择访问还是不访问的权力
     * 如果奇偶性相同, 一定要访问, 因为单纯加分, 完全不需要权衡
     */
    public long maxScore(int[] nums, int x) {
        int n = nums.length;
        this.nums = nums; this.x = x;
        this.memo = new long[n][2];
        for (long[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dfs(0, nums[0] % 2);
    }

    /**
     * @param j 上一个访问的数字的奇偶性
     * @param i 当前位置
     * @return 从当前位置开始访问, 能获得的最大得分之和
     */
    private long dfs(int i, int j) {
        if (i == nums.length) return 0;
        if (memo[i][j] != -1) return memo[i][j];
        // 两个数字奇偶性一样, 必须选择, 因为一定能让得分增长
        if (nums[i] % 2 == j) 
            return memo[i][j] = dfs(i+1, j) + nums[i];
        // 两个数字的奇偶性不一样, 就有选择和不选择两种可能
        else  
            return memo[i][j] = Math.max(dfs(i+1, j), dfs(i+1, j^1) - x + nums[i]);

    }
}
// @lc code=end

