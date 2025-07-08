/*
 * @lc app=leetcode.cn id=76 lang=java
 *
 * [76] 最小覆盖子串
 */

// @lc code=start

import java.util.HashMap;
import java.util.Map;

class Solution {
    /**
     * 这道题目属于最短型滑动窗口
     * 不同于利用单调性的滑动窗口, 这里滑动的逻辑是
     * 如果找到了一种答案, 则将左边界滑动到失去答案为止, 并在过程中更新答案
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        // k: char, v: count(出现次数)
        Map<Character, Integer> cnt = new HashMap<>();
        int less = 0; // t中还没有被覆盖的字母的数量
        for (char ch : t.toCharArray()) {
            if (!cnt.containsKey(ch)) less++;
            cnt.merge(ch, 1, Integer::sum);
        }

        int left = 0; int minLeft = -1; int minRight = s.length();
        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            // 1. 进入滑动窗口
            cnt.merge(ch, -1, Integer::sum);
            if (cnt.get(ch) == 0) less--;
            // 2. 检查
            // 如果现在的窗口中覆盖了子串
            while (less == 0) {
                // 如果找到了更短的子串
                // 更新答案
                if (right - left < minRight - minLeft){
                    minLeft = left; minRight = right;
                }
                // 3. 移出滑动窗口
                cnt.merge(s.charAt(left), 1, Integer::sum); // cnt[s[left]]++;
                if (cnt.get(s.charAt(left)) == 1) less++;
                left++;
            }
        }

        return minLeft < 0 
                ? ""
                : s.substring(minLeft, minRight+1);
    }   

    private boolean check(Map<Character, Integer> cnt) {
        for (Integer value : cnt.values()) {
            if (value > 0) return false;
        }
        return true;
    }
}
// @lc code=end

