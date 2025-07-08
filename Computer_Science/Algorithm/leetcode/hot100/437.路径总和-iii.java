/*
 * @lc app=leetcode.cn id=437 lang=java
 *
 * [437] 路径总和 III
 */

// @lc code=start

import java.util.HashMap;
import java.util.Map;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {

    private int ans = 0;
    private int targetSum;
    private Map<Long, Integer> cnt = new HashMap<>();

    public int pathSum(TreeNode root, int targetSum) {
        this.targetSum = targetSum;
        cnt.put(0L, 1);
        dfs(root, 0);
        return ans;
    }

    /**
     * 这道题目和560和为k的子数组个数是一样的题目, 不过这道题目变成了树版
     * 那道题目的处理方式是, 我们将问题转化成枚举s[j]有多少s[i]满足前缀和s[j] - s[i] = k
     * 这道题目也是一样的, 我们在路径上累加sum, 就相当于前缀和
     * @return 路径总数
     */
    private void dfs(TreeNode root, long sum) {
        if (root == null) return ;
        sum += root.val; // sum += num
        // 把root当最终的节点, 统计有多少个起点
        ans += cnt.getOrDefault(sum - targetSum, 0); // cnt[s[j]-k]
        // 增加一种可以达到sum的可能路径
        cnt.merge(sum, 1, Integer::sum); // cnt[sum]++
        dfs(root.left, sum);
        dfs(root.right, sum);
        // 这颗树, 所以加上了这个节点的值的sum就不可达了, 回溯
        cnt.merge(sum, -1, Integer::sum); // cnt[sum]--
    }
}
// [1,null,2,null,3,null,4,null,5]\n3
// @lc code=end

