/*
 * @lc app=leetcode.cn id=876 lang=java
 *
 * [876] 链表的中间结点
 */

// @lc code=start
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

/**
 * @Solution: 假设走了n步, slow在1 + n位置, fast在1 + 2n位置
 *              如果链表的长度是x, 则最后1 + 2n > x,
 *              分成两种情况, 1 + 2n == x + 1(x是偶数) / 1 + 2n == x + 2(x是奇数)
 *              分别对应n = x / 2, n = (x + 1) /2
 *              slow在的位置就是 1 + x / 2 (x是奇数)或者 1 + (x + 1) /2 (x是偶数)
 *              x是奇数的时候, 中间节点的位置就是x / 2 + 1(x == 5, mid = 3)
 *              x是偶数的时候, 中间节点的位置是1 + x / 2(x == 6, mid == 4)
 *              
 *              
 */
class Solution {
    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
}
// @lc code=end

