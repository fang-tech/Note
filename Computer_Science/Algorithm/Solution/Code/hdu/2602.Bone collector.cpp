#include <cstring>
#include <iostream>
#include <algorithm>
using namespace std;

int N, C;
int w[1000+3], c[1000], dp[1000+3];

int solve() {
    memset(dp, 0, sizeof(dp));
    int _new=1, old=0;
    for (int i = 1; i <= N; i++) {
        swap(_new, old);
        for (int j = C; j >= 0; j--) {
            if (c[i] > j) continue;
            else dp[j] = max(dp[j-c[i]] + w[i], dp[j]);
        }
    }
    return dp[C];
}

int main() {
    freopen("input.txt", "r", stdin);
    int x; cin>>x;
    for (int i = 0; i < x; i++) {
        scanf("%d %d", &N, &C);
        for (int j = 1; j <= N; j++) cin>>w[j];
        for (int j = 1; j <= N; j++) cin>>c[j];
        int ans = solve();
        printf("%d\n", ans);
    }
    return 0;
}