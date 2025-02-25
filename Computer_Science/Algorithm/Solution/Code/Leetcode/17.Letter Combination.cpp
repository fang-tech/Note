/*
 * @lc app=leetcode.cn id=17 lang=cpp
 *
 * [17] 电话号码的字母组合
 */
#include <string>
#include <vector>
using namespace std;

// @lc code=start
class Solution {
public:
    
    vector<string> ans;
    vector<string> MAPPING = {"", "", "abc", "def"
    , "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    string path;

    void dfs(string& digits, int i) {
        if (i == digits.length()) {
            ans.emplace_back(path);
            return ;
        }
        for (char c : MAPPING[digits[i] - '0']) {
            // 直接覆盖
            path[i] = c;
            dfs(digits, i + 1);
        }
    }

    vector<string> letterCombinations(string digits) {
        if (digits.empty()) return {};
        path.resize(digits.size(), 0);
        dfs(digits, 0);
        return ans;
    }
};
// @lc code=end
int main() {
    string s = "";
    Solution sol;
    sol.letterCombinations(s);
    return 0;
}

