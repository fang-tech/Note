#### P2454.[下一个更大元素 IV](https://leetcode.cn/problems/next-greater-element-iv/description/)

- 一句话题解 : 这题是常规的"下一个更大元素的拓展", 所以需要先说明原题的解法, 

  - 题目 : 找到每个元素的下一个更大的元素, 返回这个存有更大的元素的数组, 原题的思路是利用单调栈, 从0开始遍历题目数组, 栈维持着栈顶元素是最大的元素, 元素进栈的时候和栈顶元素比较, 如果栈顶元素小于当前元素, 这说明当前元素就是栈顶元素的下一个更大元素, 将栈顶元素弹出, 重复比较与弹出直至栈顶元素大于当前元素, 当前元素入栈, 重复这个过程.
  - 题目的变化 : 从下一个更大的元素, 变为对应元素的**第二大**的整数, 如果采用上面的思路, 我们只能获取第一大的整数, 同时不难发现, 这个时候每个元素需要被比较两次, 如果都被比较下去了, 第二次比较下去的元素就是我们的目标元素
  - 解决方案 : 构建一个<int,int>的stack, 第一个元素用于存储这个元素的第二大的元素的索引, 第二个元素用于存储这个元素被弹出的次数(被比较下去的次数), 那么弹出的时候需要分情况, 如果这个时候第二个元素(count)==0, 说明第一次被弹出, 在新的元素被放入单调栈中合适的位置后, 这个元素还需要再被放回stack, 所以会被先暂存入stack2用于未来将这个元素放回stack,  count == 1, 说明这个时候是第二次被弹出, 那么这个索引位置的元素已经找到了它对应的第二大元素, 将这个元素直接pop, 不再放入stack2用于复原

- code 

  ```cpp
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
  
  ```

#### 