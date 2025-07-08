/*
 * @lc app=leetcode.cn id=283 lang=java
 *
 * [283] 移动零
 */

// @lc code=start

class Solution {
    public void moveZeroes(int[] nums) {
        int zero = 0;
        // 找到第一个0的位置
        for (; zero < nums.length && nums[zero] != 0 ; zero++);
        if (zero == nums.length) return; // 说明没有0, 直接返回

        // 从第一个非0的元素开始
        for (int r = zero+1; r < nums.length; r++) {
            // r指向不是0的元素
            if (nums[r] == 0) 
                continue;

            // 将0视作空位, 我们需要将元素移动到空位上
            nums[zero] = nums[r];
            nums[r] = 0;
            // 找到下一个空位
            zero++;
        }
    }

    // 03xf的做法
    private void ling(int[] nums) {
        int i0 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) { 
                // 通过将两个数字交换的方式, 避免了处理所有元素都是非0元素的特殊情况
                int temp = nums[i0]; 
                nums[i0] = nums[i];
                nums[i] = temp;
                // 如果有0元素, 下一个零元素一定是在上一个0元素的后面
                i0++;
            }
        }
    }
}
// @lc code=end

