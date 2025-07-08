/*
 * @lc app=leetcode.cn id=1143 lang=java
 *
 * [1143] 最长公共子序列
 */

// @lc code=start
class Solution {

    /**
     * dfs(i, j) = max(dfs(i-1, j), dfs(i, j-1)) s[i] != t[j]
     * dfs(i,j) = max(dfs(i-1, j-1)+1) s[i] == t[j]
     * 使用递推以后, 能简化空间, 因为对于每个子序列能看到下一个状态只取决于上一个状态
     * 也就是使用滚动数组, 将某一个状态压缩
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(); int n = text2.length();
        char[] str1 = text1.toCharArray(); char[] str2 = text2.toCharArray();

        // 普通递推
        // int[][] f = new int[m+1][n+1];
        // for (int i = 0; i < m; i++) {
        //     for (int j = 0; j < n; j++) {
        //         if (str1[i] != str2[j]) {
        //             f[i+1][j+1] = Math.max(f[i][j+1], f[i+1][j]);
        //         } else {
        //             f[i+1][j+1] = f[i][j] + 1;
        //         }
        //     }
        // }
        // return f[m][n];

        // 滚动数组
        int[][] f = new int[2][n+1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (str1[i] != str2[j]) {
                    f[(i+1) % 2][j+1] = Math.max(f[i % 2][j+1], f[(i+1) % 2][j]);
                } else {
                    f[(i+1) % 2][j+1] = f[i % 2][j] + 1;
                }
            }
        }
        return f[m % 2][n];
    }
}
// @lc code=end

