/*
 * @lc app=leetcode.cn id=687 lang=java
 *
 * [687] 最长同值路径
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

    private int ans;

    public int longestUnivaluePath(TreeNode root) {
        if (root == null) return 0;
        dfs(root);
        return ans;
    }

    /**
     * 这颗树中从根节点出发能获得的最长节点数量
     * 如果当前节点是空节点, 返回-1
     * 如果当前节点的值和父节点的值不一样, 返回-1
     * 这道题目最关键的就是相等的条件, 因为不相等的时候和相等的时候最长链长返回是不一样的
     * 但是这个并不影响计算ans
     * dfs(root) = max(dfs(left), dfs(right))
     * @param root
     * @param last
     * @return 如果和父节点的值不一样, 返回-1, 一样则返回len
     */
    // private int dfs(TreeNode root, int last) {
    //     if (root == null) return -1;
    //     int x = root.val;
    //     int left = dfs(root.left, x);
    //     int right = dfs(root.right, x);
    //     ans = Math.max(ans, left + right + 2);

    //     if (x != last) return -1;
    //     int maxLen = Math.max(left, right);
    //     return maxLen + 1;
    // }

    /**
     * 这个版本是计算左右子树的链长的时候不处理, 
     * 但是只有发现符合条件的链才会更新值
     * @param root
     * @return 当前树的链长
     */
    // private int dfs(TreeNode root) {
    //     int x = root.val;
    //     TreeNode leftNode = root.left;
    //     TreeNode rightNode = root.right;
    //     int maxLen = 0;
    //     if (leftNode != null) {
    //         int len = dfs(leftNode) + 1;
    //         if (x == leftNode.val) {
    //             ans = Math.max(ans, len + maxLen);
    //             maxLen = Math.max(maxLen, len);
    //         }
    //     }
    //     if (rightNode != null) {
    //         int len = dfs(rightNode) + 1;
    //         if (x == rightNode.val) {
    //             ans = Math.max(ans, len + maxLen);
    //             maxLen = Math.max(maxLen, len);
    //         }
    //     }
    //     return maxLen;
    // }
}
// @lc code=end

