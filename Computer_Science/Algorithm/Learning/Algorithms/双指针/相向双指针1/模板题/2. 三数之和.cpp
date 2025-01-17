/*
 * @lc app=leetcode.cn id=15 lang=cpp
 *
 * [15] 三数之和
 */

#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    vector<vector<int>> threeSum(vector<int>& nums) {
        sort(nums.begin(), nums.end());
        vector<vector<int>> ans;
        int n = nums.size();
        for (int i=0; i < n-2; i++) {
            // 去重
            if (i >0 && nums[i] == nums[i-1]) continue;
            if (nums[i] + nums[i+1] + nums[i+2] > 0) break;
            if (nums[i] + nums[n-1] + nums[n-2] < 0) continue;
            // 将问题降维成了两数之和问题
            int left = i+1;
            int right = n - 1;
            while (left < right) {
                int s = nums[left] + nums[right] + nums[i];
                if (s > 0) {
                    right--;
                }
                if (s < 0 ) {
                    left++;
                }
                if (s == 0) {
                    ans.push_back({nums[i], nums[left], nums[right]});
                    // 去重left
                    for (left++; right>left && nums[left]==nums[left-1]; left++);
                    // 去掉重复的right
                    for (right--; right>left && nums[right]==nums[right+1]; right--);
                }
            }
        }
        return ans;
    }
};
// @lc code=end
int main(){
    vector<int> nums = {-2,0,1,1,2};
    Solution s;
    vector<vector<int>> ans =  s.threeSum(nums);
    return 0;
}

