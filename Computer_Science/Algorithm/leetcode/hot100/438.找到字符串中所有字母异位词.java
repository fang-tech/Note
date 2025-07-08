/*
 * @lc app=leetcode.cn id=438 lang=java
 *
 * [438] 找到字符串中所有字母异位词
 */

// @lc code=start

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    // 不定长滑动窗口
    // 当窗口中s'中的每个元素的目标数量就是这个元素在p中的目标数量
    // 如果每个元素的数量都一样的时候
    // 并且s'的长度和p一样, 这个时候就是找到了
    // 在加入元素的时候保证该元素在s'中的出现次数是小于等于p中的出现次数的时候
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> ans = new ArrayList<>();
        int[] cnt = new int[26];
        for (char ch : p.toCharArray()) {
            cnt[ch - 'a']++;
        }

        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            // 新加入ch元素
            cnt[ch - 'a']--;
            // 说明滑动窗口中的ch元素太多的时候, 弹出元素直到数量正常
            while (cnt[ch - 'a'] < 0) {
                cnt[s.charAt(left) - 'a']++;
                left++;
            }
            // s'中的每个元素的数量都是正确的
            if (right - left + 1 == p.length()) {
                ans.add(left);
            }
        }
        return ans;
    }
    
    // 不定长滑动窗口
    // 使用hash表记录p中的所有词的出现次数
    // 移动窗口, 当表中的所有元素都为0的时候, 为出现了一次异位词
    public List<Integer> findAnagrams1(String s, String p) {
        // k: 字符ch, v: 字符ch出现的次数
        Map<Character, Integer> m = new HashMap<>();
        char[] pArr = p.toCharArray(); char[] sArr = s.toCharArray();
        for (char ch : pArr) {
            m.put(ch, m.getOrDefault(ch, 0) + 1);
        }

        int n = p.length();
        List<Integer> ans = new ArrayList<>();
        if (n > s.length()) return ans; // p的长度超过了s

        for (int right = 0; right < sArr.length; right++) {
            char chRight = sArr[right];

            // 移动窗口的右边界
            if (m.containsKey(chRight)) {
                // 新增元素
                m.put(chRight, m.get(chRight)-1);
            }
            int left = right - p.length() + 1;
            
            // 移动窗口的左边界
            if (left < 0) {
                continue;
            }
            if (check(m)) { // 如果组成了异位词
                ans.add(left);
            }
            // 移除左边的将被弹出的元素
            if (m.containsKey(sArr[left])) 
                m.put(sArr[left], m.get(sArr[left])+1);
            left++;
        }

        return ans;

    }

    private boolean check(Map<Character, Integer> map) {
        for (Integer value : map.values()) {
            if (value != 0) return false;
        }
        return true;
    }
}
// @lc code=end

