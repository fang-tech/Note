/*
 * @lc app=leetcode.cn id=128 lang=java
 *
 * [128] 最长连续序列
 */

// @lc code=start

import java.util.HashSet;
import java.util.Set;

class Solution {
    public int longestConsecutive(int[] nums) {
        // 1. 将所有元素都放到集合里面
        Set<Integer> s = new HashSet<>();    
        for (int num : nums) {
            s.add(num);
        }
        
        // 2. 不断查找num+1, num+2...在不在集合里面, 累计长度
        int ans = 0;
        for (int num : s) {
            if (s.contains(num-1)) continue;
            int len = 1;
            int n = num;
            while (s.contains(++n))
                len++;
            ans = Math.max(ans, len);
        }
        return ans;
    }
}
// @lc code=end

