/*
 * @lc app=leetcode.cn id=1901 lang=cpp
 *
 * [1901] 寻找峰值 II
 */
#include <vector>
#include <algorithm>

using namespace std;

// @lc code=start
class Solution {
public:
    int indexOfMax(vector<int>& line) {
        return max_element(line.begin(), line.end()) - line.begin();
    }

    vector<int> findPeakGrid(vector<vector<int>>& mat) {
        // 峰值存在于这行以下
        int left = - 1;
        // 峰值存在于这行及以上 
        int right = mat.size() - 1;
        while (left < right - 1) {
            int mid = left + (right - left) / 2;
            int i = indexOfMax(mat[mid]);
            if (mat[mid][i] < mat[mid + 1][i]) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return {right, indexOfMax(mat[right])};
    }
};
// @lc code=end

