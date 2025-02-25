/*
 * @lc app=leetcode.cn id=938 lang=cpp
 *
 * [938] 二叉搜索树的范围和
 */

struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode() : val(0), left(nullptr), right(nullptr) {}
    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
    TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
};
// @lc code=start
/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode() : val(0), left(nullptr), right(nullptr) {}
 *     TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
 *     TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
 * };
 */
class Solution {
public:
    int rangeSumBST(TreeNode* root, int low, int high) {
        if (root == nullptr) return 0;

        int x = root->val;
        if (x > high) 
            // 当前节点的值大于最大值, 那么右子树的所有值都不可能在范围内
            return rangeSumBST(root->left, low, high);

        else if (x < low) 
            // 当前节点的值小于最小值, 说明左子树的所有值都不可能在范围里
            return rangeSumBST(root->right, low, high);

        return rangeSumBST((root->left), low, high) + rangeSumBST(root->right, low, high) + x;
    }
};
// @lc code=end

