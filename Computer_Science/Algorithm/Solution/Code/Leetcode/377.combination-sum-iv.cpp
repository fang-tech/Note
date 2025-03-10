/*
 * @lc app=leetcode.cn id=377 lang=cpp
 *
 * [377] Combination Sum IV
 */

#include <vector>
#include <iostream>
using namespace std;
// @lc code=start
class Solution {
public:

    vector<unsigned> f;

    // f[i] 的定义 : 走到第i个台阶的方案总数
    int combinationSum4(vector<int>& nums, int target) {
        f.resize(target + 1, 0);
        f[0] = 1;
        for (int i = 1; i <= target; i++) {
            for (int num : nums) {
                if (num <= i) {
                    f[i] += f[i - num];
                }
            }
        }
        return f[target];
    }
};
// @lc code=end
int main() {
    cout << (int) '9';
    return 0;
}