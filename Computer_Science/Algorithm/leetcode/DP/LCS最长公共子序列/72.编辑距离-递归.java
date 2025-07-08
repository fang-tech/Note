/*
 * @lc app=leetcode.cn id=72 lang=java
 *
 * [72] 编辑距离
 */

// @lc code=start

import java.util.Arrays;

class Solution {

    private char[] word1;
    private char[] word2;
    private int[][] memo;

    public int minDistance(String word1, String word2) {
        this.word1 = word1.toCharArray(); this.word2 = word2.toCharArray();
        int n = word1.length(); int m = word2.length();
        this.memo = new int[n][m];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return dfs(n-1, m-1);
    }

    /**
     * 同样是自后向前递归, 我们现在递归到dfs(i, j)
     * 如果两个元素不同, 我们有三种选择,
     * 删除i中的元素 dfs(i-1, j)
     * 删除j中的元素 dfs(i, j-1) 
     * 插入一个字符, 插入到i中:dfs(i, j-1), 插入到j中dfs(i-1, j)
     * 替换字符 dfs(i-1, j-1)也就是最长公共子序列中的两个元素都不选的情况
     * 如果两个元素相同 dfs(i-1, j-1)
     */
    private int dfs(int i, int j) {
        // 如果其中一个子数组没有元素了, 另外一个数组还有x + 1元素, 这个时候就需要执行x + 1个删除操作
        if (i < 0) return j + 1;
        if (j < 0) return i + 1;
        if (memo[i][j] != -1) return memo[i][j];

        if (word1[i] == word2[j]) 
            memo[i][j] = dfs(i-1, j-1);
        else
            memo[i][j] = Math.min(Math.min(dfs(i-1, j), dfs(i, j-1)), dfs(i-1, j-1)) + 1;

        return memo[i][j];
    }
}
// @lc code=end

