/*
 * @lc app=leetcode.cn id=209 lang=cpp
 *
 * [209] 长度最小的子数组
 */

#include <iostream>
#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    int minSubArrayLen(int target, vector<int>& nums) {
        int n = nums.size();
        int ans = n+1; // 用于判别不存在符合条件的数组的情况
        int left =0;
        int sum = 0;
        // 写法1 : 在while循环内更新ans
        // for (int right = 0; right < n; right++){
        //     sum += nums[right];
        //     while (sum >= target) {
        //         ans = min(ans, right-left+1);
        //         sum -= nums[left++];
        //     }
        // }
        
        // 写法2 : 在while循环外更新ans
        for (int right = 0; right < n; right++) {
            sum += nums[right];
            while (sum - nums[left] >= target) {
                sum -= nums[left++];
            }
            if (sum >= target) ans = min(ans, right-left+1);
            
        }
        return ans <= n ? ans : 0;
    }
};
// @lc code=end

