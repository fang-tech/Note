/*
 * @lc app=leetcode.cn id=73 lang=cpp
 *
 * [73] 矩阵置零
 */

#include <vector>
#include <iostream>
#include <cstring>
using namespace std;
// @lc code=start
class Solution {
public:
    
    bool on_path[205][205]{};

    void setZeroes(vector<vector<int>>& matrix) {
        
        for (int x = 0; x < matrix.size(); x++) {
            for (int y = 0; y < matrix[0].size(); y++) {
                if (on_path[x][y]) continue;
                on_path[x][y] = true;
                if (matrix[x][y]) continue;
                
                int m = matrix.size(), n = matrix[0].size();
                // make the row = 0
                for (int i = 0; i < n; i++) {
                    if (matrix[x][i]) {
                        matrix[x][i] = 0;
                        on_path[x][i] = true;
                    }
                }

                // make the col = 0
                for (int j = 0; j < m; j++) {
                    if (matrix[j][y]) {
                        matrix[j][y] = 0;
                        on_path[j][y] = true;
                    }
                }
            }
        }
    }
};
// @lc code=end
int main() {
    Solution s;
    vector<vector<int>> martix = {{0,1,2,0},{3,4,5,2},{1,3,1,5}};
    s.setZeroes(martix);
    for (auto& nums : martix) {
        for (int num : nums) {
            cout << num << " ";
        }
        cout << endl;
    }

    return 0;
}
