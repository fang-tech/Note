
/*
 * @lc app=leetcode.cn id=206 lang=java
 *
 * [206] 反转链表
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
 * @Solution: 链表为空的时候,因为无法尝试获取链表头的next会报错, 所以特殊情况直接返回
 *              prev指向-1的位置, curr指向index 0, 然后循环遍历
 *              存储curr的下一个节点, 将curr的下一个节点指向上一个节点
 *              prev = curr, curr向前移动一位
 *              curr是当前正在被反转的节点, 从0开始, 到链表的最后的一个节点, 
 *              在curr指向n的时候, 退出循环, 并返回prev
 */
class Solution {
    // public class ListNode {
    //     int val;
    //     ListNode next;
    //     ListNode() {}
    //     ListNode(int val) { this.val = val; }
    //     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    // }

    public ListNode reverseList(ListNode head) {
        if(head == null) return head;
        
        ListNode prev = null;
        ListNode curr = head;
        ListNode next = null;

        while(curr != null){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        return prev;
    }
}
// @lc code=end

