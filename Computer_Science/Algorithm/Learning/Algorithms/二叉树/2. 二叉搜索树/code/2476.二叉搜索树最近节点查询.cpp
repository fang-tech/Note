/*
 * @lc app=leetcode.cn id=2476 lang=cpp
 *
 * [2476] 二叉搜索树最近节点查询
 */

struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode() : val(0), left(nullptr), right(nullptr) {}
    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
    TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
};

#include <vector>
#include <algorithm>
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
    
    vector<int> a;
    void dfs(TreeNode* root) {
        if (root == nullptr) return;
        dfs(root->left);
        a.push_back(root->val);
        dfs(root->right);
    }

    vector<vector<int>> closestNodes(TreeNode* root, vector<int>& queries) {
        vector<vector<int>> ans;
        dfs(root);
        int n = a.size();

        for (auto q : queries) {
            int j = lower_bound(a.begin(), a.end(), q) - a.begin();
            // mx >= q
            int mx = j < n ? a[j] : -1;
            // mx <= q
            int mn;
            if (j == n) mn = a[j-1];
            else if(a[j] == q) mn = q;
            else if(j > 0) mn = a[j-1];
            else mn = -1;
            ans.push_back({mn, mx});
        }
        return ans;
    }
};
// @lc code=end

