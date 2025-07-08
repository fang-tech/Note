/*
 * @lc app=leetcode.cn id=22 lang=java
 *
 * [22] 括号生成
 */

// @lc code=start

import java.util.ArrayList;
import java.util.List;

class Solution {
    
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        char[] path = new char[2*n];
        dfs(n, n, ans, path, 0);
        return ans;
    }

    /**
     * 全排列问题, 这里是有两种选择, 但是只能在rightNum > leftNum的时候选右括号
     * @param leftNum
     * @param rightNum
     * @param ans
     * @param path
     * @param i
     */
    private void dfs(int leftNum, int rightNum, List<String> ans, char[] path, int i) {
        if (leftNum == 0 && rightNum == 0) {
            ans.add(new String(path));
            return;
        }

        // 选左括号
        if (leftNum > 0) {
            path[i] = '(';
            dfs(leftNum-1, rightNum, ans, path, i+1);
        }

        if (rightNum > 0 && rightNum > leftNum) {
            path[i] = ')';
            dfs(leftNum, rightNum-1, ans, path, i+1);
        }
    }
}
// @lc code=end

