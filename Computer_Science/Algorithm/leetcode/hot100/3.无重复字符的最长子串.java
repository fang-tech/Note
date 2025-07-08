/*
 * @lc app=leetcode.cn id=3 lang=java
 *
 * [3] 无重复字符的最长子串
 */

// @lc code=start

import java.util.HashSet;
import java.util.Set;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        char[] arr = s.toCharArray();
        int left = 0;
        int ans = 0;
        int len = 0;
        for (int right = 0; right < arr.length; right++) {
            char ch = arr[right];
            // 如果存在, 则向右移动窗口左端点, 直到弹出ch元素
            while (set.contains(ch)) {
                set.remove(arr[left++]);
                len--;
            }
            set.add(ch);
            len++;
            ans = Math.max(len, ans);
        }
        return ans;
    }
}
// "pwwkew"\n
// @lc code=end

