/**
 * @luogu 1868.饥饿的奶牛
 */
#include <iostream>
#include <vector>
#include <cstdio>
#include <algorithm>
using namespace std;

const int N = 3e6 + 10;
vector<int> y[N];
int max_y, dp[N]; // y为i的时候能吃到的最多的牧草
int n;

int main() {
    freopen("input.txt", "r", stdin);
    cin>>n;
    for (int i=0;i<n;i++) {
        int _y, _x; cin>>_x>>_y;
        y[_y].push_back(_x);
        max_y = max(max_y, _y);
    }

    for (int i=1;i<=max_y;i++) {
        dp[i] = dp[i-1];
        for (int j=0;j<y[i].size();j++) {
            int b = y[i][j];
            dp[i] = max(dp[i], dp[b-1]+i-b+1);
        }
    }
    cout << dp[max_y];
    return 0;
}