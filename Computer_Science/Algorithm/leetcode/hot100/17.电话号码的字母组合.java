/*
 * @lc app=leetcode.cn id=17 lang=java
 *
 * [17] 电话号码的字母组合
 */

// @lc code=start

import java.util.ArrayList;
import java.util.List;

class Solution {
    private char[][] MAP = new char[][]{{},{},{'a', 'b', 'c'}, {'d','e','f'}, {'g','h','i'}, 
                    {'j','k','l'}, {'m','n','o'}, {'p','q','r','s'}, {'t','u','v'},
                    {'w','x','y','z'}};

    public List<String> letterCombinations(String digits) {
        if (digits.length() == 0) return new ArrayList<>();
        char[] str = digits.toCharArray();
        List<String> ans = new ArrayList<>();
        char[] path = new char[digits.length()];
        dfs(0, str, path, ans); 
        return ans;
    }

    private void dfs(int i, char[] digits, char[] path, List<String> ans) {
        if (i == digits.length) {
            ans.add(new String(path));
            return;
        }

        for (char c : MAP[digits[i] - '0']) {
            path[i] = c;
            dfs(i+1, digits, path, ans);
        }
    }
}
// @lc code=end

