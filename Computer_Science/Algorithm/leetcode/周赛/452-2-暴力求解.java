/*
 * @lc app=leetcode.cn id=3566 lang=java
 *
 * [3566] 等积子集的划分方案
 */

// @lc code=start
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 暴力求解
 * @Time: O((m-k)(n-k)(k^2logk))
 */
class Solution {
    
    private int[][] grid;
    private int k;

    public int[][] minAbsDiff(int[][] grid, int k) {
        this.grid = grid; this.k = k;
        int m = grid.length; int n = grid[0].length;
        int[][] ans = new int[m-k+1][n-k+1];
        
        // 用左上角的坐标来代表这个子方阵
        for(int i = 0 ;i < m-k+1; i++) {
            for (int j = 0; j < n-k+1; j++) {
                // 计算这个方阵中的最小绝对差
                ans[i][j] = caculate(i, j);
            }
        }
        return ans;
    }

    /**
     * 计算这个方阵中的最小差
     * 通过将方阵中的所有元素排序, 最小差只会发生在相邻元素
     * @param x
     * @param y
     * @return
     */
    private int caculate(int x, int y) {
        List<Integer> arr = new ArrayList<>();
        for (int i = x; i < x + k; i++) {
            for (int j = y; j < y + k; j++) {
                arr.add(grid[i][j]);
            }
        }
        Collections.sort(arr);
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < arr.size() - 1; i++) {
            int absDiff = Math.abs(arr.get(i) - arr.get(i+1));
            if (absDiff != 0) 
                res = Math.min(absDiff, res);
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }
}
// @lc code=end