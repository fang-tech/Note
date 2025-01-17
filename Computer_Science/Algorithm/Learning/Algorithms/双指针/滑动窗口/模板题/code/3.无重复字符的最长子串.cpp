/*
 * @lc app=leetcode.cn id=3 lang=cpp
 *
 * [3] 无重复字符的最长子串
 */

#include <unordered_set>
#include <string>
using namespace std;
// @lc code=start
class Solution {
public:
    int lengthOfLongestSubstring(string s) {
        int n = s.length();
        int left = 0;
        int ans = 0;
        unordered_set<char> hash;
        for (int right = 0; right < n; right++) {
            char c = s[right];
            while (hash.count(c) > 0){
                hash.erase(s[left]);
                left++;
            }
            hash.insert(c);
            ans = max(ans, right - left + 1);
        }

        return ans;
    }
};
// @lc code=end

