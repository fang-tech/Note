/*
 * @lc app=leetcode.cn id=2861 lang=cpp
 *
 * [2861] 最大合金数
 */

#include <vector>
#include <algorithm>
#include <iostream>

using namespace std;
// @lc code=start
class Solution {
public:
    // 检查目前这个数量的合金使用这台机器能不能制造出来
    // 如果需要生产的合金数量是确定的, 很容易就能确定预算够不够
    bool check(long long target, vector<int>& machine, vector<int>& stock, vector<int>& cost, int budget) {
        long long money = 0;
        for (int i = 0; i < machine.size(); i++) {
            long long num = machine[i];
            num *= target;
            if (stock[i] < num) {
                money += (num - stock[i]) * cost[i];
                if (money > budget) return false;
            }
        }
        return true;
    }

    int maxNumberOfAlloys(int n, int k, int budget, vector<vector<int>>& composition, vector<int>& stock, vector<int>& cost) {
        int ans = 0;
        int mx = *min_element(stock.begin(), stock.end()) + budget + 1;
        for (int i = 0; i < k; i++) {
            // 蓝色, 能造出来的数量, true
            int left = ans;
            // 红色, 造不出来的数量, false
            // 初始化为不可能造出来的最大数量, 即这个合金只需要库存中最大的数量的金属并且这种金属的cost为1的时候的数量再+1
            long long right = mx;

            // 二分查找能制造出来的最大数量
            while (left < right - 1) {
                long long mid = left + (right - left) / 2;
                if (check(mid, composition[i], stock, cost, budget)) {
                    left = mid;
                } else {
                    right = mid;
                }
            }

            ans = left;
        }

        return ans;
    }
};
// @lc code=end
int main() {
    Solution s;
    vector<vector<int>> composition = {{1}};
    vector<int> stock = {6};
    vector<int> cost = {1};
    cout << s.maxNumberOfAlloys(1, 3, 48, composition, stock, cost);
    return 0;
}

