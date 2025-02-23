/*
 * @lc app=leetcode.cn id=875 lang=cpp
 *
 * [875] 爱吃香蕉的珂珂
 */

#include <vector>
#include <algorithm>
#include <iostream>

using namespace std;
// @lc code=start
class Solution {
public:
    bool check(vector<int>& piles, int h, int speed) {
        int sum = 0;
        for (auto p : piles) {
            sum += (p - 1) / speed + 1;
            if (sum > h) 
                return false;
        }
        return true;
    }

    int minEatingSpeed(vector<int>& piles, int h) {
        // 循环不变量, 恒为true
        int right = *max_element(piles.begin(), piles.end()); 
        // 循环不变量, 恒为false
        int left = 0;
        while (left < right - 1) {
            int mid = left + (right - left)  / 2;
            // 这个速度能吃完, 更新最大速度
            if (check(piles, h, mid)) {
                right = mid;
            } else { // 这个速度吃不完, 更新最小速度
                left = mid;
            }
        }
        return right;
    }
};
// @lc code=end
int main() {
    Solution s;
    vector<int> piles({3,6,7,11});
    int h = 8;
    cout << s.minEatingSpeed(piles, h);
    return 0;
}

