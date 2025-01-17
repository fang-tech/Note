/*
 * @lc app=leetcode.cn id=611 lang=cpp
 *
 * [611] 有效三角形的个数
 */

#include <algorithm>
#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    int triangleNumber(vector<int>& nums) {
        int n = nums.size();
        sort(nums.begin(), nums.end());
        int ans = 0;
        for (int i = 2; i < n; i++) {
            int x = nums[i];
            int j = 0;
            int k = i - 1;
            while (j < k) {
                if (nums[k] + nums[j] > nums[i]) {
                    ans += k - j;
                    k--;
                } else {
                    j++;
                }
            }
        }

        return ans;
    }
};
// @lc code=end

