# P1438.[绝对差不超过限制的最长连续子数组](https://leetcode.cn/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/description/)

- 一句话题解 : 连续子数组其实就是一个个的窗口, 窗口end就是i, 窗口begin向后滑动的条件是窗口中出现了超过限制的数, 一直滑动到正常为止, 这样的解法保证了每个元素只进出窗口一次, 需要特别注意的是这种解法的**边界条件** : 计算length的时机, 如果只是在出现了超过限制的数的时候计算, 就会出现如果最长子数组是窗口滑动到最后出现的, 这种情况因为没有超过限制是不会计算的, 并且这种时候length = end - begin + 1, 其他时候应该是length = end - begin 

- code

  ``` cpp
      int longestSubarray(vector<int>& nums, int limit) {
          deque<int> max_dq, min_dq;
          int length = 0;
          int begin = 0;
          for (int i = 0; i < nums.size(); i++) {
              // max的去尾
              while (!max_dq.empty() && nums[i] < nums[max_dq.back()]) max_dq.pop_back();
              max_dq.push_back(i);
              // min的去尾
              while (!min_dq.empty() && nums[i] > nums[min_dq.back()]) min_dq.pop_back();
              min_dq.push_back(i);
              
              // cout << "begin : " << begin<< ", end : " << i;
              if(abs(nums[max_dq.front()]-nums[min_dq.front()]) > limit) {
                  length = max(length, (i-begin));
                  // cout << "  计算了length";
                  while ((begin <= i) && abs(nums[max_dq.front()]-nums[min_dq.front()]) > limit) {
                      begin++;
                      while (max_dq.front() < begin && !max_dq.empty()) max_dq.pop_front();
                      while (min_dq.front() < begin && !min_dq.empty()) min_dq.pop_front();
                  }
              }
              if (nums.size() - i == 1) {
                  length = max(length, (i-begin+1));
              }
              // cout << ", length : " << length << endl;
          }
          return length;
      }
  };
  // @lc code=end
  int main() {
      Solution s;
      cout << "case 1 : " << endl;
      vector<int> input = {10,1,2,4,7,2};
      int limit = 5;
      cout << s.longestSubarray(input, limit) <<endl;
      cout << "case 2 : " << endl;
      input = {8,2,4,7};
      limit = 4;
      cout << s.longestSubarray(input, limit);
      return 0;
  }
  ```

### 