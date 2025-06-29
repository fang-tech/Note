/*
 * @lc app=leetcode.cn id=1 lang=java
 *
 * [1] 两数之和
 */

// @lc code=start

import java.util.HashMap;
import java.util.Map;

class Solution {
    /**
     * hashmap是已经遍历过的数字的集合, 也就是i左边的数
     * 我们每次循环都会检查i左边的范围中, 有没有我们要的数字
     * 并不需要先将所有的数字都加入到hashmap中, 而是在运行时加入的原因是
     * 对于第二个数, 它计算有没有能满足自己值的数字的时候, 集合里虽然只有这一个元素
     * 但是对于第三个数及以后的数, 都计算了自己和第二数的可能, 
     * 所以对于第二个数, 是考虑了所有的可能的
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> hashMap = new HashMap<>(); // k: 值, v: 索引
        for (int i = 0; i < nums.length; i++) {
            Integer n = hashMap.get(target - nums[i]); 
            if (n != null) return new int[]{n, i};
            hashMap.put(nums[i], i);
        }
        return null;
    }
}
// @lc code=end

