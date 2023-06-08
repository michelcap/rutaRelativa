package TADs.Heap.queue;

public interface MyQueueHeap<T> {
    void enqueue(T element);
    T dequeue();
    boolean isEmpty();

    void imprimir();

    public void enqueueWithPriority(T element, int prioridad);

}

