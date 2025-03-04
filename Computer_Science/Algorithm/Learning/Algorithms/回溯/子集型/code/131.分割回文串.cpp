/*
 * @lc app=leetcode.cn id=131 lang=cpp
 *
 * [131] 分割回文串
 */
#include <string>
#include <vector>
using namespace std;

// @lc code=start
class Solution {
public:
    
    vector<string> path;
    vector<vector<string>> ans;

    bool isPalindrome(const string& s, int left, int right) {
        while (left < right) {
            if (s[left++] != s[right--]) return false;
        }
        return true;
    }

    void dfs(const string& s, int i) {
        int n = s.length();
        if (i == n) {
            ans.emplace_back(path);
            return;
        }

        // 枚举子串结束的位置
        for (int j = i; j < n; j++) {
            if (isPalindrome(s, i, j)) {
                path.emplace_back(s.substr(i, j - i + 1));
                dfs(s, j+1);
                path.pop_back(); // 恢复现场
            }
        }
    }

    vector<vector<string>> partition(string s) {
        dfs(s, 0);
        return ans;
    }
};
// @lc code=end

