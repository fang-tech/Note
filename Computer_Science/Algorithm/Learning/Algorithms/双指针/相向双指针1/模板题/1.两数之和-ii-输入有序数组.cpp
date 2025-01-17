/*
 * @lc app=leetcode.cn id=167 lang=cpp
 *
 * [167] 两数之和 II - 输入有序数组
 */

#include <iostream>
#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    vector<int> twoSum(vector<int>& numbers, int target) {
        int left = 0;
        int right = numbers.size() - 1;
        while (true) {
            cout << numbers[left] << " " << numbers[right] << endl;
            if (numbers[left] + numbers[right] == target) {
                return {left+1, right+1};
            }
            if (numbers[left] + numbers[right] > target) {
                right--;
            }
            if (numbers[left] + numbers[right] < target) {
                left++;
            }
        }
    }
};
// @lc code=end

