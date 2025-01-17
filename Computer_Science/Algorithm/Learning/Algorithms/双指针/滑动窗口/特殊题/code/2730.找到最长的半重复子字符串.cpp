/*
 * @lc app=leetcode.cn id=2730 lang=cpp
 *
 * [2730] 找到最长的半重复子字符串
 */

#include <unordered_map>
#include <vector>
#include <string>
#include <iostream>
using namespace std;
// @lc code=start
class Solution {
public:
    int longestSemiRepetitiveSubstring(string s) {
        int n = s.length();
        int left = 0;
        int ans = 0;
        int repeat_idx = -1;
        for (int right = 0; right < n; right++) {
            char c = s[right];
            if (right > 0 && c == s[right-1]) {
                // 新增了一对相邻相同数字对
                if (repeat_idx == -1) {
                    // 窗口中没有相邻相同数字对
                    repeat_idx = right;
                }
                else {
                    // 已经有了一对数字, 这个时候要拆除那对数字
                    left = repeat_idx;
                    repeat_idx = right;
                }
            }
            ans = max(ans, right - left + 1);
        }
        return ans == 0 ? n : ans;
    }
};
// @lc code=end

