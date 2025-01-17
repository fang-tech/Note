/*
 * @lc app=leetcode.cn id=239 lang=cpp
 *
 * [239] 滑动窗口最大值
 */
#include <vector>
#include <deque>
#include <iostream>
using namespace std;

// @lc code=start
class Solution {
public:
    vector<int> maxSlidingWindow(vector<int>& nums, int k) {
        vector<int> ret;
        deque<int> dq;
        for (int i = 0; i < nums.size(); i++) {
            // 删尾
            while (!dq.empty() && nums[i] > nums[dq.back()]) dq.pop_back();
            dq.push_back(i); 
            if (i >= k-1) {
                // 删除头部不属于目前窗口的内容
                while (!dq.empty() && dq.front() <= i-k) dq.pop_front();
                ret.push_back(nums[dq.front()]);
            }
        }
        return ret;
    }
};
// @lc code=end