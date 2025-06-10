package Queue;

public class Main2 {
    public static void main(String[] args) {
        MyQueue<Integer> queue = new MyQueue<>();

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        System.out.println(queue.peek()); // 1
        System.out.println(queue.size()); // 3

        // Извлекаем элементы
        System.out.println(queue.dequeue()); // 1
        System.out.println(queue.dequeue()); // 2

        queue.enqueue(4);

        System.out.println(queue.peek()); // 3
        System.out.println(queue.isEmpty()); // false
    }
}
