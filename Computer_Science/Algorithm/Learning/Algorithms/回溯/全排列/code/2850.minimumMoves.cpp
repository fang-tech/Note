/*
 * @lc app=leetcode.cn id=2850 lang=cpp
 *
 * [2850] 将石头分散到网格图的最少移动次数
 */

#include <algorithm>
#include <climits>
#include <utility>
#include <vector>
#include <iostream>
using namespace std;
// @lc code=start
class Solution {
public:
    
    int minimumMoves(vector<vector<int>>& grid) {
        vector<pair<int, int>> from, to;
        int ans = INT_MAX;
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid[0].size(); j++) {
                if (grid[i][j] > 1) {
                    while (grid[i][j] > 1)
                    {
                        from.emplace_back(i, j);
                        grid[i][j] -= 1;
                    }
                }
                else if (grid[i][j] == 0) {
                    to.emplace_back(i,j);
                }
            }
        }

        do
        {
            int sum = 0;
            for (int i = 0; i < from.size(); i++) {
                sum += abs(from[i].first - to[i].first) + abs(from[i].second - to[i].second);
            }
            ans = min(ans, sum);
        } while (next_permutation(from.begin(), from.end()));
        return ans;
    }
};
// @lc code=end
int main() {
    vector<vector<int>> grid = {
        {3,2,0},{0,1,0},{0,3,0}
    };
    Solution s;
    cout << s.minimumMoves(grid);
    return 0;
}

