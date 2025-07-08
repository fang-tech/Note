/*
 * @lc app=leetcode.cn id=46 lang=java
 *
 * [46] 全排列
 */

// @lc code=start

import java.util.ArrayList;
import java.util.List;


class Solution {
    
    private List<List<Integer>> ans = new ArrayList<>();


    // 枚举选择哪个
    public List<List<Integer>> permute(int[] nums) {
        List<Integer> list = new ArrayList<>(nums.length);
        boolean[] onPath = new boolean[nums.length];
        dfs(0, nums, onPath, list);
        return ans;
    }

    private void dfs(int i, int[] nums, boolean[] onPath, List<Integer> list) {
        if (i == nums.length) {
            ans.add(new ArrayList<>(list));
            return;
        }
        for (int j = 0; j < nums.length; j++) {
            if(!onPath[j]) { // 这个节点没有访问过
                onPath[j] = true;
                list.add(nums[j]);
                dfs(i+1, nums, onPath, list);
                list.remove(list.size()-1);
                onPath[j] = false;
            }
        }
    }
}
// @lc code=end

