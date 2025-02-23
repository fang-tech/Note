/*
 * @lc app=leetcode.cn id=2641 lang=cpp
 *
 * [2641] 二叉树的堂兄弟节点 II
 */

#include <deque>
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
    TreeNode* replaceValueInTree(TreeNode* root) {
        deque<TreeNode*> q;
        q.push_back(root);
        root->val = 0;
        while (!q.empty()) {
            // 下一层所有节点的和
            int sum = 0;
            int n = q.size();
            // 获取下一层的节点值之和
            for (auto node : q){
                if (node->left) sum += node->left->val;
                if (node->right) sum += node->right->val;
            }

            for (int i = 0; i < n; i++) {
                // 当前节点的子节点的和
                int s = 0;
                auto node = q.front();
                q.pop_front();
                if (node->left) s += node->left->val;
                if (node->right) s += node->right->val;
                
                // 赋值给当前节点的堂兄弟节点和
                if (node->left) node->left->val = sum - s, q.push_back(node->left);
                if (node->right) node->right->val = sum - s, q.push_back(node->right);
            }
        }
        return root;
    }
};
// @lc code=end

