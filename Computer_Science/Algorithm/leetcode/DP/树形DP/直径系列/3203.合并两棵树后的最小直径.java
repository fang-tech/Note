/*
 * @lc app=leetcode.cn id=3203 lang=java
 *
 * [3203] 合并两棵树后的最小直径
 */

// @lc code=start
// m*n*(m+n)^2

import java.time.chrono.MinguoChronology;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

class Solution {
    
    private List<Integer>[] g;
    private boolean[] vis;
    private int d;
    private int ans;

    public int minimumDiameterAfterMerge(int[][] edges1, int[][] edges2) {
        // 遍历两颗树的所有的合并的可能
        int n = edges1.length + 1; int m = edges2.length + 1;
        g = new List[n+m];
        ans = Integer.MAX_VALUE;
        Arrays.setAll(g, e -> new ArrayList<>());
        // 第一颗树的节点编号[0, n-1]
        for (int[] row : edges1) {
            int a = row[0];
            int b = row[1];
            g[a].add(b);
            g[b].add(a);
        }
        // 第二颗树合并后的节点编号[n, m+n-1]
        for (int[] row : edges2) {
            int a = row[0] + n;
            int b = row[1] + n;
            g[a].add(b);
            g[b].add(a);
        }

        vis = new boolean[n+m];
        dfs(0);
        int d1 = d;
        d = 0;
        vis = new boolean[n+m];
        dfs(n);
        int d2 = d;
        // 两种情况
        // 第一种: 合并后直径是经过了两棵树的, 这个时候就是将两颗树的中间节点连接起来
        // 第二种: 合并后的直接是两颗树中的一棵树的直径
        ans = (d1 + 1) / 2 + (d2 + 1) / 2 + 1;
        ans = Math.max(ans, Math.max(d1, d2));
        return ans;

    }

    // 求从节点i出发, 能得到的最大链长
    private int dfs(int i) {
        vis[i] = true;
        int maxLen = 0;
        for (Integer x : g[i]) {
            if (!vis[x]) { // 因为是双向树, 我们遍历树只能单向遍历, 避免重复遍历
                int len = dfs(x) + 1;
                vis[x] = false; // 恢复现场
                d = Math.max(d, maxLen + len);
                maxLen = Math.max(maxLen, len);
            }
        }
        return maxLen;
    }
}
// @lc code=end

