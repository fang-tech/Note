/*
 * @lc app=leetcode.cn id=2415 lang=cpp
 *
 * [2415] 反转二叉树的奇数层
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
    TreeNode* reverseOddLevels(TreeNode* root) {
        bool isReverse = false; // 当前一层是不是奇数层, 第一层不是
        deque<TreeNode*> q;
        q.push_back(root);
        while (!q.empty()) {
            int n = q.size();
            // 对于奇数层, 交换当前层中节点的值
            if (isReverse) {
                deque<TreeNode*> t = q;
                for (int i = 0; i < q.size() / 2; i++) {
                    auto last = t.back(); t.pop_back();
                    auto head = t.front(); t.pop_front();
                    int temp = last->val;
                    last->val = head->val;
                    head->val = temp;
                }
            }
            for (int i = 0; i < n; i++) {
                auto node = q.front();
                q.pop_front();
                if (node->left) q.push_back(node->left);
                if (node->right) q.push_back(node->right);
            }
            isReverse = !isReverse;
        }
        return root;
    }
};
// @lc code=end

