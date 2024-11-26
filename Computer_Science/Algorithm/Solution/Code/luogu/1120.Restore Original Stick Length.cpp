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
int used[55];
int sum;
int flag = 0;
void dfs(const vector<int>& lengths, int d, int start, int cur_sum, int& counter) {
    if (counter == sum / n) flag = 1;
    if (flag) return;
    if (start == lengths.size()) {
        flag = 1;
        return;
    }
    if (used[start]) return;
    cur_sum += lengths[start];
    if (cur_sum == d) {
        counter++;
        used[start] = 1;
        dfs(lengths, d, start+1, 0, counter);
        used[start] = 0;
        counter--;
    }
    if (cur_sum < d) {
        used[start] = 1;
        for(int i = start+1; i < lengths.size(); i++)
            dfs(lengths, d, i, cur_sum, counter);
    }
    if (cur_sum > d) {
        return;
    }
}

void Divisor(vector<int>& div, int n) {
    div.clear();
    for (int i = 1; i*i < n; i++) {
        if (n%i==0) {
            div.push_back(i);
            if (i*i != n) div.push_back(n/i);
        }
    }
    sort(div.begin(), div.end());
}

int main() {
    freopen("input.txt", "r", stdin);
    while (scanf("%d", &n)) {
        memset(a, 0, sizeof(a));
        sum = 0;
        for (int i = 0; i < n; i++) {cin>>a[i]; sum+=a[i];}
        vector<int> lengths(a, a + n);
        sort(lengths.begin(), lengths.end(), greater<int>()); // 将小木棍从大到小排列, 用于优化搜索的顺序
        vector<int> D;// 用于存约数
        // 求所有的约数
        Divisor(D, sum);
        for (int d : D) {
            if (d >= lengths[0]) {
                memset(used, 0, sizeof(used));
                int counter = 0;
                flag = 0;
                dfs(lengths, d, 0, 0, counter);
                if (counter == sum/d) {cout<<d<<"\n"; break;}
            }
        }
    }
    return 0;
}