/*
 * @lc app=leetcode.cn id=73 lang=java
 *
 * [73] 矩阵置零
 */

// @lc code=start
class Solution {
    /**
     * 创建置0数组, 分别记录需要变成0的行, 和需要变成0的列
     * 用于计算出来哪些位置需要变成0
     * 如果[x,y]是0, 记录行x和列y, 
     * @param matrix
     */
    public void setZeroes(int[][] matrix) {
        int m = matrix.length; int n = matrix[0].length;
        int[] row = new int[m]; int[] col = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = 1;
                    col[j] = 1;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (row[i] == 1 || col[j] == 1) 
                    matrix[i][j] = 0;
            }
        }

        return;
    }
}
// @lc code=end

