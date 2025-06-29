/*
 * @lc app=leetcode.cn id=2246 lang=java
 *
 * [2246] 相邻字符不同的最长路径
 */

// @lc code=start

import java.util.ArrayList;
import java.util.List;

/**
 * 这道题目的主要
 */
class Solution {
    private List<ArrayList<Integer>> g;
    private int ans;
    private char[] s;

    public int longestPath(int[] parent, String s) {
        g = new ArrayList<>();
        for (int i = 0; i < parent.length; i++) {
            g.add(new ArrayList<>());
        }
        for (int i = 1; i < parent.length; i++) {
            g.get(parent[i]).add(i); // 父节点parent[i]添加子节点i
        }
        this.s = s.toCharArray();
        dfs(0);
        return ans + 1;
    }

    /**
     * dfs(i)的定义是从i节点出发能得到的最长链长
     * @param i
     * @return
     */
    private int dfs(int i) {
        ArrayList<Integer> sons = g.get(i); // 获取到当前节点的所有子节点
        int max_len = 0;
        for (int j : sons) {
            int len = dfs(j) + 1;
            if (s[j] != s[i]) {
                ans = Math.max(ans, max_len + len);
                max_len = Math.max(len, max_len);
            }
        }
        return max_len;
    }
}
// @lc code=end

