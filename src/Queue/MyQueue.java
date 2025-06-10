package Queue;

import java.util.Stack;

public class MyQueue<T> {
    private final Stack<T> inStack;   // Стек для добавления элементов
    private final Stack<T> outStack;  // Стек для извлечения элементов

    public MyQueue() {
        this.inStack = new Stack<>();
        this.outStack = new Stack<>();
    }

    public void enqueue(T item) {
        inStack.push(item);
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Очередь пуста");
        }
        transferIfNeeded();
        return outStack.pop();
    }

    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Очередь пуста");
        }
        transferIfNeeded();
        return outStack.peek();
    }

    public boolean isEmpty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    public int size() {
        return inStack.size() + outStack.size();
    }

    private void transferIfNeeded() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
    }
}