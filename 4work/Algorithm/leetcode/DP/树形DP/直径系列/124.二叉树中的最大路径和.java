/*
 * @lc app=leetcode.cn id=124 lang=java
 *
 * [124] 二叉树中的最大路径和
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
 * 比较简单的就是543的变式, 我们能在计算出来树的最大链和的过程中
 * 计算出来二叉树的最大路径和
 */
class Solution {
    private int ans;

    public int maxPathSum(TreeNode root) {
        ans = root.val;
        dfs(root);
        return ans;
    }

    
    private int dfs(TreeNode root) {
        if (root == null) return 0;

        int left = dfs(root.left);
        int right = dfs(root.right);
        
        ans = Math.max(ans, left + right + root.val);
        int res = Math.max(left, right) + root.val; 
        return res > 0 ? res : 0;
    }
}
// @lc code=end

