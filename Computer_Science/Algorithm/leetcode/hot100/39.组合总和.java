/*
 * @lc app=leetcode.cn id=39 lang=java
 *
 * [39] 组合总和
 */

// @lc code=start

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<Integer> path = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        dfs(0, candidates, target, path, ans);
        return ans;
    }

    /**
     * 子集型题目, 选和不选
     * 不过这里可以重复选同一个数字
     * @param i
     * @param candidates
     * @param target
     */
    private void dfs(int i, int[] candidates, int target, List<Integer> path, List<List<Integer>> ans) {
        if (target == 0) { // 找到了答案
            ans.add(new ArrayList<>(path));
            return;
        }
        if (i >= candidates.length) return;

        int x = candidates[i];
        // 选当前元素
        if (target - x >= 0) {
            path.add(x);
            dfs(i, candidates, target-x, path, ans);
            path.remove(path.size()-1);
        }

        // 不选当前元素
        dfs(i+1, candidates, target, path, ans);
    }

}
// @lc code=end

