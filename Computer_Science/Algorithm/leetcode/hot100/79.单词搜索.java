/*
 * @lc app=leetcode.cn id=79 lang=java
 *
 * [79] 单词搜索
 */

// @lc code=start

class Solution {
    
    private int[][] DIRS = {{0,1}, {0,-1}, {1,0}, {-1,0}};

    public boolean exist(char[][] board, String word) {
        char[] w = word.toCharArray();
        int[] cnt = new int[128];
        int[] wordCnt = new int[128];
        for (char[] row : board) {
            for(char ch : row) {
                cnt[ch]++;
            }
        }

        for (char ch : w) {
            if (++wordCnt[ch] > cnt[ch]) return false;
        }

        if (cnt[w[w.length-1]] < cnt[w[0]]) 
            w = new StringBuilder(word).reverse().toString().toCharArray();

        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == w[0]){
                    boolean[][] vis = new boolean[board.length][board[0].length];
                    if (dfs(0, w, board, i, j, vis)) return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(int i, char[] word, char[][] board, int x, int y, boolean[][] vis) {

        if (board[x][y] != word[i]) return false;
        if (i == word.length-1) return true;

        vis[x][y] = true;

        for (int j = 0; j < 4; j++) {
            int nx = x + DIRS[j][0];
            int ny = y + DIRS[j][1];
            if (nx >= 0 && nx < board.length
            &&  ny >= 0 && ny < board[0].length
            &&  !vis[nx][ny] && dfs(i+1, word, board, nx, ny, vis)) {
                return true;
            }
        }
        vis[x][y] = false;
        return false;
    }
}
// @lc code=end

