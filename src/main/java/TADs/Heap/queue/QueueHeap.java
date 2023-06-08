package TADs.Heap.queue;

public class QueueHeap<T extends Comparable<T>> implements MyQueueHeap<T>{

    public NodeHeap<T> tail_rear;
    public NodeHeap<T> head_front;

    @Override
    public void enqueue(T element) {
        NodeHeap<T> newNodeHeap = new NodeHeap<>(element, 0);

        if (head_front == null && tail_rear == null) {
            head_front = tail_rear = newNodeHeap;
            return;
        }

        tail_rear.next = newNodeHeap;
        tail_rear = newNodeHeap;
    }



    @Override
    public T dequeue() {
        if (head_front == null) return null; // Si la cola está vacía, devolvemos null

        T value = head_front.data; // Guardamos el valor que vamos a devolver

        if(head_front == tail_rear){ // Si solo hay un elemento
            head_front = tail_rear = null;
        } else {
            head_front = head_front.next;
        }

        return value; // Devolvemos el valor
    }


    @Override
    public boolean isEmpty() {
        return (head_front == null && tail_rear == null);
    }


    @Override
    public void imprimir() {
        NodeHeap<T> temporaryNodeHeap = head_front;

        while (temporaryNodeHeap.next != null){
            System.out.print(temporaryNodeHeap.data + " --> ");
            temporaryNodeHeap = temporaryNodeHeap.next;
        }
        System.out.println(temporaryNodeHeap.data);
    }

    @Override
    public void enqueueWithPriority(T element, int priority) {
        NodeHeap<T> newNodeHeap = new NodeHeap<>(element, priority);

        // Si la cola está vacía, el nuevo nodo es tanto la cabeza como la cola
        if (isEmpty()) {
            head_front = tail_rear = newNodeHeap;
            return;
        }

        // Si la prioridad del nuevo nodo es mayor que la cabeza, se convierte en la nueva cabeza
        if (head_front.priority < priority) {
            newNodeHeap.next = head_front;
            head_front = newNodeHeap;
        } else {
            NodeHeap<T> current = head_front;

            // Busca el lugar correcto en la cola y añade el nodo
            while (current.next != null && current.next.priority >= priority) {
                current = current.next;
            }

            newNodeHeap.next = current.next;
            current.next = newNodeHeap;

            // Si el nodo se añade al final de la cola, actualizar la cola
            if (current == tail_rear) {
                tail_rear = newNodeHeap;
            }
        }
    }


    // -----  ------  -----  ------  -----  ------  -----  ------  -----  ------  -----  ------
    //aca arranca el main

    public static void main(String[] args){
        QueueHeap<Integer> cola = new QueueHeap<>();

        System.out.println("mi cola esta vacia? "+cola.isEmpty());

        cola.enqueue(1);
        cola.enqueue(2);
        cola.enqueue(3);
        cola.imprimir();
        System.out.println();
        //comentario aver si se sube al repo


        cola.dequeue();
        System.out.print("dsp de hacer dequeue: ");
        cola.imprimir();


        System.out.println("mi cola esta vacia? "+cola.isEmpty());


    }
}

