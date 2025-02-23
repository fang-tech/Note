/*
 * @lc app=leetcode.cn id=1110 lang=cpp
 *
 * [1110] 删点成林
 */

#include <cstddef>
#include <vector>
using namespace std;
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

    TreeNode* dfs(vector<TreeNode*>& ans, TreeNode* node, int* map) {
        if (node == nullptr) return nullptr;

        // 后序遍历
        node->left = dfs(ans, node->left, map);
        node->right = dfs(ans, node->right, map);

        // 需要删除的节点
        if (map[node->val]) {
            if(node->left) ans.push_back(node->left);
            if(node->right) ans.push_back(node->right);
            // 删除节点
            return nullptr;
        }
        return node;
    }

    vector<TreeNode*> delNodes(TreeNode* root, vector<int>& to_delete) {
        int map[1005] = {0};
        for (int v : to_delete) map[v] = 1;
        vector<TreeNode*> ans;
        dfs(ans, root, map);
        if (!map[root->val]) ans.push_back(root);
        return ans;
    }
};
// @lc code=end

