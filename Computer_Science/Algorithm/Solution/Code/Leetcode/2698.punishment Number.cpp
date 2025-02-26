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
    int ans = 0;

    bool found = false;
    int path = 0;
    // 判断这个数字是不是惩罚数
    bool dfs(string& s,int start, int i, int target) {
        int n = s.size();
        if (i == n)
            return path == target;
        for (int j = i; j < n; j++) {
            int num = stoi(s.substr(start, i - start + 1));
            path += num;
            return dfs(s, j, i,target);
            path -= num;
        }
    }

    int punishmentNumber(int n) {
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            string s = to_string(i*i);
            found = false;
            dfs(s, 0, 0, i);
            if (found) {
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

