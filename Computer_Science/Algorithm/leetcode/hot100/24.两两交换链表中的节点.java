/*
 * @lc app=leetcode.cn id=24 lang=java
 *
 * [24] 两两交换链表中的节点
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
class Solution {
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0, head);
        ListNode prev = dummy;
        ListNode cur = dummy;
        ListNode next = dummy;
        
        while (cur != null && cur.next != null && cur.next.next != null) {
            prev = cur;
            cur = cur.next;
            next = cur.next;

            cur.next = next.next;
            next.next = cur;
            prev.next = next;
        }

        return dummy.next;
    }
}
// @lc code=end

