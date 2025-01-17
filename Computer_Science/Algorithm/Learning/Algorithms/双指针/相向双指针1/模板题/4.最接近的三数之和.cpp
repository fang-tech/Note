/*
 * @lc app=leetcode.cn id=16 lang=cpp
 *
 * [16] 最接近的三数之和
 */

#include <stdio.h>
#include <vector>
#include <algorithm>
using namespace std;
// @lc code=start
class Solution {
public:
    int threeSumClosest(vector<int>& nums, int target) {
        int n = nums.size();
        sort(nums.begin(), nums.end());
        int min_diff = INT_MAX;
        int ans;
        for (int i=0;i<n-2;i++) {
            int x = nums[i];
            // 优化1
            if (i>0 && nums[i] == nums[i-1]) continue;
            // 优化2
            // 当前i对应的最小的三数和
            int sum = x + nums[i+1] + nums[i+2]; 
            if (sum > target) {
                // 如果最小的三数和都大于target, 则后面的所有组合都会大于target
                // 更新数字并return
                if (sum - target < min_diff) {
                    ans = sum;
                }
                break;
            }

            // 优化3
            // 当前i对应的最大的三数和
            sum = x + nums[n-1] + nums[n-2];
            if (sum < target) {
                // 如果最大的三数和都小于target, 则当前sum就是这组数字能给出的最接近的数
                // 到下一组
                if (target - sum < min_diff) {
                    min_diff = target-sum;
                    ans = sum;
                }
                continue;
            }

            int l = i+1;
            int r = n-1;
            while (r > l) {
                sum = x + nums[l] + nums[r];
                if (sum == target) return target;
                if (sum > target) {
                    if (min_diff > sum - target) {
                        min_diff = sum - target;
                        ans = sum;
                    }
                    r--;
                }
                if (sum < target) {
                    if (target - sum < min_diff) {
                        min_diff = target - sum;
                        ans = sum;
                    }
                    l++;
                }
            }
        }

        return ans;
    }
};
// @lc code=end

