package Stack;

public class Main {
    public static void main(String[] args) {
        MinStack stack = new MinStack(5);

        stack.push(3);
        stack.push(5);
        stack.push(2);
        stack.push(1);

        System.out.println(stack.getMin()); // 1
        System.out.println(stack.peek());  // 1

        stack.pop();
        System.out.println(stack.getMin()); // 2
        System.out.println(stack.peek());  // 2

        stack.pop();
        System.out.println(stack.getMin()); // 3
    }
}
