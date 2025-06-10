package Stack;

public class MinStack {
    private final MyStack dataStack;
    private final MyStack minStack;

    public MinStack(int capacity) {
        this.dataStack = new MyStack(capacity);
        this.minStack = new MyStack(capacity);
    }

    public void push(int value) {
        if (dataStack.isFull()) {
            throw new IllegalStateException("Stack overflow");
        }

        dataStack.push(value);

        if (minStack.isEmpty() || value <= minStack.peek()) {
            minStack.push(value);
        } else {
            minStack.push(minStack.peek());
        }
    }

    public int pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack underflow");
        }

        minStack.pop();
        return dataStack.pop();
    }

    public int getMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return minStack.peek();
    }

    public int peek() {
        return dataStack.peek();
    }

    public boolean isEmpty() {
        return dataStack.isEmpty();
    }

    public boolean isFull() {
        return dataStack.isFull();
    }
}