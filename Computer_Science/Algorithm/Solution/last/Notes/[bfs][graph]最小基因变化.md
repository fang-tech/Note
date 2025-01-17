# P443.[最小基因变化](https://leetcode.cn/problems/minimum-genetic-mutation/description/)

## 一句话题解

- 这道题不同于典型的八数码问题, 本质上是找一条路径, 从与八数码问题在子问题上的结构清晰的角度看, 以及这实际上是一个图最短路径问题(每条边的长度为1), 可以使用BFS求解, 但从寻找路径的角度看, DFS也不错, 实现上采取的是BFS, 和图相关的题目使用BFS还是很便捷的, 所有的子情况是一个序列对应的32种变化, 每次循环的时候, 将现在队列头的子情况都push进队列即可
- 难点 : 在BFS中获取层数 ,这里是误打误撞写出来的, 在这种写法下, 检查"单个节点是否产生有效变异"，但实际效果等同于在检查"当前层是否产生有效变异", 因为每一层中不管存了多少个元素, 每个元素怎么样, 如果有判重机制的话, 只有这一层第一个被front出来的元素是"有效的", 因为在遍历第一个元素的时候, 就已经遍历了所有的32种子情况, 该层其他的元素, 在第一个元素寻找子情况的时候就已经被visited过了, 所以压根就不能影响flag
  - 正常的层数判断, 在最下方
- 剪枝 : 判重

### 层数的获取

```cpp
while(!q.empty()) {
    int size = q.size(); // 获取当前层的节点数
    for (int i = 0; i < size; i++) {
        ...
    }
    level++; // 当前层处理完毕
}
```

## code

```cpp
/*
 * @lc app=leetcode.cn id=433 lang=cpp
 *
 * [433] 最小基因变化
 */
#include <set>
#include <queue>
#include <string>
#include <iostream>
using namespace std;

// @lc code=start
class Solution {
public:
    int minMutation(string startGene, string endGene, vector<string>& bank) {
        set<string> visited;
        set<string> bankSet(bank.begin(), bank.end());
        queue<string> q;
        int num = 0;
        char status[] = {'A', 'C', 'G', 'T'};
        q.push(startGene);
        bool flag = 0;
        while(!q.empty()) {
            string now = q.front();
            cout << "num : " << num << endl;
            q.pop();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    string g = now;
                    g[i] = status[j];
                    if (!visited.count(g)) {
                        visited.insert(g);
                        if (bankSet.count(g)) {
                            flag = 1;
                            if (g == endGene) return num+1;
                            q.push(g);
                            cout  << "g : " << g << endl;
                        }
                    }
                }
            }
            if (flag) {
                num++;
                flag = 0;
            }
        }
        return -1;
    }
};
// @lc code=end

int main() {
    string start = "AGCAAAAA";
    string end = "GACAAAAA";
    vector<string> bank = {"AGTAAAAA","GGTAAAAA","GATAAAAA","GACAAAAA"};
    Solution s;
    cout << "case 1 : " << endl;
    cout << s.minMutation(start, end, bank) << endl;
    cout <<  "end case";  
    return 0;
}
```

