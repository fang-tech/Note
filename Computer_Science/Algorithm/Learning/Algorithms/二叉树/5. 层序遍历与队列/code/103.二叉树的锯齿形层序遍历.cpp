/*
 * @lc app=leetcode.cn id=103 lang=cpp
 *
 * [103] 二叉树的锯齿形层序遍历
 */

#include <algorithm>
#include <vector>
#include <queue>
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
    vector<vector<int>> zigzagLevelOrder(TreeNode* root) {
        if (root == nullptr) return {};
        vector<vector<int>> ans;
        queue<TreeNode*> q;
        q.push(root);
        bool isReverse = true;
        while (!q.empty())
        {
            vector<int> t;
            int n = q.size();
            for (int i = 0; i < n; i++) {
                TreeNode* cur = q.front();
                t.push_back(cur->val);
                q.pop();
                if (isReverse) {
                    if (cur->right) q.push(cur->right);
                    if (cur->left) q.push(cur->left);
                } else {
                    if (cur->left) q.push(cur->left);
                    if (cur->right) q.push(cur->right);
                }
            }
            ans.emplace_back(t);
            isReverse = !isReverse;

        }
        return ans;
    }
};
// @lc code=end

