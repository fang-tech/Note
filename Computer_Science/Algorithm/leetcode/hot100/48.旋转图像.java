/*
 * @lc app=leetcode.cn id=48 lang=java
 *
 * [48] 旋转图像
 */

// @lc code=start

import java.util.ArrayList;
import java.util.List;

class Solution {

    /**
     * 向右旋转90°可以翻译成先转置再行翻转
     * 一般地，把一个点绕O旋转任意 θ 角度，
     * 可以通过两个翻转操作实现。要求这两条翻转的对称轴，交点为O且夹角为θ/2
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        // 转置
        for (int i = 0; i < matrix.length; i++) { // 遍历对角线下方的元素
            for (int j = 0; j < i; j++) {
                swap(matrix, i, j, j, i);
            }
        }

        // 行翻转
        for (int[] row : matrix) {
            // 翻转
            int left = 0; int right = row.length - 1;
            while (left < right) {
                int tmp = row[left];
                row[left++] = row[right];
                row[right--] = tmp;
            }
        }
    }

    private void swap(int[][] matrix, int x1, int y1, int x2, int y2) {
        int tmp = matrix[x1][y1];
        matrix[x1][y1] = matrix[x2][y2];
        matrix[x2][y2] = tmp;
    }

}
// @lc code=end

