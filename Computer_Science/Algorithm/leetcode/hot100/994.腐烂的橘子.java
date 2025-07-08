/*
 * @lc app=leetcode.cn id=994 lang=java
 *
 * [994] 腐烂的橘子
 */

// @lc code=start

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    
    private int[][] DIRS = {{0,1}, {0, -1}, {1, 0}, {-1, 0}};

    public int orangesRotting(int[][] grid) {
        int ans = 0;
        Deque<int[]> queue = new ArrayDeque<>(); // 存的是下一层要遍历的节点, 只会放入新鲜节点
        int m = grid.length; int n = grid[0].length;
        // 找到所有腐烂的橘子
        for (int i = 0; i < m; i++) {
            for (int j  = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    queue.addLast(new int[]{i, j});}
            }
        }

        // 作为bfs的起点, 加入到队列中, 是第零层的节点
        while (!queue.isEmpty()) {
            ans++;
            int size = queue.size();
            for (int j = 0; j < size; j++) {
                int[] org = queue.removeFirst();
                for (int i = 0; i < 4; i++) {
                    int nx = org[0] + DIRS[i][0];
                    int ny = org[1] + DIRS[i][1];
                    if (nx < m && nx >= 0 && ny < n && ny >= 0 // 下一个节点在边界内
                    && grid[nx][ny] == 1) { // 是个新鲜的橘子
                        grid[nx][ny] = 2;
                        queue.addLast(new int[]{nx, ny}); // 扩散腐烂
                    }
                }
            }
        }
                // 如果有还有新鲜橘子, 返回-1
        for (int[] row : grid) {
            for (int num : row) {
                if (num == 1) return -1;
            }
        }

        // 返回时间
        return ans == 0 ? 0 : ans - 1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // 测试用例1: 预期结果 4
        int[][] grid1 = {{2,1,1},{1,1,0},{0,1,1}};
        System.out.println("测试用例1结果: " + solution.orangesRotting(grid1));
        
        // 测试用例2: 预期结果 -1
        int[][] grid2 = {{2,1,1},{0,1,1},{1,0,1}};
        System.out.println("测试用例2结果: " + solution.orangesRotting(grid2));
        
        // 测试用例3: 预期结果 0
        int[][] grid3 = {{0,2}};
        System.out.println("测试用例3结果: " + solution.orangesRotting(grid3));
        
        System.out.println("✅ 腐烂的橘子算法测试完成!");
    }
}
// @lc code=end

