#include <iostream>
#include <utility>
#include <vector>
using namespace std;

int main() {
    cout << "hello world";
    pair<int,int> p(1,2);
    cout << p.first << p.second;
    vector<pair<int,int>> v(2, (0, 0));
    return 0;
}