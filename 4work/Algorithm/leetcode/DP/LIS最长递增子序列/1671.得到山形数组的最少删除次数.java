/*
 * @lc app=leetcode.cn id=1671 lang=java
 *
 * [1671] 得到山形数组的最少删除次数
 */

/**
 * 题目要求的其实就是在i前面是最长的递增子序列, i后面的是最长递减子序列
 * Incr[i]: 以nums[i]结尾的最长递增子序列的长度
 * Decr[i]: 以nums[i]开始的最长递减子序列的长度
 *  (反向去遍历数组就能达到目的)
 *  dfs(i) = Decr[i], dfs(i) = max{dfs(j)} + 1
 * 我们需要选出来一个最好i来让Incr[i] + Decr[i] - 1最大
 */
// @lc code=start
class Solution {
    private int[] incr;
    private int[] decr;
    private int[] nums;

    public int minimumMountainRemovals(int[] nums) {
        int n = nums.length;
        this.nums = nums;
        incr = new int[n]; decr = new int[n];

        int ans = 0;
        for (int i = 1; i < n-1; i++) { // i只会在[1, length-2]中选出来
            if (decrDfs(i) > 1 && incrDfs(i) > 1) // 题目中隐含了这个递增和递减序列的长度 > 1
                ans = Math.max(ans, decrDfs(i) + incrDfs(i));
        }
        return n - (ans - 1);
    }

    private int incrDfs(int i) {
        if (incr[i] != 0) return incr[i];

        for (int j = 0; j < i; j++) {
            if (nums[i] > nums[j])
                incr[i] = Math.max(incr[i], incrDfs(j));
        }
        
        return ++incr[i];
    }

    private int decrDfs(int i) {
        if (decr[i] != 0) return decr[i];

        for (int j = nums.length-1; j > i; j--) {
            if (nums[i] > nums[j])
                decr[i] = Math.max(decr[i], decrDfs(j));
        }

        return ++decr[i];
    }

    public static void main(String[] args) {
        int[] nums = {9,8,1,7,6,5,4,3,2,1};
        Solution s = new Solution();
        int ans = s.minimumMountainRemovals(nums);
        System.out.println(ans);
    }
}
// @lc code=end

