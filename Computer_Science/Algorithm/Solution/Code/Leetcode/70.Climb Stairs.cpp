/*
 * @lc app=leetcode.cn id=70 lang=cpp
 *
 * [70] 爬楼梯
 */

#include <iostream>
#include <stdio.h>
using namespace std;
// @lc code=start
const int N = 50;
class Solution {
private:
int dp[3];
public:
    int climbStairs(int n) {
        if (n==1 || n==2) return n;
        dp[0] = 1; dp[1] = 2;
        for (int i = 3; i <= n; i++) {
            dp[2] = dp[1] + dp[0];
            dp[0] = dp[1]; dp[1] = dp[2];
        }
        return dp[2];
    }
};
// @lc code=end
int main() {
    freopen("input.txt", "r", stdin);
    int n; cin>>n;
    Solution s;
    cout << s.climbStairs(n);
    return 0;
}

