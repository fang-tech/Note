/*
 * @lc app=leetcode.cn id=1026 lang=cpp
 *
 * [1026] 节点与其祖先之间的最大差值
 */

#include <algorithm>
struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode() : val(0), left(nullptr), right(nullptr) {}
    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
    TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
};
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
public:
    int ans = -1;
    void dfs(TreeNode* node, int mx, int mn) {
        if (node == nullptr){
            ans = max({mx - mn, ans});
            return;
        }
        mn = min(node->val, mn);
        mx = max(node->val, mx);
        dfs(node->left, mx, mn);
        dfs(node->right, mx, mn);
    }

    int maxAncestorDiff(TreeNode* root) {
        dfs(root, root->val, root->val);
        return ans;
    }
};
// @lc code=end

