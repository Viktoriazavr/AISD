package LinkedList;

public class Main3 {
    public static void main(String[] args) {
        MyLinkedList<String> list = new MyLinkedList<>();

        list.append("A");
        list.append("B");
        list.prepend("C");

        System.out.println(list); // [C, A, B]
        System.out.println(list.size()); // 3

        // Проверяем наличие элементов
        System.out.println(list.contains("A")); // true
        System.out.println(list.contains("D")); // false

        // Удаляем элемент
        list.remove("A");
        System.out.println(list); // [C, B]

        System.out.println(list.get(1)); // B
    }
}