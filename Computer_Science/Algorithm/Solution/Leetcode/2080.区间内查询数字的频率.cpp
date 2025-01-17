/*
 * @lc app=leetcode.cn id=2080 lang=cpp
 *
 * [2080] 区间内查询数字的频率
 */

#include <iostream>
#include <vector>
#include <unordered_map>
#include <algorithm>
using namespace std;
// @lc code=start
class RangeFreqQuery {
public:
    unordered_map<int, vector<int>> map;
    RangeFreqQuery(vector<int>& arr) {
        for (int i = 0; i < arr.size(); i++) {
            map[arr[i]].push_back(i);
        }
    }
    
    int query(int left, int right, int value) {
        auto it = map.find(value);
        if (it == map.end()) return 0;
        auto& a = it->second;
        return upper_bound(a.begin(), a.end(), right) - lower_bound(a.begin(), a.end(), left);
    }
};

/**
 * Your RangeFreqQuery object will be instantiated and called as such:
 * RangeFreqQuery* obj = new RangeFreqQuery(arr);
 * int param_1 = obj->query(left,right,value);
 */
// @lc code=end

