package 刷题.链表;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/linked-list-cycle/
 * 给定一个链表，判断链表中是否有环。
 * <p>
 * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是
 * -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
 * <p>
 * 如果链表中存在环，则返回 true 。 否则，返回 false 。
 *
 * @author likeLove
 * @since 2020-10-02  19:16
 */
public class _141环形链表 {

    /**
     * 判断链表是否有环
     * - 使用hashSet判断
     *
     * @param head head节点
     * @return boolean
     */
    public boolean hasCycle(ListNode head) {
        //定义辅助节点和set集合
        ListNode temp = head;
        Set<ListNode> set = new HashSet<>();

        //循环
        while (temp != null) {
            //判断这个节点是否存在在集合中，存在就返回true
            if (set.contains(temp)) {
                return true;
            }

            //添加到集合中
            set.add(temp);

            //后移
            temp = temp.next;
        }
        return false;
    }

    /**
     * 判断链表是否有环
     * - 快慢指针
     *
     * @param head the head
     * @return the boolean
     */
    public boolean hasCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        //定义快慢指针
        ListNode slow = head;
        ListNode fast = head.next;

        while (fast != null || fast.next == null) {
            //如果快慢指针指向同一个节点，就说明有环
            if (fast == slow) {
                return true;
            }

            //后移
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }

    /**
     * 判断链表是否有环
     * 递归-标记法
     *
     * @param head the head
     * @return the boolean
     */
    private boolean traverseMarkSolution(ListNode head) {
        if (head == null) {
            return false;
        }

        //判断是否遍历过
        if (head.val == 0xcafebabe) {
            return true;
        }

        //标记，表示这个节点已经遍历过
        head.val = 0xcafebabe;

        return traverseMarkSolution(head.next);
    }
}
