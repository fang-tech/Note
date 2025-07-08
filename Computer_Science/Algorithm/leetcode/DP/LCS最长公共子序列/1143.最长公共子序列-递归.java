/*
 * @lc app=leetcode.cn id=1143 lang=java
 *
 * [1143] 最长公共子序列
 */

// @lc code=start
class Solution {
    
    private char[] text1;
    private char[] text2;
    private int[][] memo;

    public int longestCommonSubsequence(String text1, String text2) {
        this.text1 = text1.toCharArray(); this.text2 = text2.toCharArray();
        memo = new int[text1.length()][text2.length()];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dfs(text1.length()-1, text2.length()-1);
    }

    /**
     * 对于两个字符串中的元素我们都有选和不选两种选择(分别构建各自的子序列), 然后找出来最长的公共子序列
     * 最后我们会组成选/不选text1[i] , 选/不选text2[j]四种选择, 对应的退回到四个问题
     * dfs(i-1, j): 选了i, dfs(i, j-1): 选了j, dfs(i-1, j-1)两个都没有选 
     * dfs(i-1, j-1)+1, 两个都选了
     * 最后dfs(i, j) = max(dfs(i-1, j), dfs(i, j-1), dfs(i-1, j-1)) s[i] != t[j]
     *    dfs(i,j) = max(dfs(i-1, j), dfs(i, j-1), dfs(i-1, j-1)+1) s[i] == t[j]
     * 优化成
     * 最后dfs(i, j) = max(dfs(i-1, j), dfs(i, j-1)) s[i] != t[j], 因为dfs(i-1, j)会经过dfs(i-1, j-1)
     *    dfs(i,j) = max(dfs(i-1, j-1)+1) s[i] == t[j], 因为不选的话, 也不会在dfs(i-1,j)和dfs(i, j-1)中出现更大的答案
     * 
     */
    private int dfs(int i, int j) {
        if (i < 0 || j < 0) return 0;
        if (memo[i][j] != -1) return memo[i][j];
        if (text1[i] != text2[j]) {
            memo[i][j] = Math.max(dfs(i-1, j), dfs(i, j-1));
        } else {
            memo[i][j] = dfs(i-1, j-1) + 1;
        }
        return memo[i][j];
    }
}
// @lc code=end

