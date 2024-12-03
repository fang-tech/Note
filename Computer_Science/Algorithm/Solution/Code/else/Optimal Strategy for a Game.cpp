// 有n堆硬币, 价值数组v[n], n为偶数, 
// 每次只能在现在的硬币堆的第一堆和最后一堆拿硬币, 
// 现在你是先手拿硬币的人, 给出你能拿到的最大价值
// dp[i][j] => 第i堆硬币到第j堆硬币你先手拿硬币能拿到的最大价值
// 输入 :n
//       v[n]
#include <iostream>
#include <cstdio>
using namespace std;

const int N = 100;
int v[N];
int dp[N][N];
int n;

int main() {
    //@test
    freopen("input.txt", "r", stdin);
    cin >> n;
    for (int i=1;i<=n;i++) cin>>v[i];

    for (int i=n;i>=1;i-=2) {
        for (int j=i+2;j<=n;j+=2) {
            
        }
    }

    return 0;
}