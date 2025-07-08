/*
 * @lc app=leetcode.cn id=240 lang=java
 *
 * [240] 搜索二维矩阵 II
 */

// @lc code=start
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length; int n = matrix[0].length;
        int x = m-1; int y = 0;
        while (x >= 0 && y <= n-1) {
            int num = matrix[x][y];
            if (num < target)  // 向右找更大的数字
                y++;
            if (num > target) // 向上找更小的数字
                x--;
            if (num == target)  // 找到答案
                return true;
        }
        return false;
    }
}
// @lc code=end

