/*
 * @lc app=leetcode.cn id=150 lang=cpp
 *
 * [150] 逆波兰表达式求值
 */
#include <stack>
#include <iostream>
#include <string>
#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    int evalRPN(vector<string>& tokens) {
        // 循环压入栈中, 数字则直接压入栈中
        // 如果是运算符, 取出栈顶的两个元素进行运算, 并将运算结果存回栈
        // 没有左右括号
        stack<string> st;
        for (string c: tokens) {
            if (c == "+" || c == "-" || c == "*" || c == "/") {
                long long num2 = stoi(st.top());
                st.pop();
                long long num1 = stoi(st.top());
                st.pop();
                long long num3 = 0;
                if (c == "+") 
                    num3 = num1 + num2;
                else if (c == "-")
                    num3 = num1 - num2;
                else if (c == "*")
                    num3 = num1 * num2;
                else if (c == "/")
                    num3 = num1 / num2;
                st.push(to_string(num3));
            }
            else
                st.push(c);
        }
        return stoi(st.top());
    }
};
// @lc code=end
int main() {
    Solution s;
    cout << "case 1 :" <<endl;
    vector<string> tokens = {"2","1","+","3","*"};
    cout << s.evalRPN(tokens) << endl;

    cout << "case 2 :" <<endl;
    tokens = {"4","13","5","/","+"};
    cout << s.evalRPN(tokens) << endl;

    cout << "case 3 :" <<endl;
    tokens = {"10","6","9","3","+","-11","*","/","*","17","+","5","+"};
    cout << s.evalRPN(tokens) << endl;
    
    return 0;
}
