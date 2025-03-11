/*
 * @lc app=leetcode.cn id=2266 lang=cpp
 *
 * [2266] 统计打字方案数
 */

#include <string>
#include <iostream>
using namespace std;
// @lc code=start
const int MAX = 1e5 + 1;
const int MOD = 1e9 + 7;

long long f[MAX];
long long g[MAX];

// int init = []() {
//         // 初始化f和g
//         f[0] = g[0] = 1;
//         f[1] = g[1] = 1;
//         f[2] = g[2] = 2;
//         f[3] = g[3] = 4;
//         // dp计算出来爬楼梯的方案数
//         for (int i = 4; i < MAX; i++) {
//             f[i] = (f[i - 1] + f[i -2] + f[i - 3]) % MOD;
//             g[i] = (g[i - 1] + g[i -2] + g[i - 3] + g[i - 4]) % MOD;
//         }
//         return 0;
// }();

class Solution {
public:
    
    Solution() {
        f[0] = g[0] = 1;
        f[1] = g[1] = 1;
        f[2] = g[2] = 2;
        f[3] = g[3] = 4;
        // dp计算出来爬楼梯的方案数
        for (int i = 4; i < MAX; i++) {
            f[i] = (f[i - 1] + f[i -2] + f[i - 3]) % MOD;
            g[i] = (g[i - 1] + g[i -2] + g[i - 3] + g[i - 4]) % MOD;
        }
    }
    
    int countTexts(string pressedKeys) {
        long long ans = 1;
        int n = pressedKeys.size();
        int cnt = 0;
        // 默认是连续的内容
        for (int i = 0; i < n; i++){
            cnt++;
            // 如果是最后元素, 就等不到不相等的后面的情况了
            if (i == n - 1 || pressedKeys[i] != pressedKeys[i+1]) {
                ans = ans * ((pressedKeys[i] == '7' || pressedKeys[i] == '9') ? g[cnt] : f[cnt]) % MOD;
                cnt = 0;
            }
        }
        return ans;
    }
};
// @lc code=end
int main() {
    Solution s;
    string str = "22233";
    cout << s.countTexts(str);
    return 0;
}
