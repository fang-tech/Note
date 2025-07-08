/*
 * @lc app=leetcode.cn id=131 lang=java
 *
 * [131] 分割回文串
 */

// @lc code=start

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Solution {

    public List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<>();
        List<String> path = new ArrayList<>();
        dfs(0, 0, s, ans, path);
        return ans;
    }

    /**
     * 这个位置切割还是不切割 == 选还是不选
     * @param i
     * @param word
     * @param ans
     * @param path
     */
    private void dfs(int i, int j, String word, List<List<String>> ans, List<String> path) {
        if (i == word.length()) {
            ans.add(new ArrayList<>(path));
            return;
        }

        // 在当前位置分割, word[i]和下一个元素之间是切割点
        if (check(word, j, i)) { // 如果是回文串
            path.add(word.substring(j, i+1));
            dfs(i+1, i+1, word, ans, path);
            path.remove(path.size()-1);
        }

        // 不分割, 最后一个元素必须分割
        if (i < word.length() - 1)
            dfs(i+1, j, word, ans, path);
    }

    /**
     * 是不是回文串
     * @param word
     * @param l
     * @param r
     * @return
     */
    private boolean check(String word, int l, int r) {
        while (l < r) {
            if (word.charAt(l++) != word.charAt(r--)) return false;
        }
        return true;
    }


}
// @lc code=end

