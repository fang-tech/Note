/*
 * @lc app=leetcode.cn id=76 lang=cpp
 *
 * [76] 最小覆盖子串
 */

#include <string>
#include <iostream>

using namespace std;
// @lc code=start
class Solution {
public:
    string minWindow(string s, string t) {
        int ansLeft=-1, ansRight=s.length();
        int cnt[128] = {0};
        int less = 0;
        for (char c: t) {
            if (cnt[c] == 0)
                less++;
            cnt[c]++;
        }
        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s[right];
            cnt[c]--;
            if (cnt[c] == 0)
                less--;
            while (less == 0) {
                // 更新答案
                if (ansRight - ansLeft > right - left) {
                    ansRight = right; ansLeft = left;
                }
                char x = s[left];
                if (cnt[x] == 0) {
                    less++;
                } 
                cnt[x]++;
                left++;
            }
        }
    return ansLeft < 0 ? "" : s.substr(ansLeft, ansRight - ansLeft + 1);
    }
};
// @lc code=end
int main(){
    Solution solu;
    string s = "aa";
    string t = "aa";
    cout << solu.minWindow(s,t);
    return 0;
}



