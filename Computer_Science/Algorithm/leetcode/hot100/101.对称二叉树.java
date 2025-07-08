/*
 * @lc app=leetcode.cn id=101 lang=java
 *
 * [101] 对称二叉树
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
    public boolean isSymmetric(TreeNode root) {
        return isSymmetricSame(root.left, root.right);
    }

    private boolean isSymmetricSame(TreeNode root1, TreeNode root2) {
        // 两棵树有树为空的时候
        if (root1 == null || root2 == null) return root1 == root2;
        return isSymmetricSame(root1.left, root2.right) // 左树的左子树和右树的右子树相等
            && isSymmetricSame(root1.right, root2.left) // 右树的左子树和左树的右子树相等
            && root1.val == root2.val; // 如果左右子树对称相等, 当前两个根节点的值需要相等
    }
}
// @lc code=end

