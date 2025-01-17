/*
 * @lc app=leetcode.cn id=2962 lang=cpp
 *
 * [2962] 统计最大元素出现至少 K 次的子数组
 */

#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    long long countSubarrays(vector<int>& nums, int k) {
        int max = *max_element(nums.begin(), nums.end());
        long long cnt = 0; //  最大的数出现的次数
        long long ans = 0;
        int left = 0;
        for (int right = 0; right < nums.size(); right++){
            if (nums[right] == max) {
                cnt++;
            }
            if (cnt == k) {
                while (nums[left] != max) {
                    left++;
                }
                left++;
                cnt--;
            }
            ans += left;
        }
        return ans;
    }
};
// @lc code=end

