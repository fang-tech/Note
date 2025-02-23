/*
 * @lc app=leetcode.cn id=110 lang=cpp
 *
 * [110] 平衡二叉树
 */
#include <algorithm>
#include <winnt.h>

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
    int get_height(TreeNode* node) {
        if (node == nullptr) return 0;
        int leftH = get_height(node->left);
        if (leftH == -1) return -1;
        int rightH = get_height(node->right);
        // 高度差大于1的时候返回-1, 也就是非法的时候的返回
        if (rightH == -1 || abs(leftH - rightH) > 1) return -1;
        // 正常的时候的返回
        return max(get_height(node->left), get_height(node->right)) + 1;
    }

    bool isBalanced(TreeNode* root) {
        return get_height(root) != -1;
    }
};
// @lc code=end

