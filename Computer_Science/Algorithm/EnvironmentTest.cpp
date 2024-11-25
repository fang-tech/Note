#include <iostream>
#include <utility>
#include <vector>
#include <bitset>
using namespace std;

int main() {
    // cout << "hello world";
    int temp = 123123;
    cout << "temp = " << (bitset<16>) temp << ", temp & 1 = " << (bitset<16>)( temp & 1 ) << endl;
    temp = 2311232;
    cout << "temp = " << (bitset<16>) temp << ", temp & 1 = " << (bitset<16L>) (temp & 1)  << endl;
    return 0;
}