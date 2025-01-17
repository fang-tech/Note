#### P150. [逆波兰表达式求值](https://leetcode.cn/problems/evaluate-reverse-polish-notation/description/)

- 一句话题解 : 

  - 如果是数字, 直接`push()`入栈, 如果是符号, 则取出栈顶的两个元素做运算, 并将结果再存入栈中

- 特性 :

  - 后缀表达式本身的设计就是为了能方便计算机线性的运算非线性的非线性运算的中缀表达式, 利用栈解决更像是模拟题
  - 需要特别注意的是, **中间结果的类型 : `long long`**, 这个是需要特别注意的点, 因为中间结果可能会超出int 

- code

  ```cpp
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
  ```

## 

