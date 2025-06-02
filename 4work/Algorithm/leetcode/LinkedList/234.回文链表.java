/*
 * @lc app=leetcode.cn id=234 lang=java
 *
 * [234] 回文链表
 */

import java.util.ArrayDeque;
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
 * @Solution: 先找到链表的中间节点, 在找到的过程中, 将中间节点以前的节点的值压入栈
 *              然后从中间节点后的一个节点和栈顶的值比对
 */
class Solution {
    public boolean isPalindrome(ListNode head) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            stack.addFirst(slow.val);
            fast = fast.next.next;
            slow = slow.next;
        }

        if (fast != null && fast.next == null) slow = slow.next; // 奇数节点, slow需要向前再走一步

        while (slow != null) {
            int top = stack.pollFirst();
            if (top != slow.val) return false;
            slow = slow.next;
        }

        return true;
    }
}
// @lc code=end

