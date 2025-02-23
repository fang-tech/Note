/*
 * @lc app=leetcode.cn id=1234 lang=cpp
 *
 * [1234] 替换子串得到平衡字符串
 */
#include <iostream>
#include <algorithm>
#include <string>
#include <unordered_map>
#include <assert.h>
using namespace std;
// @lc code=start

class Solution {
public:
    // 判断现在窗口外面的元素是不是全都小于等于n/4
    // 也就是能通过替换窗口中的字符串来得到平衡字符串的时机
    bool balance(unordered_map<char, int>& map, int target) {
        return (map['Q'] <= target && map['W'] <= target 
        && map['E'] <= target &&map['R'] <= target);
    }
    
    int balancedString(string s) {
        unordered_map<char, int> num = {
            {'Q', 0},
            {'W', 0},
            {'E', 0},
            {'R', 0}
        };
        int n = s.length();
        int target = n/4;
        // 统计每个字母出现的次数
        for (int _ = 0; _ < n; _++){
            num[s[_]]++;
        }

        // 优化一, 窗口为0的时候, 判断是不是平衡字符串
        if (balance(num, target)) return 0;

        int ans = n;
        int left = 0;
        for (int right=0; right < n; right++) {
            num[s[right]]--;
            // 如果能实现平衡就将left右移, 并重新计算ans
            while (balance(num, target)) {
                // 计算现在能让字符串平衡的窗口字串的大小, 并比较和ans哪个更小
                ans = min(ans, right - left + 1);
                // 将left右移
                num[s[left++]]++;
            }
            // 这个时候窗口中为空, 对应着找到了只用改变一个字符就能平衡的方案
            // 可以直接返回
            // if (left > right) return 1;
        }
        return ans;
    }
};
// @lc code=end
