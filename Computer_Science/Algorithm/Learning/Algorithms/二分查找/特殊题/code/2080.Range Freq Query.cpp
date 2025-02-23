/*
 * @lc app=leetcode.cn id=2080 lang=cpp
 *
 * [2080] 区间内查询数字的频率
 */

#include <vector>
#include <algorithm>
#include <unordered_map>
#include <iostream>

using namespace std;
// @lc code=start
class RangeFreqQuery {
public:
    unordered_map<int, vector<int>> pos; 

    RangeFreqQuery(vector<int>& arr) {
        for (int i = 0; i < arr.size(); i++) {
            pos[arr[i]].push_back(i);
        }
    }
    
    int query(int left, int right, int value) {
        auto it = pos.find(value);
        if (it == pos.end()) {
            return 0;
        }
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
int main() {
    vector<int> nums = {12,33,4,56,22,2,34,33,22,12,34,56};
    RangeFreqQuery s(nums);

    cout << s.query(1,2,4);
    return 0;
}
