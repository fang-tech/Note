/*
 * @lc app=leetcode.cn id=1617 lang=java
 *
 * [1617] 统计子树中城市之间最大距离
 */


// @lc code=start

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {

    private boolean[] visited, inSet;
    private List<Integer>[] g;
    private int[] ans;
    private int n;
    private int d;
    
    public int[] countSubgraphsForEachDiameter(int n, int[][] edges) {
        this.n = n;
        g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0]-1; int v = edges[i][1]-1;
            // 建树
            g[u].add(v);
            g[v].add(u);
        }
        ans = new int[n-1];
        inSet = new boolean[n];
        f(0);
        return ans;
    }

    private void f(int i) {
        if (i == n) {
            for (int v = 0; v < n ;v++) {
                if (inSet[v]) {
                    visited = new boolean[n];
                    d = 0;
                    dfs(v);
                    break;
                }
            }
            if (d > 0 && Arrays.equals(visited, inSet))
                ans[d-1]++;
            return ;
        }


        // 选
        inSet[i] = true;
        f(i+1);
        inSet[i] = false;

        // 不选
        f(i+1);
    }

    private int dfs(int i) {
        visited[i] = true;
        List<Integer> list = g[i];
        int maxLen = 0;
        for (Integer nxt : list) {
            if (!visited[nxt] && inSet[nxt]) { // 没有访问
                int len = dfs(nxt) + 1;
                d = Math.max(len + maxLen, d);
                maxLen = Math.max(maxLen, len);
            }
        }
        return maxLen;
    }
}
// @lc code=end

