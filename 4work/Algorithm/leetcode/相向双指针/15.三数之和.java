import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
 * @lc app=leetcode.cn id=15 lang=java
 *
 * [15] 三数之和
 */

// @lc code=start

/**
 * @Solution: 三个元素的和为0, 也就可以在选定了一个元素以后, 比如确定了nums[i]以后
 * 找到两数之和为-nums[i], 同时因为三个数字都是不一样的, 所以是从i之后去找
 * 这里的难点有两个
 * 1. 需要给i, j, k都做去重, 并且注意去重的时机, 
 * i应该在第一次碰到这个值的时候正常运行, 后续再碰到的时候去重
 * 2. j, k在一个i找到了一个答案以后, 让k--, j++, 这样才是才会移动到这个i对应的下一个可能的答案
 */
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        int len = nums.length;
        List<List<Integer>> ans = new ArrayList<>();

        for (int i = 0; i < len - 2; i++) {
            if (i > 0 && nums[i] == nums[i-1]) continue;
            if (nums[i] + nums[len-1] + nums[len-2] < 0) continue; // 当前i能匹配到的最大组合都小于0, 说明需要找一个更大的i
            if (nums[i] + nums[i+1] + nums[i+2] > 0) break; // 当前的i能匹配到的最小的组合都大于0, 说明不论i取什么值, 都无法找到==0的组合了
            int target = -nums[i];
            int j = i + 1;
            int k = len - 1;

            // 两数之和问题
            while (j < k) {
                int sum = nums[j] + nums[k];

                if (sum < target) {
                    j++;
                }
                if (sum > target) {
                    k--;
                }

                // 添加答案, 并将j和k移动一位(寻找下一个可能的答案)
                if (sum == target) {
                    ans.add(List.of(nums[i], nums[j], nums[k]));
                    for (j++; j < k && nums[j] == nums[j-1]; j++); // 跳过重复的数字
                    for (k--; j < k && nums[k] == nums[k+1]; k--); // 跳过重复的数字
                }
            }
        }
        return ans;
    }
    public static void main(String[] args) {
        int[] nums = new int[]{-1,0,1,2,-1,-4};
        Solution s = new Solution();
        System.out.println(s.threeSum(nums));
    }
}
// @lc code=end

