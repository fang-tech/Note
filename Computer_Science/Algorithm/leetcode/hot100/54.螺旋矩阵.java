/*
 * @lc app=leetcode.cn id=54 lang=java
 *
 * [54] 螺旋矩阵
 */

// @lc code=start

import java.util.ArrayList;
import java.util.List;

class Solution {

    private int[][] map = new int[][] {{0,1}, {1,0}, {0,-1}, {-1,0}};

    public List<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length; int n = matrix[0].length;
        int x = 0; int y = -1; int size = m*n;
        List<Integer> ans = new ArrayList<>(size);
            // 求模转弯
            // 这层循环用于转换方向和步数
            for (int di = 0; ans.size() < size; di = (di+1)%4) { 
                int[] DIRS = map[di];
                for (int i = 0; i < n; i++) {
                    x += DIRS[0];
                    y += DIRS[1];
                    ans.add(matrix[x][y]);
                }
                // 转换m和n, 先--下, 减小下一次的步数
                int temp = m-1;
                m = n;
                n = temp;
            }
            
            return ans;

            // for (int[] des : map) {
            //     if (des[0] == 0) {
            //         // 水平移动
            //         // 移动的次数和n有关
            //         for (int i = 0; i < n; i++) {
            //             x += des[0];
            //             y += des[1];
            //             ans.add(matrix[x][y]);
            //         }
            //         m--;
            //         // System.out.println("x=" + x + " y=" + y + " m=" + m + " n=" + n);
            //     } else {
            //         // 竖直移动
            //         // 移动的次数和m有关
            //         for (int i = 0; i < m; i++) {
            //             x += des[0];
            //             y += des[1];
            //             ans.add(matrix[x][y]);
            //         }
            //         n--;
            //         // System.out.println("x=" + x + " y=" + y + " m=" + m + " n=" + n);
            //     }
            // }
        
    }
}
// @lc code=end

