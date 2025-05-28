/*
 * @lc app=leetcode.cn id=92 lang=java
 *
 * [92] 反转链表 II
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
 * @Solution: 需要往某个节点添加/删除节点的时候, 可以考虑哨兵节点, 
 *              相当于有了一个-1的节点, 能向0前面添加节点
 *              
 * 
 * */
class Solution {
    // public class ListNode {
    //     int val;
    //     ListNode next;
    //     ListNode() {}
    //     ListNode(int val) { this.val = val; }
    //     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    // }

    public ListNode reverseBetween(ListNode head, int left, int right) {

        // 哨兵节点
        ListNode dummy = new ListNode(0, head);
        // 找到left的前一个节点
        ListNode leftPrev = dummy;
        for(int cnt = 0; cnt < left - 1; cnt++)
            leftPrev = leftPrev.next;

        ListNode prev = null;
        ListNode curr = leftPrev.next;
        ListNode next = null;

        for (int i = left; i <= right; i++) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        leftPrev.next.next = curr; // 反转区间的第一个元素指向right后面的元素
        leftPrev.next = prev; // 反转区间的前面一个元素指向现在反转区间的第一个元素

        return dummy.next;
    }

}
// @lc code=end

