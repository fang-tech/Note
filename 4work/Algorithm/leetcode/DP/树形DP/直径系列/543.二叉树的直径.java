/*
 * @lc app=leetcode.cn id=543 lang=java
 *
 * [543] 二叉树的直径
 */

// @lc code=start

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

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
    // public class TreeNode {
    //     int val;
    //     TreeNode left;
    //     TreeNode right;
    //     TreeNode() {}
    //     TreeNode(int val) { this.val = val; }
    //     TreeNode(int val, TreeNode left, TreeNode right) {
    //         this.val = val;
    //         this.left = left;
    //         this.right = right;
    //     }
    // }

    int ans;
    /**
     * 遍历这棵树, 计算从当前root出发能得到的最长链
     * 通过左右子树的最长链长度, 计算出来树的直径
     * @param root
     * @return
     */
    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return ans;
    }

    /**
     * 计算当前子树的最长链长度
     * @param root
     * @return
     */
    private int dfs(TreeNode root) {
        if (root == null) // 当前节点是空节点
            return -1;

        int left = dfs(root.left);
        int right = dfs(root.right);
        ans = Math.max(ans, left + right + 2);
        return Math.max(left, right) + 1;
    }
}
// @lc code=end

