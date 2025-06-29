/*
 * @lc app=leetcode.cn id=2826 lang=java
 *
 * [2826] 将三个组排序
 */

// @lc code=start

import java.util.List;

class Solution {

    /**
     * 这道题可以转化成求最长的非递减序列的长度
     * dfs(i+1, j): 对于序列[0, i]中最后一个元素 <= j的最长非递减子序列的长度 nums[i] = x
     * 不选择x, 则问题变成求[0, i-1]中的最后一个元素 <= j的最长非递减子序列的长度, dfs(i+1, j) = dfs(i, j)
     * 选择x, 需要满足x <= j, 
     * 这个时候问题同样变成求[0, i-1]中的最后一个元素 <= x最长非递减子序列的长度   
     * dfs(i+1, j) = dfs(i, x)+1
     * 综合两种情况
     * if x < j : dfs(i+1, j) = dfs(i, j)
     * if x >= j: dfs(i+1, j) = max(dfs(i, x) + 1, dfs(i, j))
     * 这道题目应该是最长递增子序列的变式
     * 在最长递增子序列里面, 在求每个状态的时候, 我们需要遍历前面的所有的状态
     * 也就是前面的所有的递增子序列的长度, 找出来最长的子序列
     * 现在我们直接记录下来每个子序列它的最大的元素 <= j的时候的最长非递减子序列长度
     * 求每个状态的时候就是O(1)的时间复杂度
     * @param nums
     * @return
     */
    public int minimumOperations(List<Integer> nums) {
        int n = nums.size();
        int[][] f = new int[n+1][4];

        for (int i = 0; i < n; i++) {
            int x = nums.get(i);
            for (int j = 1; j < 4; j++) {
                if (x > j) 
                    f[i+1][j] = f[i][j];
                else
                    f[i+1][j] = Math.max(f[i][j], f[i][x]+1);
            }
        }
        return n - f[n][3];
    }

}
// @lc code=end

