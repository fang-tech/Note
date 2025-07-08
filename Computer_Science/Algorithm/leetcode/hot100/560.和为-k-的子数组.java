/*
 * @lc app=leetcode.cn id=560 lang=java
 *
 * [560] 和为 K 的子数组
 */

// @lc code=start

import java.util.HashMap;
import java.util.Map;

class Solution {
    // 这道题目不适用滑动窗口的原因变化不单调, 我们无法决定什么时候移动窗口的边界
    // 这种检索某个范围的内的值是不是等于什么, 可以通过前缀和处理
    // i, j之间的值 = s[j] - s[i]
    // 所以题目就变成了有多少对s[j] - s[i] = k
    // 枚举s[j]就变成了对于s[j]有多少是s[i]==s[j] - k
    // 其实就变成了两数之和问题, 但是有很多重复的数字, 
    // 我们需要找出来有多少对满足要求的数字对
    // key: 值, value: 这个值的个数
    public int subarraySum(int[] nums, int k) {
        // 计算前缀和
        // 暴力的做法是枚举, 但是我们现在能通过将已经枚举过的数字添加到map中
        // 将已经遍历过的s[j]添加到map中, 将他的次数+1
        int s = 0;
        int ans = 0;
        Map<Integer, Integer> cnt = new HashMap<>(nums.length+1);
        for (int num : nums) {
            cnt.merge(s, 1, Integer::sum); // cnt[s[i-1]]++
            s += num; //s[i]
            ans += cnt.getOrDefault(s-k, 0); // ans += cnt[s[i]-k]
        }

        return ans;
    }
}
// @lc code=end

