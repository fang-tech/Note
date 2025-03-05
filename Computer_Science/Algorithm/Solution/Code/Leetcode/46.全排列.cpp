/*
 * @lc app=leetcode.cn id=46 lang=cpp
 *
 * [46] 全排列
 */

#include <cstring>
#include <vector>
#include <unordered_set>
using namespace std;
// @lc code=start
class Solution {
public:
    // 判断这个数字在不在路径中
    vector<int> path;
    vector<bool> on_path;
    vector<vector<int>> ans;

    void dfs(vector<int>& nums, int i) {
        int n = nums.size();
        if (i == n) {
            ans.emplace_back(path);
            return;
        }

        for (int j = 0; j < n; j++) {
            if (!on_path[j]) {
                on_path[j] = true;
                path[i] = nums[j];
                dfs(nums, i + 1);
                on_path[j] = false;
            }
        } 
    }


    vector<vector<int>> permute(vector<int>& nums) {
        path.resize(nums.size());
        on_path.resize(nums.size());
        dfs(nums, 0);
        return ans;
    }
};
// @lc code=end

