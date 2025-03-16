/*
 * @lc app=leetcode.cn id=213 lang=cpp
 *
 * [213] 打家劫舍 II
 */

#include <vector>
#include <iostream>
using namespace std;
// @lc code=start
class Solution {
public:
    
    // [start, end)
    int rob1(vector<int> nums, int start, int end) {
        int f1 = 0, f0 = 0;
        for (int i = start; i < end; i++) {
            int f_new = max(f1, f0 + nums[i]);
            f0 = f1;
            f1 = f_new;
        }
        return f1;
    }

    int rob(vector<int>& nums) {
        // choose the first house
        // can not choose the second house and the last house

        // not choose the first house
        int n = nums.size();
        return max(rob1(nums, 1, n), rob1(nums, 2, n - 1) + nums.front());
    }
};
// @lc code=end
int main() {
    Solution s;
    vector<int> nums = {2,1,1,2};
    cout << s.rob(nums) << endl;
    return 0;
}
