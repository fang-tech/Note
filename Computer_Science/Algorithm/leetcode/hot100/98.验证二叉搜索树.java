/*
 * @lc app=leetcode.cn id=98 lang=java
 *
 * [98] 验证二叉搜索树
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
    
    public boolean isValidBST(TreeNode root) {
        return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    /**
     * 当前节点的范围是(min, max)
     * @param root
     * @param max 严父对孩子的最大值的限制
     * @param min 对孩子最小值的限制
     * @return
     */
    private boolean isValid(TreeNode root, long min, long max) {
        if (root == null) return true;
        int x = root.val;
        return (x < max && x > min) 
            && isValid(root.left, min, Math.min(max, x)) // (min, min(root, max))
            && isValid(root.right, Math.max(min, x), max); // (min(root, min), max)

    }
}
// @lc code=end

