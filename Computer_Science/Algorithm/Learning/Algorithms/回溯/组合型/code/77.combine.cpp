/*
 * @lc app=leetcode.cn id=77 lang=cpp
 *
 * [77] 组合
 */

#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    vector<int> path;
    vector<vector<int>> ans;
    // 从输入的角度, 每个数字我们选不选
    void dfs(int i, int n, int k){
        // 减枝优化, 如果接下来需要选的数字数量 > 还剩下的数字数量, 直接返回
        if (k > n - i + 1) return;

        if (k == 0) {
            ans.emplace_back(path);
            return;
        }

        // 选
        path.push_back(i);
        dfs(i+1, n, k-1);
        path.pop_back();
        // 不选
        dfs(i+1, n, k);

    }

    vector<vector<int>> combine(int n, int k) {
        dfs(1, n, k);
        return ans;
    }
};
// @lc code=end
int main() {
    Solution s;
    s.combine(4,2);
    return 0;
}

