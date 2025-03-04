/*
 * @lc app=leetcode.cn id=2698 lang=cpp
 *
 * [2698] 求一个整数的惩罚数
 */

#include <iostream>
#include <string>
using namespace std;
// @lc code=start
class Solution {
public:

    int sum = 0;
    bool isFound = false;
    // i 当前字符串在的位置
    void dfs(string& str, int i, int target) {
        int n = str.length();
        if (i == n) {
            if (sum == target) isFound = true;
            return;
        }
        // 从答案的视角
        // 这一步选择的字符串 str[i,,,j]
        for (int j = i; j < n; j++) {
            int num = stoi(str.substr(i, j - i + 1));
            sum += num;
            dfs(str, j + 1, target);
            sum -= num;
        }
    }

    int punishmentNumber(int n) {
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            string str = to_string(i*i);
            isFound = false;
            dfs(str, 0, i);
            if (isFound) {
                ans += i*i;
            }
        }
        return ans;
    }

};
// @lc code=end
int main() {
    Solution s;
    cout << s.punishmentNumber(37);
}

