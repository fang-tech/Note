/*
 * @lc app=leetcode.cn id=2563 lang=cpp
 *
 * [2563] 统计公平数对的数目
 */

#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:

    long long lower_bound(vector<int>& nums, int target, int start) {
        long left = start + 1;
        long right = nums.size() - 1;
        while (left <= right) {
            long long mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    long long countFairPairs(vector<int>& nums, int lower, int upper) {
        sort(nums.begin(), nums.end());
        int n = nums.size();
        long long ans = 0;
        for (int i = 0; i < n-1; i++) {
            // 找到第一个小于等于upper的数的索引
            int up = upper - nums[i];
            int end = lower_bound(nums, up + 1, i) - 1;
            if (nums[end] > up) continue;

            // 找到第一个大于等于lower的数的索引
            int low = lower - nums[i];
            int start = lower_bound(nums, low, i);
            //  没有找到的时候
            if (start == n || nums[start] < low) continue;

            // end和start都存在的时候
            ans += end - start + 1;
        }
        
        return ans;
    }
};
// @lc code=end

int main (){
    vector<int> nums = {-1,0};
    Solution s;
    cout << s.countFairPairs(nums, 1, 1);
    return 0;
}

