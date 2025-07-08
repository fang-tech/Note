/*
 * @lc app=leetcode.cn id=41 lang=java
 *
 * [41] 缺失的第一个正数
 */

// @lc code=start
class Solution {

    /**
     * 有n个学生, 索引+1就是他们的学号, n个座位, 座位的编号是1~n
     * 我们将学生安排到座位上, 
     * 则第一个座位号和学号不一样的位置的座位号就是缺失的第一个正数
     */
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            // 学号和自己现在的座位编号是一样的, 
            // 或者真身已经坐到了正确的位置, 自己是影分身, 都要跳过
            while (1 <= nums[i] && nums[i] <= n && nums[i] != nums[nums[i]-1]) {
                swap(nums, i, nums[i]-1);
            }
        }

        // 找第一个学号和座位号不匹配的学生, 返回座位号
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) 
                return i+1;
        }
        return n+1;
    }

    private void swap(int[] nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }
}
// @lc code=end

