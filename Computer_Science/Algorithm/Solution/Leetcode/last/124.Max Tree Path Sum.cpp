/*
 * @lc app=leetcode.cn id=124 lang=cpp
 *
 * [124] 二叉树中的最大路径和
 */
#include <iostream>
#include <vector>
using namespace std;
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
private:
    int dfs(TreeNode* root, int& ans) {
        if (root == nullptr) return 0;
        TreeNode* l = root->left; TreeNode* r = root->right;
        int left = dfs(l, ans); int right = dfs(r, ans);
        int maxSum = root->val;
        ans = max(ans, left + right + root->val);
        maxSum += max(left, right);
        return maxSum > 0 ? maxSum : 0;
    }
public:
    int maxPathSum(TreeNode* root) {
        int ans = root->val;
        dfs(root, ans);
        return ans;
    }
};
// @lc code=end
