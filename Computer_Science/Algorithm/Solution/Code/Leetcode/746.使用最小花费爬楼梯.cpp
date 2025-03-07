/*
 * @lc app=leetcode.cn id=746 lang=cpp
 *
 * [746] 使用最小花费爬楼梯
 */

#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    vector<int> f;
    int dfs(vector<int>& cost, int n){
        if (n <= 0) return 0;
        if (memo[n] != -1) return memo[n];
        
        memo[n] =  min(dfs(cost, n-1), dfs(cost, n-2)) + cost[n-1];
        return memo[n];
    } 

    int minCostClimbingStairs(vector<int>& cost) {
        int n = cost.size();
        f.resize(n+2, -1);
        
    }
};
// @lc code=end

