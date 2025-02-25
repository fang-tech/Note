/*
 * @lc app=leetcode.cn id=2698 lang=cpp
 *
 * [2698] 求一个整数的惩罚数
 */

#include <string>
using namespace std;
// @lc code=start
class Solution {
public:
    int ans = 0;

    int str2int(string&s, int left, int right) {
        int num = 0; 
        for (int i = left; i < right; i++) {
            num = num * 10 + (s[i] - '0');
        }
        return num;
    }

    int sum = 0; // 相当于path
    // 判断这个字符串能不能分隔成若干连续的子字符串, 且每个字符的和等于n
    void dfs(string& s, int i, int target) {
        int n = s.length();
        if (i == n) {
            true;
            return ;
        }

        // 从答案的角度 : 枚举字符串结束的位置
        for (int j = i; j < n; j++) {

            if (sum + )
        }

    }

    int punishmentNumber(int n) {
        
    }
};
// @lc code=end

