/*
 * @lc app=leetcode.cn id=543 lang=java
 *
 * [543] 二叉树的直径
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
class Solution {
    

    private int ans = Integer.MIN_VALUE;
    /**
     * 求最大深度的过程中, 当前子树能获取的最大直径是, 左子树的最大深度 + 右子树最大深度 + 1
     */
    public int diameterOfBinaryTree(TreeNode root) {
        maxDepth(root);
        return ans;
    }

    private int maxDepth(TreeNode root) {
        if (root == null) return -1;
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        ans = Math.max(ans, left + right + 2); // 加上根节点和左右子树连接的两条边
        return Math.max(left, right) + 1;
    }

}
// @lc code=end

