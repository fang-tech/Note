/*
 * @lc app=leetcode.cn id=20 lang=cpp
 *
 * [20] 有效的括号
 */
#include <iostream>
#include <stack>
#include <string>
using namespace std;

// @lc code=start
class Solution {
public:
    bool isValid(string s) {
        if (s.length() % 2) return false;
        stack<char> st1;
        for (int i = 0; i < s.length(); i++) {
            char ch = s[i];
            if (ch == '(' || ch == '{' || ch == '[') 
                st1.push(ch);
            else {
                // 需要匹配的情况, 
                // 这里如果遍历栈的时候, 右括号一个都没有匹配到左括号
                // 说明已经出错, 匹配到了则双双弹出
                if (st1.empty() || ((ch == ')' && st1.top() != '(') || (ch == ']' && st1.top() != '[') || (ch == '}' && st1.top() != '{')))
                    return false;
                st1.pop();
            }
        }
        if (!st1.empty()) return false;
        return true;
    }
};
// @lc code=end
int main() {
    Solution s;
    cout << "case : 1" << endl;
    string str = "()";
    cout << s.isValid(str) << endl;
    cout << "case : 2" << endl;
    str = "()[]{}";
    cout << s.isValid(str) << endl; 
    cout << "case : 3" << endl;
    str = "()[]{{}";
    cout << s.isValid(str) << endl; 
    cout << "case : 4" << endl;
    str = "()[]{}}";
    cout << s.isValid(str) << endl; 
    cout << "case : 5" << endl;
    str = "()[{(})]{}";
    cout << s.isValid(str) << endl; 
    return 0;
}
