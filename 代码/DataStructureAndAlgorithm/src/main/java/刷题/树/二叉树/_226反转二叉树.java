package 刷题.树.二叉树;

import 刷题.树.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author like
 * @date 2020-12-17 10:28
 * @contactMe 980650920@qq.com
 * @description https://leetcode-cn.com/problems/invert-binary-tree/
 * 输入：
 * <p>
 * 4
 * /   \
 * 2     7
 * / \   / \
 * 1   3 6   9
 * 输出：
 * <p>
 * 4
 * /   \
 * 7     2
 * / \   / \
 * 9   6 3   1
 */
public class _226反转二叉树 {

    /**
     * 倒置的树
     * 使用前序遍历
     *
     * @param root 根
     * @return {@link TreeNode}
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        // 交换
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        invertTree(root.left);
        invertTree(root.right);
        return root;
    }


    public TreeNode invertTree2(TreeNode root) {
        if (root == null) {
            return null;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode poll = queue.poll();
            // 交换
            TreeNode temp = poll.left;
            poll.left = poll.right;
            poll.right = temp;
            if (poll.left != null) {
                queue.offer(poll.left);
            }
            if (poll.right != null) {
                queue.offer(poll.right);
            }
        }
        return root;
    }
}

