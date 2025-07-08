/*
 * @lc app=leetcode.cn id=108 lang=java
 *
 * [108] 将有序数组转换为二叉搜索树
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
    public TreeNode sortedArrayToBST(int[] nums) {
        return merge(nums, 0, nums.length);
    }

    /**
     * 因为是升序数组, 将数组从中间拆分, 左边部分就是左子树, 右半部分就是右子树
     * mid就是根节点
     */
    // 范围是从[left, right)
    private TreeNode merge(int[] nums, int left, int right) {
        int m = right - left;
        if (m == 0) return null;
        if (m == 1) return new TreeNode(nums[left]);
        
        int mid = left + m / 2;
        TreeNode leftNode = merge(nums, left, mid);
        TreeNode rightNode = merge(nums, mid+1, right);
        TreeNode root = new TreeNode(nums[mid], leftNode, rightNode);
        return root;
    }
}
// @lc code=end

