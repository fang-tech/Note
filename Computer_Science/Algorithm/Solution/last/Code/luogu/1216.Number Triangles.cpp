#include <iostream>
#include <cstdio>
#include <algorithm>
#include <cstring>
using namespace std;

// r : 该元素所在的层数, index : 该元素是该层的第几个元素, 均从1开始计数
// #define INDEX(r, index) ((r+1)*r/2-r+index)
// #define LEFT(r,index) ()
const int N = 1000+5;
int tri[N][N];
int dp[N][N]; // 该元素为起点到底部结束经过的数字最大和
int r;
const int INF = 0x3f3f3f3f;

int dfs(int l, int index) {
    if (!(dp[l][index]==INF)) return dp[l][index]; // 如果到过
    if (l==r) {dp[l][index] = tri[l][index]; return dp[l][index];} // 如果碰到底了, 返回
    
    dp[l][index] = tri[l][index] + max(dfs(l+1, index), dfs(l+1, index+1));
    return dp[l][index];
}

int main() {
    freopen("input.txt", "r", stdin);
    cin>>r;
    for (int i=1;i<=r;i++) {
        for (int j=1;j<=i;j++) scanf("%d ", &tri[i][j]);
    }
    memset(dp, 0x3f, sizeof(dp));
    printf("%d\n", dfs(1,1));
    return 0;
}


