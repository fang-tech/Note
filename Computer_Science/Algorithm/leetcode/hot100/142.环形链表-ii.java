/*
 * @lc app=leetcode.cn id=142 lang=java
 *
 * [142] 环形链表 II
 */

// @lc code=start
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    /**
     * 是怎么定位环的入口的?
     * 设从起点到环入口之间的距离是a, 从环起点到相遇点是c, 环的长度是b
     * 则有slow = a + c, fast = a + c + kb, 这是两个指针走过的距离
     * 则kb = a + c
     * 这也就说明, slow在环中再走a步, 那么它在环中走过距离就是kb了, 回到了环起点, 
     * 并且head指针同步走a步也会到达环起点, 两个节点会相遇
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                while (head != slow) {
                    slow = slow.next;
                    head = head.next;
                }
                return slow;
            }
        }
        return null;
    }
}
// @lc code=end

