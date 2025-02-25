/*
 * @lc app=leetcode.cn id=2517 lang=cpp
 *
 * [2517] 礼盒的最大甜蜜度
 */
#include <vector>
#include <algorithm>
#include <iostream>

using namespace std;
// @lc code=start
class Solution {
public:

    // 甜蜜度至少为d是不是可能的
    bool check(vector<int>& price, int k, int d) {
        int cnt = 1;
        int pre = price[0];
        for (int p : price) {
            if (p - pre >= d) {
                pre = p;
                cnt++;
            }
        }
        return cnt >= k;
    }

    int maximumTastiness(vector<int>& price, int k) {
        sort(price.begin(), price.end());
        // 甜蜜度越高是不可能的, 所以left是蓝色, true
        int left = 0;
        // false
        int right = (price.back() - price[0]) / (k - 1)+ 1;
        while (left < right - 1) {
            int mid = left + (right - left) / 2;
            (check(price, k, mid) ? left : right) = mid;
        }
        return left;
    }
};
// @lc code=end

