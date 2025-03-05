/*
 * @lc app=leetcode.cn id=22 lang=cpp
 *
 * [22] 括号生成
 */

#include <string>
#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    
    string path = "";
    vector<string> ans;
    
    // 我们现在遍历到可选括号数组中的第i个位置
    void dfs(int n, int i, int open) {
        if (i == n) {
            ans.emplace_back(path);
            return;
        }
        
        // 选左括号< 也就是现在左括号的数量 < n / 2的时候
        if (open < n / 2) {
            path += '(';
            dfs(n, i+1, open+1);
            path.pop_back();
        }
        // 选右括号, 也就是现在还有左括号能匹配, 也就是path中右括号的数量 < 左括号的数量
        if ((i - open) < open) {
            path += ')';
            dfs(n, i+1, open);
            path.pop_back();
        }
    }

    vector<string> generateParenthesis(int n) {
        dfs(2*n, 0, 0);
        return ans;
    }
};
// @lc code=end

