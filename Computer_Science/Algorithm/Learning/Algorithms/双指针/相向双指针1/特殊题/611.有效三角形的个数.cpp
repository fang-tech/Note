/*
 * @lc app=leetcode.cn id=611 lang=cpp
 *
 * [611] 有效三角形的个数
 */

#include <algorithm>
#include <vector>
#include <assert.h>
using namespace std;
// @lc code=start
class Solution {
public:
    int triangleNumber(vector<int>& nums) {
        int n = nums.size();
        int ans = 0;
        sort(nums.begin(), nums.end());
        for (int a = n-1; a >= 2; a--) {
            // a >= b >= c
            // 这个时候只需要满足 b + c > a
            int b = a - 1;
            int c = 0;
            while (b > c) {
                int s = nums[b] + nums[c];
                // 满足情况的时候
                if (s > nums[a]) {
                    ans += b - c;
                    b--;
                }
                else {
                    c++;
                }
            }
        }
        return ans;
    }
};
// @lc code=end

