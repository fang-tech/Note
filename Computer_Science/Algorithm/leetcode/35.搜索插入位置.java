/*
 * @lc app=leetcode.cn id=35 lang=java
 *
 * [35] 搜索插入位置
 */

// @lc code=start
class Solution {
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length-1;
        // if left == right - 1 means null
        // nums[left] <= target
        // nums[right] > target
        // left is last num <= target
        while (left != right+1) {
            int mid = left + (right - left) / 2;
            int n = nums[mid];
            if (n <= target) 
                left = mid+1;
            else 
                right = mid-1;
        }
        System.out.println(left + " " + right);
        return right;
    }
}
// @lc code=end

