/*
 * @lc app=leetcode.cn id=39 lang=cpp
 *
 * [39] 组合总和
 */

#include <ratio>
#include<vector>
#include<algorithm>
using namespace std;
// @lc code=start
class Solution {
public:
    vector<vector<int>> ans;
    vector<int> path;
    
    void dfs(vector<int>& candidates, int i, int target) {
        int n = candidates.size();
        if (i == n) return;
        if (target < 0) return;
        if (target == 0) {
            ans.emplace_back(path);
            return;
        }

        for (int j = i; j < n; j++) {
            path.push_back(candidates[j]);
            // 还是从j开始表明下次还可以选这个元素
            dfs(candidates, j, target - candidates[j]);
            path.pop_back();
        }
    }

    vector<vector<int>> combinationSum(vector<int>& candidates, int target) {
        dfs(candidates, 0, target);
        return ans;
    }
};
// @lc code=end

