/*
 * @lc app=leetcode.cn id=2454 lang=cpp
 *
 * [2454] 下一个更大元素 IV
 */
#include <iostream>
#include <vector>
#include <stack>
#include <utility>
using namespace std;
// @lc code=start
class Solution {
public:
    vector<int> secondGreaterElement(vector<int>& nums) {
        vector<int> ret(nums.size(), -1);
        stack<pair<int,int>> st1, st2; // 第一个元素存储对应元素的索引, 第二个元素存储该元素出栈的次数
        // 遍历入栈
        for (int i = 0; i < nums.size(); i++) {
            // 入栈的时候和栈顶元素进行比较, 找到自己的位置, 然后push_back
            // 栈顶元素是最小元素
            pair<int, int> current(i, 0);
            while (!st1.empty() && nums[st1.top().first] < nums[current.first]) {
                // 同时对弹出的元素做判断, 如果第二个元素是0, 则存入st2, 同时second++, 
                if (st1.top().second == 0) {
                    st1.top().second++;
                    st2.push(st1.top());
                // 如果是1, 说明已经出栈一次了, 存入目标数组ret
                } else if (st1.top().second == 1) {
                    ret[st1.top().first] = nums[i];
                }
                st1.pop();
            }
            st1.push(current);
            // 将st2中的元素, 重新压入st1中, 将暂存的元素还给st1
            while (!st2.empty()) {
                st1.push(st2.top());
                st2.pop();
            }
        }
        // 最后清空栈 -> 不必要的操作, 因为默认值就是0
        return ret;
    }
};
// @lc code=end

// @test
int main() {
    Solution s;
    cout << "case 1 :" << endl;
    vector<int> nums = {2,4,0,9,6};
    cout << "ret : [";
    for (const auto& num : s.secondGreaterElement(nums)) {
        cout << num << ", ";
    } 
    cout << "]" << endl;

    cout << "边界条件的测试" <<endl;
    cout << "case 2 :" << endl;
    nums = {0};
    cout << "ret : [";
    for (const auto& num : s.secondGreaterElement(nums)) {
        cout << num << ", ";
    } 
    cout << "]" << endl;

    return 0;
}
