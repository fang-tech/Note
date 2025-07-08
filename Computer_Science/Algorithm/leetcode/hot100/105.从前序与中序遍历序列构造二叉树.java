/*
 * @lc app=leetcode.cn id=105 lang=java
 *
 * [105] 从前序与中序遍历序列构造二叉树
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

    // k: inorder[i], v: i
    private Map<Integer, Integer> map = new HashMap<>();

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        int n = preorder.length;
        return dfs(preorder, inorder, 0, n, 0, n);
    }

    /**
     * preorder[0]是当前子树的根节点
     * 左闭右开
     */
    private TreeNode dfs(int[] preorder, int[] inorder, int preL, int preR, int inL, int inR) {
        if (preL == preR) return null;
        int rootVal = preorder[preL];
        int rootIdx = map.get(rootVal);
        // 左子树的大小
        int leftLen = rootIdx - inL;
        TreeNode root = new TreeNode(rootVal);
        root.left = dfs(preorder, inorder, preL+1, preL+leftLen+1, inL, rootIdx);
        root.right = dfs(preorder, inorder, preL+leftLen+1, preR, rootIdx+1, inR);
        return root;
    }
}
// @lc code=end

