#include <algorithm>
#include <vector>
using namespace std;

class Solution {
public:
    
    vector<vector<int>> f; // 走到(x,y)的时候能获得的最大价值
    /**
     * 我们从起点向外走, 每次可以选择向下或者向右
     * 定义f(x,y), 在x,y位置能获得的最大收益
     * f(x,y) = max(f(x-1, y), f(x, y-1)) + now_value
     */
    int jewelleryValue(vector<vector<int>>& frame) {
        int m = frame.size(); int n = frame[0].size();
        f.resize(frame.size()+1, vector<int>(frame[0].size()+1, 0));
        f[0][0] = 0;
        f[1][0] = 0;
        f[0][1] = 0;

        for (int x = 0; x < m; x++){
            for (int y = 0; y < n; y++) {
                f[x+1][y+1] = max(f[x][y+1], f[x+1][y]) + frame[x][y];
            }
        }

        return f[m][n];
    }
};