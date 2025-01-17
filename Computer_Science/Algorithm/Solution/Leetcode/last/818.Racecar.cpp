/*
 * @lc app=leetcode.cn id=818 lang=cpp
 *
 * [818] 赛车
 */
#include <queue>
#include <utility>
#include <set>
#include <string>
#include <vector>
#include <iostream>
using namespace std;
// @lc code=start
class Solution {
// private:
//     struct node
//     {
//         int position;
//         int speed;
//         string path;
//         node(int p, int s, string path): position(p), speed(s), path(path) {}
//     };
    
public:
    // 三种状态
    // position += speed, speed *= 2
    // speed = 1
    // speed = -1 -> 只有在position > target 且 speed > 0的时候触发
    // 
    int bfs(int target) { 
        int level = 0;
        pair<int, int> start(0,1); // (position, speed)
        queue<pair<int, int>> q;
        set<pair<int,int>> visited; // 用于判重
        visited.insert(start);
        // 直接让车子先达到会出现路径的前一段位置
        int position = start.first; int speed = start.second;
        while (!(position + speed >= target)) {
            position += speed;
            speed *= 2;
            level++;
            cout << "\nlevel : " << level << endl;
            visited.insert(make_pair(position, speed));
            printf("<%d, %d> ", position, speed);
        }
        int left_boundary = 0; int right_boundary = position + speed;
        start.first = position; start.second = speed;
        q.push(start);
        while (!q.empty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) { // 用于记层数, 即指令数
                pair<int,int> curr = q.front();
                int position = curr.first; int speed = curr.second;
                // printf("<%d,%d> ", position, speed);
                q.pop();
                
                if (1) { // A指令
                    int rp = position + speed;
                    int rs = speed*2;
                    if (rp == target) {
                        return ++level; 
                    }
                    pair<int,int> r(rp, rs);
                    if (!visited.count(r) && rp <= right_boundary && rp >= left_boundary) {// 判重减枝
                        visited.insert(r);
                        printf("<%d, %d> ", rp, rs);
                        q.push(r);
                    }
                }

                if (1) { // R指令
                    int as = speed > 0 ? -1 : 1;
                    pair<int, int> a(position, as);
                    if (!visited.count(a)) { // 判重减枝
                        visited.insert(a);
                        printf("<%d, %d> ", position, as);
                        q.push(a);
                    }
                }
            }
            level++;
            cout << "\nlevel : " << level << endl;
        }
        
        return 0;
    }

    int racecar(int target) {
        int ans = 0;
        ans = bfs(target);
        return ans;
    }
};
// @lc code=end

int main() {
    Solution s;
    vector<int> target = {5};
    for (int i = 1; i <= target.size(); i++) {
        cout << "case " << i << " : \n" << "target = " << target[i-1];
        cout << ", ans : " << s.racecar(target[i-1]) << endl;
    }
    return 0;
}