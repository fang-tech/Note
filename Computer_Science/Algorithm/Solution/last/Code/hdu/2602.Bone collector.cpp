#include <cstring>
#include <iostream>
#include <algorithm>
using namespace std;

int N, C;
int w[1000+3], c[1000];
int dp[1000+3][1000+3];
const int INF = 0x3f3f3f3f;

int package(int i, int j) {
    if (dp[i][j] != INF) return dp[i][j];
    if (i == 0 || j == 0) {dp[i][j] = 0; return dp[i][j];}
    if (c[i] > j) return package(i-1, j);
    else return max(package(i-1, j), package(i-1, j-c[i])+w[i]);
}

int main() {
    // freopen("input.txt", "r", stdin);
    int x; cin>>x;
    for (int i = 0; i < x; i++) {
        memset(dp, 0x3f, sizeof(dp));
        scanf("%d %d", &N, &C);
        for (int j = 1; j <= N; j++) cin>>w[j];
        for (int j = 1; j <= N; j++) cin>>c[j];
        int ans = package(N, C);
        printf("%d\n", ans);
    }
    return 0;
}