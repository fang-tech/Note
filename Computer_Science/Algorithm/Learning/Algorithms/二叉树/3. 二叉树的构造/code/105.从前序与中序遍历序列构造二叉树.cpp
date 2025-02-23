/*
 * @lc app=leetcode.cn id=105 lang=cpp
 *
 * [105] 从前序与中序遍历序列构造二叉树
 */

#include <algorithm>
#include <unordered_map>
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
    TreeNode* build(vector<int>& preorder, int preBegin,unordered_map<int,int>& index, vector<int>& inorder, int inBegin, int inEnd) {
        // 区间内没有元素的时候
        if (inEnd - inBegin < 0) return nullptr;
        // 从先序序列中获取根节点的值
        int r = preorder[preBegin];
        TreeNode* root = new TreeNode(r);
        // 在中序序列中找到根节点, 从而将数组分成两部分传递
        int pos = index[r];
        // 左子树中有多少个元素
        int l_cnt = pos - inBegin;
        // 创建左子树
        root->left = build(preorder, preBegin + 1,index, inorder, inBegin, pos - 1);
        // 创建右子树
        root->right = build(preorder, preBegin + l_cnt + 1, index, inorder, pos + 1, inEnd);
        return root;
    }

    TreeNode* buildTree(vector<int>& preorder, vector<int>& inorder) {
        unordered_map<int, int> index;
        for (int i = 0; i < inorder.size(); i++)
            index[inorder[i]] = i;
        return build(preorder,0,index,inorder,0, inorder.size()-1);
    }
};
// @lc code=end

