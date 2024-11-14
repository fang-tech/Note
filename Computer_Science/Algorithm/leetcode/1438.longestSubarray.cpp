/*
 * @lc app=leetcode.cn id=1438 lang=cpp
 *
 * [1438] 绝对差不超过限制的最长连续子数组
 */

#include <deque>
#include <vector>
#include <iostream>
using namespace std;
// @lc code=start
class Solution {
public:
    int longestSubarray(vector<int>& nums, int limit) {
        deque<int> max_dq, min_dq;
        int length = 0;
        int begin = 0;
        for (int i = 0; i < nums.size(); i++) {
            // max的去尾
            while (!max_dq.empty() && nums[i] < nums[max_dq.back()]) max_dq.pop_back();
            max_dq.push_back(i);
            // min的去尾
            while (!min_dq.empty() && nums[i] > nums[min_dq.back()]) min_dq.pop_back();
            min_dq.push_back(i);
            
            // cout << "begin : " << begin<< ", end : " << i;
            if(abs(nums[max_dq.front()]-nums[min_dq.front()]) > limit) {
                length = max(length, (i-begin));
                // cout << "  计算了length";
                while ((begin <= i) && abs(nums[max_dq.front()]-nums[min_dq.front()]) > limit) {
                    begin++;
                    while (max_dq.front() < begin && !max_dq.empty()) max_dq.pop_front();
                    while (min_dq.front() < begin && !min_dq.empty()) min_dq.pop_front();
                }
            }
            if (nums.size() - i == 1) {
                length = max(length, (i-begin+1));
            }
            // cout << ", length : " << length << endl;
        }
        return length;
    }
};
// @lc code=end
int main() {
    Solution s;
    cout << "case 1 : " << endl;
    vector<int> input = {10,1,2,4,7,2};
    int limit = 5;
    cout << s.longestSubarray(input, limit) <<endl;
    cout << "case 2 : " << endl;
    input = {8,2,4,7};
    limit = 4;
    cout << s.longestSubarray(input, limit);
    return 0;
}
