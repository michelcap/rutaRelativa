package TADs.Heap.queue;

public class NodeHeap<T extends Comparable<T>> {

    public T data;
    public NodeHeap<T> next;

    public int priority;

    public NodeHeap(T data, int priority) {
        this.data = data;
        this.priority = priority;
        this.next = null;
    }

}
