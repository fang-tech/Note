# P20.[有效的括号](https://leetcode.cn/problems/valid-parentheses/description/)

## 一句话题解 : 

- 如果是左符号, 直接压入栈, 如果是右符号, 则首先判断栈中有无元素, 如果没有, 直接`return false`, 接下来判断栈顶元素是不是其对应的左符号, 如果是直接`pop()`, 如果不是则直接`return false`, 所有元素遍历完以后, 判断此时栈中还有没有剩余, 如果有`return false`
- 加快速度 : 如果元素数量不是偶数, 必定无法匹配完全, 直接`return false`

## code

```cpp
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
```

- 