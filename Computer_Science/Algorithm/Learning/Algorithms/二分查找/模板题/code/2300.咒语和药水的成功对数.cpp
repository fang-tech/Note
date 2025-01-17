/*
 * @lc app=leetcode.cn id=2300 lang=cpp
 *
 * [2300] 咒语和药水的成功对数
 */

#include <algorithm>
#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:

    int lower_bound(vector<int>& nums, long target) {
        int left = 0, right = nums.size() - 1;
        while (right >= left) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target){
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    vector<int> successfulPairs(vector<int>& spells, vector<int>& potions, long long success) {
        int n = spells.size();
        vector<int> ans(n);
        sort(potions.begin(), potions.end());
        for (int i = 0; i < n; i++) {
            long temp = success / spells[i];
            long target = success % spells[i] ? temp + 1 : temp;
            ans[i] = potions.size() -  lower_bound(potions, target);
        }
        return ans;
    }
};
// @lc code=end

