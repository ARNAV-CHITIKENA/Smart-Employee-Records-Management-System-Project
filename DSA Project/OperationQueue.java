// =====================================================================
//  OperationQueue.java  —  DSA: Queue for admin operation logs
// =====================================================================
import java.util.LinkedList;

public class OperationQueue {

    private final LinkedList<String> queue = new LinkedList<>();

    // Add an operation description to the back of the queue
    public void enqueue(String operation) {
        queue.offer(operation);
    }

    // Remove and return the front operation (returns null if empty)
    public String dequeue() {
        return queue.isEmpty() ? null : queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    // Print all queued operations without removing them
    public void printAll() {
        if (queue.isEmpty()) {
            System.out.println("  (No operations logged yet)");
            return;
        }
        int i = 1;
        for (String op : queue) {
            System.out.println("  " + i++ + ". " + op);
        }
    }

    // Drain (clear) the entire queue
    public void clear() {
        queue.clear();
    }
}
