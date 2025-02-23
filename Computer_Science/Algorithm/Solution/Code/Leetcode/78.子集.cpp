/*
 * @lc app=leetcode.cn id=78 lang=cpp
 *
 * [78] 子集
 */

#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    vector<int> path;
    vector<vector<int>> ans;
    // 从答案的角度, 枚举选哪个
    void dfs(vector<int>& nums, int i) {
        ans.emplace_back(path);
        for (int j = i; j < nums.size(); j++) {
            path.emplace_back(nums[j]);
            dfs(nums, j+1);
            path.pop_back(); // 恢复现场
        }
    }

    vector<vector<int>> subsets(vector<int>& nums) {
        dfs(nums, 0);
        return ans;
    }
};
// @lc code=end

