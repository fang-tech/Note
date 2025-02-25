/*
 * @lc app=leetcode.cn id=889 lang=cpp
 *
 * [889] 根据前序和后序遍历构造二叉树
 */
#include <vector>
#include <unordered_map>
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

    // 区间采取左闭右开
    TreeNode* build(vector<int>& preorder, int preL, unordered_map<int, int> index, int postL, int postR) {
        // 区间内没有元素的时候
        if (postL == postR) return nullptr;
        if (postR - postL == 1) return new TreeNode(preorder[preL]);
        
        int r = preorder[preL];
        TreeNode* root = new TreeNode(r);
        int left_size = index[preorder[preL+1]] - postL + 1;
        root->left = build(preorder, preL + 1, index, postL, postL + left_size);
        root->right = build(preorder, preL + 1 + left_size, index, postL + left_size, postR - 1);
        return root;
    }

    TreeNode* constructFromPrePost(vector<int>& preorder, vector<int>& postorder) {
        unordered_map<int, int> index;
        for (int i = 0; i< postorder.size(); i++) {
            index[postorder[i]] = i;
        }
        return build(preorder, 0, index, 0, postorder.size());
    }
};
// @lc code=end

