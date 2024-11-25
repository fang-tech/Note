# P124.[二叉树中的最大路径和](https://leetcode.cn/problems/binary-tree-maximum-path-sum/description/)

## 题解

- 这题的难点是如何搜索完所有的情况以及什么地方可能出现最大值
  - 如何搜索完所有的情况 : 这是一道"树"搜索题目, 所以很自然地就会去使用dfs遍历搜索
  - 什么地方会出现最大值 : 1. 当前节点和它的"左最大和"与"右最大和"三数之和, 如果左最大和或者右最大和<0, 则直接赋值为0, 说明这条路径不走能获得更大的路径和

## code

```cpp
/*
 * @lc app=leetcode.cn id=124 lang=cpp
 *
 * [124] 二叉树中的最大路径和
 */
#include <iostream>
#include <vector>
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
private:
    int dfs(TreeNode* root, int& ans) {
        if (root == nullptr) return 0;
        TreeNode* l = root->left; TreeNode* r = root->right;
        int left = dfs(l, ans); int right = dfs(r, ans);
        int maxSum = root->val;
        ans = max(ans, left + right + root->val);
        maxSum += max(left, right);
        return maxSum > 0 ? maxSum : 0;
    }
public:
    int maxPathSum(TreeNode* root) {
        int ans = root->val;
        dfs(root, ans);
        return ans;
    }
};
// @lc code=end
```



