/*
 * @lc app=leetcode.cn id=1004 lang=cpp
 *
 * [1004] 最大连续1的个数 III
 */

#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    int longestOnes(vector<int>& nums, int k) {
        int ans = 0;
        int left = 0;
        int used = 0;
        for (int right = 0; right < nums.size(); right++) {
            if (nums[right] == 0 && used >= k){
                while (used >= k) {
                    if (nums[left] == 0) used--;
                    left++;
                }
            }
            if (nums[right] == 0 && used < k) {
                ans = max(ans, right - left + 1);
                used++;
            } 
            if (nums[right] == 1) ans = max(ans, right - left + 1);
        }
        return ans;
    }
};
// @lc code=end

