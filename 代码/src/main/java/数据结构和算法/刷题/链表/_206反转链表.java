package 数据结构和算法.刷题.链表;

/**
 * The type 206 反转链表.
 *
 * @author likeLove
 * @since 2020 -10-01  18:50
 * https://leetcode-cn.com/problems/reverse-linked-list/
 * 反转一个单链表。
 * <p> 示例:
 * <p> 输入:
 * 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 * 进阶: 你可以迭代或递归地反转链表。你能否用两种方法解决这道题？
 */
public class _206反转链表 {


    /**
     * 递归
     *
     * @param head the head
     *
     * @return 新的头节点
     */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseList(head.next);

        //如果链表是 1->2->3->4->5，那么此时的cur就是5
        //而head是4，head的下一个是5，下下一个是空
        //所以head.next.next 就是5->4
        head.next.next = head;
        //防止链表循环，需要将head.next设置为空
        head.next = null;

        return newHead;
    }

    /**
     * 迭代
     *
     * @param head the head
     *
     * @return the list node
     */
    public ListNode reverseList2(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            //保存curr的下一个节点
            ListNode temp = curr.next;

            //后移prev
            curr.next = prev;
            prev = curr;

            //后移curr
            curr = temp;
        }

        return prev;
    }
}

