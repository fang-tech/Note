# [P2679 \[NOIP2015 提高组]子串](https://www.luogu.com.cn/problem/P2679)

## 题目说明

长度为n的A字符串, 长度为m的B字符串, 现在从A中取出k个不重叠的子串按照在A中的顺序来组合成B字符串, 总共有多少种方案

## 题目分析

这种组合成另一个字符串的题目, 实质上一种字符串匹配, 将A中的子串组合成B字符串. 

接下来就需要设计状态, 如何设计出来一个具有无后效应的状态

### DP状态设计

