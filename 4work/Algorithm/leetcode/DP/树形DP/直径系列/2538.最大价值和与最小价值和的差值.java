/*
 * @lc app=leetcode.cn id=2538 lang=java
 *
 * [2538] 最大价值和与最小价值和的差值
 */

// @lc code=start

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 这道题目和常规的求直径的区别就是, 求的实际上是去了一个叶子节点的最大直径
 * 可以通过dfs(i)[0] = i作为中间节点, 去了叶子节点的最大链和
 *        dfs(i)[1] = i作为中间节点, 没有去叶子节点的最大链和
 * 这样就能通过max(
 * 当前带叶子节点的最大直径和 + 最大的不带叶子节点的最大直径和
 * 当前不带叶子节点的最大直径和 + 最大的带叶子节点的最大直径和 
 * )
 * 来计算出来去了一个叶子节点以后的最大直径
 */
class Solution {
    
    private List<Integer>[] g;
    private long ans;
    private boolean[] vis;
    private int[] price;

    public long maxOutput(int n, int[][] edges, int[] price) {
        this.price = price;
        g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] edge : edges) {
            int x = edge[0]; int y = edge[1];
            g[x].add(y);
            g[y].add(x);
        }
        vis = new boolean[n];
        dfs(0);
        return ans;
    }

    private long[] dfs(int i) {
        vis[i] = true; 
        int p = price[i];
        long maxS1 = p, maxS2 = 0;
        // 当前带叶子节点的最大直径和
        // 当前不带叶子节点的最大直径和

        for (Integer node : g[i]) {
            if (!vis[node]) {
                long[] res = dfs(node);
                long s1 = res[0], s2 = res[1];
                vis[node] = false;
                ans = Math.max(ans, Math.max(maxS1 + s2, maxS2 + s1));
                // 当前带叶子节点的最大直径和 + 最大的不带叶子节点的最大直径和
                // 当前不带叶子节点的最大直径和 + 最大的带叶子节点的最大直径和
                maxS1 = Math.max(maxS1, s1 + p);
                // 能进入到这里, 说明这个节点不是叶子节点, 也就是i一定不是叶子节点
                maxS2 = Math.max(maxS2, s2 + p);
            }
        }
        // System.out.println("i = " + i + "; maxS1 = " + maxS1 + " maxS2 = " + maxS2);
        return new long[]{maxS1, maxS2};
    }
}
// @lc code=end

