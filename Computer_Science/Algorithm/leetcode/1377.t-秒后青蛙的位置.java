/*
 * @lc app=leetcode.cn id=1377 lang=java
 *
 * [1377] T 秒后青蛙的位置
 */

// @lc code=start

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {

    private List<Integer>[] g;
    private boolean[] vis;
    private int target;
    private int level;
    private int t;

    public double frogPosition(int n, int[][] edges, int t, int target) {
        g = new List[n]; this.target = target - 1; this.t = t;
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int i = 0; i < n; i++) g[i].add(i);
        for (int[] row : edges) {
            int a = row[0]-1; int b = row[1]-1;
            g[a].add(b);
            g[b].add(a);
        }
        vis = new boolean[n];
        level = 0;
        int[] ans = dfs(0);
        System.out.println(ans[0] + " " + ans[1]);
        return (double) ans[1] == 1 ? 1d / ans[0] : 0;
        
    }

    /*
    8\n[[2,1],[3,2],[4,1],[5,1],[6,4],[7,1],[8,7]]\n7\n7
    7/n[[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]]/n20/n6
    */

    /**
     * 因为是无向树, 就只有一条路径是能通向最后的target的
     * 跳到没有节点可以跳了, 或者跳到了target节点就能不跳了
     * dfs(i) = 上一个节点可以跳的累计可以跳的选择总数 + 当前节点的选择总数
     * @param i 当前所处的节点
     * @return  如果返回的是0, 说明这条路径没有找到target. 如果返回的不是0, 说明找到了target
     */
    private int[] dfs(int i){
        if (level == t && i == target) return new int[]{1, 1};
        int sum = 0;
        int cnt = 0; int flag = 0; // 这条路径是不是发现了target
        for (int nxt : g[i]) {
            if (!vis[nxt]) {
                level++;
                int[] res = dfs(nxt);
                level--;
                vis[nxt] = false;
                if (res[1] == 1) {
                    sum = res[0];
                    flag = 1;
                }
                cnt++;
            }
        }
        if (flag == 1)  {
            System.out.println("当前节点是: " + i + " cnt: " + cnt + "level: " + level);
            return new int[]{cnt*sum, flag};
        }
        return new int[]{1,0};
    }
}
// @lc code=end

