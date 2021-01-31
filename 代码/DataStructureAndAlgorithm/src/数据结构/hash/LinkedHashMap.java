package 数据结构.hash;

/**
 * @author like
 * @date 2020-12-26 13:18
 * @contactMe 980650920@qq.com
 * @description
 */
public class LinkedHashMap<K, V> extends HashMap<K, V> {
    private LinedNode<K, V> first;
    private LinedNode<K, V> last;

    @Override
    public void clear() {
        super.clear();
        first = null;
        last = null;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) { return; }
        LinedNode<K, V> node = this.first;
        while (node != null) {
            if (visitor.visit(node.key, node.value)) {return;}
            node = node.next;
        }

    }

    @Override
    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        LinedNode<K, V> node = new LinedNode<>(key, value, parent);
        if (first == null) {
            first = last = node;
        } else {
            last.next = node;
            node.prev = last;
            last = node;
        }
        return node;
    }

    @Override
    protected void afterRemove(Node<K, V> willNode, Node<K, V> node) {
        LinedNode<K, V> n1 = (LinedNode<K, V>) willNode;
        LinedNode<K, V> n2 = (LinedNode<K, V>) node;

        if (n2 != n1) {
            // 交換 prev
            LinedNode<K, V> temp = n1.prev;
            n1.prev = n2.prev;
            n2.prev = temp;
            if (n1.prev == null) {
                first = n1;
            } else {
                n1.prev.next = n1;
            }
            if (n2.prev == null) {
                first = n2;
            } else {
                n2.prev.next = n2;
            }
            // 交換 prev
            temp = n1.next;
            n1.next = n2.next;
            n2.next = temp;
            if (n1.next == null) {
                last = n1;
            } else {
                n1.next.prev = n1;
            }
            if (n2.next == null) {
                last = n2;
            } else {
                n2.next.prev = n2;
            }
        }

        // 刪除
        LinedNode<K, V> prev = n1.prev;
        LinedNode<K, V> next = n1.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
    }


    public static class LinedNode<K, V> extends Node<K, V> {
        private LinedNode<K, V> prev;
        private LinedNode<K, V> next;

        public LinedNode(K key, V value, Node<K, V> parent) {
            super(key, value, parent);
        }
    }
}

