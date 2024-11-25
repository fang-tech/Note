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
