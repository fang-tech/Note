/*
 * @lc app=leetcode.cn id=2958 lang=cpp
 *
 * [2958] 最多 K 个重复元素的最长子数组
 */

#include <unordered_map>
#include <vector>
#include <iostream>
using namespace std;
// @lc code=start
class Solution {
public:
    int maxSubarrayLength(vector<int>& nums, int k) {
        unordered_map<int, int> hash;
        int ans = 0;
        int n = nums.size();
        int left = 0;
        for (int right = 0; right < n; right++) {
            int num = nums[right];
            while (hash[num] > k-1) {
                hash[nums[left++]]--;
            }
            hash[num]++;
            ans = max(ans, right - left + 1);
        }
        return ans;
    }
};
// @lc code=end

