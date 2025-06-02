/*
 * @lc app=leetcode.cn id=3566 lang=java
 *
 * [3566] 等积子集的划分方案
 */

// @lc code=start
class Solution {

    private int[] nums;
    private long target;
    private long mul1 = 1;
    private long mul2 = 1;
    
    /**
     * 使用子集型回溯
     * 时间复杂度: O(2^n), 对于每个元素都有选和不选两种方案, 最后组成一颗高度为n的子集树
     * 空间复杂度: O(n)
     * @param nums
     * @param target
     * @return
     */
    public boolean checkEqualPartitions(int[] nums, long target) {
        this.nums = nums; this.target = target;
        return dfs(0);
    }

    /**
     * 这道题目选和不选具有天然的编码优势
     * 选到了set1, 就将m1乘
     * 不选set1, 其实就是将这个元素选到了set2, m2乘
     */
    private boolean dfs(int i){
        // 有一边计算出来的乘积 > target, 就说明这个分配方案已经是不可能的了
        if (mul1 > target || mul2 > target) return false;
        if (i == nums.length) return (mul1 == target && mul2 == target);

        int n = nums[i];
        // 当前元素给set1
        mul1 *= n;
        boolean ans1 = dfs(i + 1);
        mul1 /= n;

        // 给set2
        mul2 *= n;
        boolean ans2 = dfs(i + 1);
        mul2 /= n;

        return ans2 || ans1;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{40,15,92,65,42,7,80,17,46,68,78,48};
        long target = 4098931200L;
        Solution s = new Solution();
        boolean ans = s.checkEqualPartitions(nums, target);
        System.out.println(ans);
    }
}
// @lc code=end