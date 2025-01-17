#include <vector>
#include <algorithm>
#include <numeric>
#include <cstdio>
#include <bits/stdc++.h>
#include <set>
#include <iostream>
using namespace std;
int n;
int a[55];
vector<int> Div;
int used[55];
int sum;
int flag = 0;
int d = 0; // 用于存现在的约数
int cnt;

void dfs(int start, int cur_sum, int num) {
    if (flag) return;
    if (num == cnt+1) {flag = 1; return;}  // 最后合成了需要数量的木棍
    // 越界问题
    if (!used[start]) cur_sum += a[start];
    if (cur_sum == d) {dfs(0, 0, num+1)}
    if (cur_sum < d) {
        for(int i = n; i > 0; i--) {
            if (!used[i]) {
                used[i] = 1;
                dfs(i, cur_sum, num);
                used[i] = 0;
            }
        }
    }

}
void Divisor(int n) {
    // 求一个数字的所有约数
    Div.clear();
    for (int i = 1; i*i < n; i++) {
        if (n%i==0) {
            Div.push_back(i);
            if (i*i != n) Div.push_back(n/i);
        }
    }
    sort(Div.begin(), Div.end());
}

int main() {
    freopen("input.txt", "r", stdin);
    while (scanf("%d", &n)) {
        sum = 0;
        for (int i = 0; i < n; i++) {cin>>a[i]; sum+=a[i];}
        sort(a, a+n, greater<int>()); // 将小木棍从大到小排列, 用于优化搜索的顺序
        // 求所有的约数
        Divisor(sum);
        for (int i = 0; i < Div.size(); i++) {
            d = Div[i];
            if (d >= a[0]) {
                memset(used, 0, sizeof(used));
                flag = 0;
                cnt = sum / d; // 最后合成的木棍数
            }
        }
    }
    return 0;
}