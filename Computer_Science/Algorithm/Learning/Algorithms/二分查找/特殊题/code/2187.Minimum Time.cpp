/*
 * @lc app=leetcode.cn id=2187 lang=cpp
 *
 * [2187] 完成旅途的最少时间
 */
#include <vector>
#include <algorithm>
#include <iostream>

using namespace std;
// @lc code=start
class Solution {
public:
    bool check(vector<int>& time, int totalTrips, long long minTime) {
        long long sum = 0;
        for (int t : time) {
            sum += minTime / t;
            if (sum >= totalTrips) 
                return true;
        }
        return false;
    }

    long long minimumTime(vector<int>& time, int totalTrips) {
        // 跑不完的时候, 红色
        long long left = 0;
        // 最多需要的时间 ; 只有一辆巴士, 并且是速度最慢的巴士运行的时候
        // 跑的完的时候, 蓝色
        long long right = *min_element(time.begin(), time.end());
        right *= totalTrips;
        // 开区间
        while (left < right - 1) {
            long long mid = left + (right - left) / 2;
            // 如果这个时间跑的完
            if (check(time, totalTrips, mid))
                right = mid;
            else 
                left = mid;
        }
        return right;
    }
};
// @lc code=end
int main() {
    Solution s;
    vector<int> time = {10000};
    int totalTrips = 10000000;
    cout << s.minimumTime(time, totalTrips);
    return 0;
}

