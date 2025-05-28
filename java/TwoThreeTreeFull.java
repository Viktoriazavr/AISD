
import java.util.*;

public class TwoThreeTreeFull {

    // Счетчик переходов между узлами
    static class OperationCounter {
        public int transitions = 0;
        public void step() { transitions++; }
        public void reset() { transitions = 0; }
    }

    static class Node {
        Integer key1, key2;
        Node left, middle, right;

        Node(int key) { key1 = key; }
        boolean isLeaf() { return left == null && middle == null && right == null; }
        boolean is2Node() { return key2 == null; }
    }

    static class TwoThreeTree {
        Node root;
        OperationCounter counter = new OperationCounter();

        public void insert(int key) {
            counter.reset();
            root = insert(root, key);
        }

        private Node insert(Node node, int key) {
            if (node == null) {
                counter.step();
                return new Node(key);
            }
            counter.step();
            if (node.isLeaf()) {
                if (node.key2 == null) {
                    if (key < node.key1) {
                        node.key2 = node.key1;
                        node.key1 = key;
                    } else {
                        node.key2 = key;
                    }
                    return node;
                } else {
                    return splitLeaf(node, key);
                }
            } else {
                if (key < node.key1) {
                    node.left = insert(node.left, key);
                } else if (node.key2 == null || key < node.key2) {
                    node.middle = insert(node.middle, key);
                } else {
                    node.right = insert(node.right, key);
                }
                return node;
            }
        }

        private Node splitLeaf(Node node, int key) {
            List<Integer> keys = new ArrayList<>(Arrays.asList(node.key1, node.key2, key));
            Collections.sort(keys);
            Node newParent = new Node(keys.get(1));
            newParent.left = new Node(keys.get(0));
            newParent.middle = new Node(keys.get(2));
            return newParent;
        }

        public boolean search(int key) {
            counter.reset();
            return search(root, key);
        }

        private boolean search(Node node, int key) {
            while (node != null) {
                counter.step();
                if (key == node.key1 || (node.key2 != null && key == node.key2)) return true;
                if (key < node.key1) node = node.left;
                else if (node.key2 == null || key < node.key2) node = node.middle;
                else node = node.right;
            }
            return false;
        }

        public void delete(int key) {
            counter.reset();
            root = delete(root, key);
        }

        private Node delete(Node node, int key) {
            if (node == null) return null;
            counter.step();
            if (node.isLeaf()) {
                if (Objects.equals(node.key1, key)) {
                    node.key1 = node.key2;
                    node.key2 = null;
                } else if (Objects.equals(node.key2, key)) {
                    node.key2 = null;
                }
                return node;
            }
            if (key < node.key1) node.left = delete(node.left, key);
            else if (node.key2 == null || key < node.key2) node.middle = delete(node.middle, key);
            else node.right = delete(node.right, key);
            return node;
        }
    }
}