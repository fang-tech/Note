/*
 * @lc app=leetcode.cn id=2 lang=java
 *
 * [2] 两数相加
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
    
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        ListNode cur = dummy; 
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            if (l1 != null) {
                carry += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                carry += l2.val;
                l2 = l2.next;
            }
            cur.next = new ListNode(carry % 10);
            cur = cur.next;
            carry /= 10;
        }
        return dummy.next;
    }

    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        ListNode cur = dummy; 
        int cin = 0;
        while (l1 != null && l2 != null) {
            int sum = l1.val + l2.val + cin;
            if (sum < 10) {
                cur.next = new ListNode(sum);
                cin = 0;
            } else {
                cur.next = new ListNode(sum - 10);
                cin = 1;
            }
            cur = cur.next;
            l1 = l1.next;
            l2 = l2.next;
        }

        while (l1 != null) {
            int sum = l1.val + cin;
            if (sum < 10) {
                cur.next = new ListNode(sum);
                cin = 0;
            } else {
                cur.next = new ListNode(sum - 10);
                cin = 1;
            }
            cur = cur.next;
            l1 = l1.next;
        }

        while (l2 != null) {
            int sum = l2.val + cin;
            if (sum < 10) {
                cur.next = new ListNode(sum);
                cin = 0;
            } else {
                cur.next = new ListNode(sum - 10);
                cin = 1;
            }
            cur = cur.next;
            l2 = l2.next;
        }

        if (cin == 1) { // 最后还有一个进位信号
            cur.next = new ListNode(1);
        }

        return dummy.next;
    }
}
// @lc code=end

