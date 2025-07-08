/*
 * @lc app=leetcode.cn id=49 lang=java
 *
 * [49] 字母异位词分组
 */

// @lc code=start

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> m = new HashMap<>();
        for (String str : strs) {
            // 1. 将字符排序
            char[] arr = str.toCharArray();
            Arrays.sort(arr);
            // 2. k: 排序后的字符串, v: 原字符串
            m.computeIfAbsent(new String(arr), v -> new ArrayList<String>()).add(str);
            // 两个字符串排序后相同, 说明就是同一类的字符串
        }
        return new ArrayList<>(m.values());
    }
}
// @lc code=end

