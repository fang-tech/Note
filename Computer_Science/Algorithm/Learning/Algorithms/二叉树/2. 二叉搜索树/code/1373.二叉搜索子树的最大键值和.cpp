/*
 * @lc app=leetcode.cn id=1373 lang=cpp
 *
 * [1373] 二叉搜索子树的最大键值和
 */
#include <algorithm>
#include <climits>
#include <tuple>
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

    int ans = 0;

    // 子树的最大值, 最小值和子树的所有节点和
    tuple<int, int, int> dfs(TreeNode* root) {
        if (root == nullptr) return {INT_MIN, INT_MAX, 0};

        auto [l_mx, l_mn, l_s] = dfs(root->left);
        auto [r_mx, r_mn, r_s] = dfs(root->right);
        int x = root->val;

        // 当前树不是合法的二叉搜索树
        if (x <= l_mx || x >= r_mn) return {INT_MAX, INT_MIN, 0};
        
        // 进入了这个位置, 说明这是一颗合法的二叉树
        int sum = l_s + r_s + x;
        ans = max(sum, ans);
        return {max(x, r_mx), min(l_mn, x), sum};
    }

    int maxSumBST(TreeNode* root) {
        dfs(root);
        return ans;
    }
};
// @lc code=end

