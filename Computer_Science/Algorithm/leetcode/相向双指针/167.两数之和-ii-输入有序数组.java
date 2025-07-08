/*
 * @lc app=leetcode.cn id=167 lang=java
 *
 * [167] 两数之和 II - 输入有序数组
 */

// @lc code=start
/**
 * @Solution: 这道题目的关键信息就是数组已经按照非递减顺序排列
 * 这样的话, 我们就能根据target的值和现在遍历的左右两个边界的值
 * 决定是左指针向右移动还是右指针向左移动
 * 能这样做的原因是, 移动左指针或右指针对于sum是靠近还是原理target是确定的
 * 右指针向左移动 -> sum减小, 左指针向右移动会 -> sum增大
 * 
 * @Analyze: 
 * 时间复杂度: O(n), 因为每一个元素都只会被遍历一遍
 * 空间复杂度: O(1)
 */
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int right = numbers.length - 1;
        int left = 0;
        while (left < right) { // 遍历区间[left, right]
            int sum = numbers[left] + numbers[right];
            if (sum == target) 
                return new int[]{left+1, right+1};
            if (sum < target) // sum需要增大
                left++;
            else // sum需要减小
                right--;
        }
        return new int[]{};
    }
}
// @lc code=end

