/*
 * @lc app=leetcode.cn id=98 lang=cpp
 *
 * [98] 验证二叉搜索树
 */
#include <climits>
#include <utility>
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
    // 前序遍历
    // bool isValidBST(TreeNode* root, long long left = LLONG_MIN, long long right = LLONG_MAX) {
    //     if (root == nullptr) return true;
    //     long long x = root->val;
    //     return x > left && x < right &&
    //             isValidBST(root->left, left, x) &&
    //             isValidBST(root->right, x, right);
    // }



    // 中序遍历
    // long long pre = LLONG_MIN;
    // bool isValidBST(TreeNode* root) {
    //     if (root == nullptr) return true;
    //     if (!isValidBST(root->left) || root->val <= pre)
    //         return false;
    //     pre = root->val;
    //     return isValidBST(root->right);
    // }

    // 返回值 :
    // 当前这颗树的最小值
    // 当前这颗树的最大值
    pair<long long , long long> dfs(TreeNode* node) {
        if (node == nullptr) return {LLONG_MAX, LLONG_MIN};
        auto[l_min, l_max] = dfs(node->left);
        auto[r_min, r_max] = dfs(node->right);
        long long x = node->val;
        // 不合法的情况
        if (x <= l_max || x >= r_min) return {LLONG_MIN, LLONG_MAX};
        return {min(l_min, x), max(r_max, x)};
    }

    // 后序遍历
    bool isValidBST(TreeNode* root) {
        return dfs(root).second != LLONG_MAX;
    }
};
// @lc code=end

