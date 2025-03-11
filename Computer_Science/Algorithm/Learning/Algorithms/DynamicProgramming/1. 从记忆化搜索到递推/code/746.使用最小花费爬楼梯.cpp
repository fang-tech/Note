/*
 * @lc app=leetcode.cn id=746 lang=cpp
 *
 * [746] 使用最小花费爬楼梯
 */

#include <algorithm>
#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    vector<int> memo;
    
    // dfs(i) : 我们从起点爬到第i个台阶的最小费用
    int dfs(vector<int>& cost, int n){
        if (n == 0) return 0;
        if (n < 0) return 0;

        int& res = memo[n];
        if (res != -1) return res;
        res = min(dfs(cost, n - 1), dfs(cost, n -2)) + cost[n-1];
        return res;
    } 

    int minCostClimbingStairs(vector<int>& cost) {
        memo.resize(cost.size() + 1, -1);
        return min(dfs(cost, cost.size()), dfs(cost, cost.size() - 1));
    }
};
// @lc code=end

