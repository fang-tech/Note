/*
 * @lc app=leetcode.cn id=216 lang=cpp
 *
 * [216] 组合总和 III
 */

#include <system_error>
#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    vector<vector<int>> ans;
    vector<int> path;

    /**
     * 从答案的角度
     * i : 现在在可选数组中的位置, 由于是逆序, 从大到小, 这个时候i还是数组中还有多少个元素
     * target : n, 也就是我们需要加到的值
     * k : 还剩下几个数需要加
     */
    void dfs(int i, int k, int target) {
        
        // 剪枝 1: 如果现在target < 0 return
        if (target < 0) return;
        // 剪枝 2: 如果现在的target > 能从接下的数字中能获取到的最大的组合, return, 
        if (target > (2 * i - k + 1) * k / 2) return;

        // 由上面的两个剪枝, 保证了target >= 0, 而k = 0的时候, 
        // 保证了target <= 0, 即target == 0, 所以不需要额外的判断
        if (0 == k) {
            ans.emplace_back(path);
            return;
        }
        
        // 选 
        path.push_back(i);
        dfs(i-1, k-1, target - i);
        path.pop_back();

        // 剪枝 1: 如果当前现在可选的数字数量 < 需要选的数字数量 return
        // 不选, 这个剪枝在这里体现就是, 如果剩余的数字个数小于等于需要的数字数量, 就不能不选
        if (i > k)
            dfs(i - 1, k, target);
    }

    vector<vector<int>> combinationSum3(int k, int n) {
        dfs(9, k, n);
        return ans;
    }
};
// @lc code=end

