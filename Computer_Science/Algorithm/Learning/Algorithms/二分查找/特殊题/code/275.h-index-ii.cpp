/*
 * @lc app=leetcode.cn id=275 lang=cpp
 *
 * [275] H 指数 II
 */
#include <vector>
#include <iostream>

using namespace std;
// @lc code=start
class Solution {
public:
    int hIndex(vector<int>& citations) {
        int n = citations.size();
        int left = -1;
        int right = n;
        while (left < right - 1) {
            int mid = left + (right - left) / 2;
            if (citations[mid] < n - mid) {
                left = mid;
            }else {
                right = mid;
            }
        }
        return n - right;
    }
};
// @lc code=end
int main() {
    Solution s;
    vector<int> nums = {0,1,3,5,6};
    cout << s.hIndex(nums);
    return 0;
}

