/*
 * @lc app=leetcode.cn id=78 lang=java
 *
 * [78] 子集
 */

// @lc code=start

import java.util.ArrayList;
import java.util.List;

class Solution {
    
    private List<List<Integer>> ans;

    public List<List<Integer>> subsets(int[] nums) {
        ans = new ArrayList<>(nums.length);
        List<Integer> path = new ArrayList<>(nums.length);
        dfs(0, nums, path);
        return ans;
    }

    // 枚举选择
    private void dfs(int i, int[] nums, List<Integer> path) {
        ans.add(new ArrayList<>(path));

        for (int j = i; j < nums.length; j++) {
            path.add(nums[j]);
            dfs(j+1, nums, path);
            path.remove(path.size()-1);
        }
    }

    // 选或者不选
    private void dfs1(int i, int[] nums, List<Integer> path) {
        if (i == nums.length) {
            ans.add(new ArrayList<>(path));
            return;
        }

        // 选
        path.add(nums[i]);
        dfs1(i+1, nums, path);
        path.remove(path.size()-1);

        // 不选当前元素
        dfs1(i+1, nums, path);
    }
}
// @lc code=end

