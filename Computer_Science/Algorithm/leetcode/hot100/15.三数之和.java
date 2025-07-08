/*
 * @lc app=leetcode.cn id=15 lang=java
 *
 * [15] 三数之和
 */

// @lc code=start

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        // 将数组排序
        Arrays.sort(nums);

        List<List<Integer>> ans= new ArrayList<>();

        // 按照两数之和-输入有序数组的写法, 
        // 遍历第一个数, 对剩下的两个数使用双指针
        // 这里需要额外注意的就是答案中不能有重复的答案
        // 同时注意, 跳过重复数字, 并不是跳过第一个数字, 而是跳过第二个数字
        for (int i = 0; i < nums.length - 2; i++) {
            // 跳过重复的i
            if (i > 0 && nums[i] == nums[i-1]) continue;
            int x = nums[i];
            int left = i + 1;
            int right = nums.length - 1;
            if (x + nums[i+1] + nums[i+2] > 0) break; // 优化一: 如果最小的三个数都大于0, 不再存在==0 的组合
            if (x + nums[right] + nums[right-1] < 0) continue; // 优化二: 最大的三个数<0, 说明这个i不能组合出==0的组合

            while (left < right) {
                int sum = x + nums[left] + nums[right];
                if (sum == 0) { // 找到答案了
                    List<Integer> list = new ArrayList<>();
                    list.add(x); list.add(nums[left]); list.add(nums[right]);
                    ans.add(list);
                    // 寻找下一组答案, 并去重, 下一组答案一定是要将left和right至少都移动一位的
                    for (left++; left < right && nums[left] == nums[left-1]; left++); 
                    for (right--; right > left && nums[right] == nums[right+1]; right--);
                } else if (sum > 0) {
                    right--;
                } else {
                    left++;
                }
            }
        }
        return ans;
    }
}
// @lc code=end

