/*
 * @lc app=leetcode.cn id=234 lang=java
 *
 * [234] 回文链表
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
    public boolean isPalindrome(ListNode head) {
        // 哨兵节点
        ListNode s = new ListNode(0, head);
        // 找到中间节点
        ListNode slow = s;
        ListNode fast = s;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        slow = slow.next; // 中间节点
        
        ListNode head2 = reverseList(slow);
        while (head2 != null) {
            if (head2.val != head.val)  
                return false;
            head2 = head2.next;
            head = head.next;
        }

        return true;
    }

    public ListNode reverseList(ListNode head) {
        ListNode c = head, p = null, n = head;
        while (c != null) {
            n = n.next;
            c.next = p;
            p = c;
            c = n;
        }
        return p;
    }
}
// @lc code=end

