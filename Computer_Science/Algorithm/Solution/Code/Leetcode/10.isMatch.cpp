/*
 * @lc app=leetcode.cn id=10 lang=cpp
 *
 * [10] 正则表达式匹配
 */

#include <cassert>
#include <string>
#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:

    vector<int> buildNext(string pattern) {
        vector<int> next(pattern.size());
        next[0] = 0;  // 第一个位置一定是0
        
        int j = 0;  // j是前缀末尾
        // i是后缀末尾
        for (int i = 1; i < pattern.size(); i++) {
            // 如果不匹配，j回退
            while (j > 0 && (pattern[i] != pattern[j] || (pattern[i] != '.' && pattern[j] != '.'))) {
                j = next[j-1];  // 回退到上一个可能的位置
            }
            
            // 如果匹配，j前进
            if (pattern[i] == pattern[j] || pattern[i] == '.' || pattern[j] == '.') {
                j++;
            }
            
            next[i] = j;  // 记录当前位置的最长相同前后缀长度
        }
        return next;
    }



    bool isMatch(string s, string p) {
        vector<int> next = buildNext(p);
        int j = 0;  // 模式串的位置
        
        int n = s.size();
        string str = "";
        // 去除s中的重复字符
        for (int i = 0; i < n; i++) {
            if (i + 1 < n && s[i] == s[i+1]) {
                while (s[i] == s[i+1]) i++;
                assert(s[i] != s[i+1]);
                str.push_back(s[i]);
                str.push_back('*');
            }
            else str.push_back(s[i]);
        }

        for (int i = 0; i < str.size(); i++) {
            // 不匹配时，利用 next 数组跳转
            while (j > 0 && (str[i] != p[j] || (p[i] != '.' && p[j] != '.'))) {
                j = next[j-1];  // 跳转到前面的可能匹配位置
            }
            
            // 匹配时，继续向前
            if (str[i] == p[j] || str[i] == '.' || p[j] == '.') {
                j++;
            }
            
            // 找到完整匹配
            if (j == p.size()) {
                return true;
            }
        }
        return false;
    }
};
// @lc code=end
int main() {
    Solution s;
    string str = "aaabbsbvvvvxc";
    string p = "v*xc";
    s.isMatch(str, p);

}

