/*
 * @lc app=leetcode.cn id=199 lang=java
 *
 * [199] 二叉树的右视图
 */

// @lc code=start

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

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
    public List<Integer> rightSideView(TreeNode root) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        List<Integer> ans = new ArrayList<>();
        if (root != null) queue.addLast(root);
        while (!queue.isEmpty()) {
            // 将当前层的第一个节点添加到答案中
            ans.add(queue.getFirst().val);
            // 遍历当前层(我们从右向左添加子树, 这种情况第一个被添加的节点就是下一层右视图)
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.removeFirst();
                if(node.right != null) queue.addLast(node.right);
                if(node.left != null) queue.addLast(node.left);
            }
        }
        return ans;
    }
}
// @lc code=end

