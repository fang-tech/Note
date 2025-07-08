/*
 * @lc app=leetcode.cn id=51 lang=java
 *
 * [51] N 皇后
 */

// @lc code=start

import java.util.ArrayList;
import java.util.List;

class Solution {
    /**
     * N皇后问题, 每行每列都必须有一个皇后
     * 我们记录皇后的位置, 可以用一个一维数组来记录queen[i] = j
     * 我们就将这个问题转化成了排列问题, 找出来所有符合条件排列方式
     * 如果(i,j)位置有了皇后, 则下一个皇后(x,y)
     * x-y == i-j, 说明
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> ans = new ArrayList<>();
        int[] queue = new int[n];
        boolean[] sum = new boolean[2*n];
        boolean[] diff = new boolean[2*n];
        boolean[] vis = new boolean[n];
        dfs(0, queue, n, ans, vis, sum, diff);
        return ans;
    }

    private void dfs(int i, int[] queen, int n, List<List<String>> ans, boolean[] vis, boolean[] sum, boolean[] diff) {
        if (i == n) {
            List<String> path = new ArrayList<>();
            for (int q : queen) {
                char[] str = new char[n];
                // 生成q个.
                for(int j = 0; j < q; j++) {
                    str[j] = '.';
                }
                // 生成Q
                str[q] = 'Q';
                // 生成剩下的.
                for (int j = q+1; j < n; j++) {
                    str[j] = '.';
                }
                path.add(new String(str));
            }
            ans.add(path);
            return;
        }

        for (int j = 0; j < n; j++) {
            // 这个列号没有被选择过, 并且斜向也没有皇后
            if (!vis[j] && !sum[j+i] && !diff[j-i+n]) { 
                queen[i] = j;
                sum[i+j] = true;
                diff[j-i+n] = true;
                vis[j] = true;
                dfs(i+1, queen, n, ans, vis, sum, diff);
                vis[j] = false;
                sum[i+j] = false;
                diff[j-i+n] = false;
            }  
        }

    }
}
// @lc code=end

