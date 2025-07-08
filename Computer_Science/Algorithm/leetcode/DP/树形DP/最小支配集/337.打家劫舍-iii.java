/*
 * @lc app=leetcode.cn id=337 lang=java
 *
 * [337] 打家劫舍 III
 */

// @lc code=start
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

/**
 * 题目的思路其实和之前的2538类似, 分别返回选和不选当前节点的能获取的最大值
 * 详细定义看dfs函数
 */
class Solution {
    public int rob(TreeNode root) {
        int[] ans = dfs(root);
        return Math.max(ans[1], ans[0]);
    }

    /**
     * dfs(root) = max(选了当前节点max(left_noselect, right_noselect) + root.val, 没有选当前节点max(left_select, right_select))
     * @param root
     * @return dfs[0]: 选了当前节点能获取的最大值, dfs[1]: 没选当前节点能获取的最大值
     */
    private int[] dfs(TreeNode root) {
        if (root == null) return new int[]{0, 0};

        int[] left = dfs(root.left);
        int[] right = dfs(root.right);
        
        // select: 选了子节点能获取的最大值, no_select: 不选子节点能获取的最大值
        int left_select = left[0]; int left_noselect = left[1];
        int right_select = right[0]; int right_noselect = right[1];

        // 选了root节点, 后面的节点一定不能选
        int root_select = left_noselect + right_noselect + root.val;
        // 不选root节点, 后面的节点可选可不选
        int root_noselect = Math.max(left_select, left_noselect) + Math.max(right_noselect, right_select);

        return new int[]{root_select, root_noselect};
    }
}
// @lc code=end

