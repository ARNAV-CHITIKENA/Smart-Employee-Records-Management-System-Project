// =====================================================================
//  NavigationStack.java  —  DSA: Stack for admin page navigation
// =====================================================================
import java.util.LinkedList;

public class NavigationStack {

    private final LinkedList<String> stack = new LinkedList<>();

    // Push a page name onto the stack
    public void push(String page) {
        stack.push(page);
    }

    // Pop the top page (returns null if empty)
    public String pop() {
        return stack.isEmpty() ? null : stack.pop();
    }

    // Peek at the top without removing
    public String peek() {
        return stack.isEmpty() ? null : stack.peek();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }
}
