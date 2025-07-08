/*
 * @lc app=leetcode.cn id=200 lang=java
 *
 * [200] 岛屿数量
 */

// @lc code=start
class Solution {
    
    private boolean[][] vis;
    private int[][] DIRS = {{0,1}, {0, -1}, {1, 0}, {-1, 0}};
    private int ans = 0;

    public int numIslands(char[][] grid) {
        int m = grid.length; int n = grid[0].length;
        vis = new boolean[m][n];
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (!vis[x][y] && grid[x][y] == '1') {
                    dfs(grid, x, y);
                    ans++;
                }
            }
        }
        return ans;
    }

    private void dfs(char[][] grid, int x, int y) {
        if (vis[x][y]) return; // 如果访问过这个节点
        vis[x][y] = true;
        int m = grid.length; int n = grid[0].length;
        for (int i = 0; i < 4; i++) { // 遍历上下左右四个方向
            // 下一个要访问的节点
            int nx = x + DIRS[i][0]; 
            int ny = y + DIRS[i][1];

            if (nx < m && nx >= 0 && ny < n && ny >= 0 // 节点在边界内
                && !vis[nx][ny] && grid[nx][ny] == '1') { // 节点没有被访问过, 同时是陆地
                dfs(grid, nx, ny);
            }
        }
    }
}
// @lc code=end
