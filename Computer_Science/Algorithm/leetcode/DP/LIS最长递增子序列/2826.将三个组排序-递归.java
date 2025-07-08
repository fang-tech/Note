/*
 * @lc app=leetcode.cn id=2826 lang=java
 *
 * [2826] 将三个组排序
 */

// @lc code=start

import java.util.Arrays;
import java.util.List;

class Solution {
    private Integer[] nums;
    private int[][] memo;
    /**
     * 分析问题:
     * 原问题: 计算出来[0,n-1]成为非递减序列需要删除的元素数量的最小值
     * 子问题: 已经处理了0是不是要删除了, 现在就需要计算[1, n-1]最小操作数
     * 下一个子问题: 计算出来[i+1, n-1]非递减的最小操作数
     * 当前问题怎么处理:
     *  决定当前元素是不是要删除, 如果不删除那么下一个元素就必须比当前元素 >=
     *  如果删除, 就需要比上一个没有删除的元素 >=
     *  如果这个元素比上一个元素小, 一定要删除
     * dfs(i, j): [i, n-1]非递减的最小操作数, j是上一个没有删除的数字
     * dfs(i, j) = min(dfs(i+1, j), dfs(i+1, nums[i]))
     * @param nums
     * @return
     */
    public int minimumOperations(List<Integer> nums) {
        this.nums = nums.toArray(new Integer[0]);
        memo = new int[nums.size()][4];
        for (int[] row : memo) Arrays.fill(row, -1);
        return dfs(0, 0);
    }
    
    private int dfs(int i, int j) {
        if (i == nums.length) return 0;
        if (memo[i][j] != -1) return memo[i][j];
        int x = nums[i];
        if (x < j) return memo[i][j] = dfs(i+1, j) + 1; // 只能删除
        else return memo[i][j] = Math.min(dfs(i+1, j) + 1, dfs(i+1, nums[i])); // 删或不删
    }

}
// @lc code=end

