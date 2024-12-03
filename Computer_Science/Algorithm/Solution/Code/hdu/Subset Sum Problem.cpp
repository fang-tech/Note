#include <iostream>
#include <cstdio>
using namespace std;

const int N = 1000;
int set[N];
int dp[N];
int n = 0;
int m;

int main() {
    freopen("input.txt", "r", stdin);
    int i=1;
    cin >> m;
    while (cin>>set[i++]) n++;
    i = -1;
    // while (!set[++i]) cout << set[i] << " ";

    dp[0] = 1;

    for (int i=1;i<=n;i++) {
        for (int j=m;j>=set[i];j--) {
            if (j >= set[i])
                dp[j] = max(dp[j], dp[j-set[i]]);
        } 
    }

    cout << dp[m];
    return 0;
}