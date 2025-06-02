package DP.LIS最长递增子序列;

import java.util.ArrayList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=300 lang=java
 *
 * [300] 最长递增子序列
 */

// @lc code=start
class Solution {
    public int lengthOfLIS(int[] nums) {
        // g[i] 长度为i的递增子序列末尾元素的最小值
        List<Integer> g = new ArrayList<>();
        
        for (int n : nums) {
            int i = lowerBound(g, n);
            if (i == g.size()) g.add(n); // 这个元素比所有的元素都大, 直接添加到数组尾
            else g.set(i, n); // g[i] <= n, g[i+1] > n;
        }
        return g.size();
    }

    // (left, right) 返回的是数组中第一个 >= target的元素的idx
    private int lowerBound(List<Integer> g, int target) {
            int left = -1;
            int right = g.size();
            // nums[left] < target
            // nums[right] >= target
            while (left < right - 1) {
                int mid = left + (right - left) / 2;
                if (g.get(mid) < target) 
                    left = mid; 
                else 
                    right = mid;
            }
            return right;
    }
}
// @lc code=end

