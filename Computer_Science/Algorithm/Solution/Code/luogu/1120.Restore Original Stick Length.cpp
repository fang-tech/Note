#include <vector>
#include <algorithm>
#include <numeric>
#include <cstdio>
#include <set>
#include <iostream>
using namespace std;
int n;
int a[55];
int flag = 0;
int used[55];
int sum;
void dfs(vector<int> lengths, int d) {
    // 验证通过全排列原来的木棍, 能不能做到将它还原为长度为d的长木棍
    
    
}

int main() {
    freopen("input.txt", "r", stdin);
    while (scanf("%d")) {
        memset(a, 0, sizeof(a));
        sum = 0;
        for (int i = 0; i < n; i++) {cin>>a[i]; sum+=a[i];}
        vector<int> lengths(a, a + n);
        sort(lengths.begin(), lengths.end(), greater<int>()); // 将小木棍从大到小排列, 用于优化搜索的顺序
        vector<int> D;// 用于存约数
        // 求所有的约数
        for (int i = 1; i * i <= sum; i++) {
            if (n % i == 0) {
                D.push_back(i);
                if (i * i != n) {
                    D.push_back(n / i);
                }
            }
        }
        sort(D.begin(), D.end());

        for (int d : D) {
            flag = 0;
            dfs(lengths, d);
            if (flag) {cout<<d<<"\n"; break;}
        }
    }
    return 0;
}