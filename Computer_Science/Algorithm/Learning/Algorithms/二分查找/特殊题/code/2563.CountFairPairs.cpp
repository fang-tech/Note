/*
 * @lc app=leetcode.cn id=2563 lang=cpp
 *
 * [2563] 统计公平数对的数目
 */
#include <vector>
#include <algorithm>

using namespace std;


// @lc code=start
class Solution {
public:
    long long countFairPairs(vector<int>& nums, int lower, int upper) {
        sort(nums.begin(), nums.end());
        long long ans = 0;
        
        for (int i = 0; i < nums.size(); i++) {
            auto l = lower_bound(nums.begin() + i + 1, nums.end(), lower - nums[i]);
            auto r = upper_bound(nums.begin() + i + 1, nums.end(), upper - nums[i]);
            ans += r - l;
        }

        return ans;
    }
};
// @lc code=end
int main() {
    Solution s;
    vector<int> nums = {0,1,7,4,4,5};
    int lower = 3;
    int upper = 4;
    s.countFairPairs(nums, lower, upper);
    return 0;
}

