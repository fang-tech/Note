/*
 * @lc app=leetcode.cn id=236 lang=java
 *
 * [236] 二叉树的最近公共祖先
 */

// @lc code=start
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    
    private TreeNode p, q;

    /**
     * 什么时候root会是p和q的公共祖先 ?
     * q和p分散在两个子树中的时候, 或者root就是p和q之一, 另一个在某一个子树中的时候
     * 如果q和p都在某一颗子树里面的时候, 则公共祖先在那个子树里面
     * @return 公共节点或者null(代表没有找到)
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) return root;
        return left != null ? left : right;
    }

}
// @lc code=end

