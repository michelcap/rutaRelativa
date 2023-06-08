package TADs.Heap;

import TADs.BST.LL.LL;
import TADs.BST.NodeBST;
import TADs.Queue.Queue;

public class Heap<K extends Comparable<K>, V> implements MyHeap<K, V> {

    HeapNode<K, V> root;

    @Override
    public V find(K key) {
        if (root == null) {
            return null;
        }

        Queue<HeapNode<K, V>> queue = new Queue<>();
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            HeapNode<K, V> currentNode = queue.dequeue();

            // Si la clave del nodo actual coincide con la clave buscada, retorna el valor
            if (currentNode.key.equals(key)) {
                return currentNode.value;
            }

            // Si no, agrega los hijos del nodo actual a la cola
            if (currentNode.leftChild != null) {
                queue.enqueue(currentNode.leftChild);
            }
            if (currentNode.rightChild != null) {
                queue.enqueue(currentNode.rightChild);
            }
        }

        // Si no se encuentra la clave en el árbol, retorna null
        return null;
    }



    @Override
    public void insert(K key, V value) {
        HeapNode<K, V> newNode = new HeapNode<>(key, value);

        if (root == null) {
            root = newNode;
            return;
        }

        Queue<HeapNode<K, V>> queue = new Queue<>();
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            HeapNode<K, V> current = queue.head_front.data;

            if (current.leftChild == null) {
                current.leftChild = newNode;
                percolateUp(current, newNode);
                break;
            } else if (current.rightChild == null) {
                current.rightChild = newNode;
                percolateUp(current, newNode);
                break;
            } else {
                queue.enqueue(current.leftChild);
                queue.enqueue(current.rightChild);
            }

            queue.dequeue();  // remove the node from the queue after we have processed it
        }
    }

    private void percolateUp(HeapNode<K, V> parent, HeapNode<K, V> child) {
        if (parent.key.compareTo(child.key) < 0) {
            K tempKey = parent.key;
            V tempValue = parent.value;

            parent.key = child.key;
            parent.value = child.value;

            child.key = tempKey;
            child.value = tempValue;

            HeapNode<K, V> grandParent = getGrandParent(root, parent);
            if (grandParent != null) {
                percolateUp(grandParent, parent);
            }
        }
    }

    private HeapNode<K, V> getGrandParent(HeapNode<K, V> currentNode, HeapNode<K, V> parentNode) {
        if (currentNode == null) {
            return null;
        }

        if (currentNode.leftChild == parentNode || currentNode.rightChild == parentNode) {
            return currentNode;
        }

        HeapNode<K, V> grandParent = getGrandParent(currentNode.leftChild, parentNode);
        if (grandParent == null) {
            grandParent = getGrandParent(currentNode.rightChild, parentNode);
        }
        return grandParent;
    }


    @Override
    public void delete() {
        if (root == null)
            return;

        Queue<HeapNode<K, V>> queue = new Queue<>();
        queue.enqueue(root);

        HeapNode<K, V> node = null, keyNode = null;

        while (!queue.isEmpty()) {
            node = queue.dequeue();

            if (node.leftChild != null)
                queue.enqueue(node.leftChild);
            if (node.rightChild != null)
                queue.enqueue(node.rightChild);

            if (node.key == root.key)
                keyNode = node;
        }

        if (keyNode != null) {
            K lastKey = node.key;
            V lastData = node.value;
            deleteDeepest(node);
            keyNode.key = lastKey;
            keyNode.value = lastData;
        }
    }

    @Override
    public void delete(K key) {
        if (root == null)
            return;

        Queue<HeapNode<K, V>> queue = new Queue<>();
        queue.enqueue(root);

        HeapNode<K, V> keyNode = null, tempNode = null;

        while (!queue.isEmpty()) {
            tempNode = queue.dequeue();

            if (tempNode.key.equals(key))
                keyNode = tempNode;

            if (tempNode.leftChild != null)
                queue.enqueue(tempNode.leftChild);

            if (tempNode.rightChild != null)
                queue.enqueue(tempNode.rightChild);
        }

        if (keyNode != null) {
            K tempKey = tempNode.key;
            V tempValue = tempNode.value;
            deleteDeepest(tempNode);
            keyNode.key = tempKey;
            keyNode.value = tempValue;
        }
    }

    private void deleteDeepest(HeapNode<K, V> node) {
        Queue<HeapNode<K, V>> queue = new Queue<>();
        queue.enqueue(root);

        HeapNode<K, V> temp;
        while (!queue.isEmpty()) {
            temp = queue.dequeue();

            if (temp == node) {
                temp = null;
                return;
            }

            if (temp.rightChild != null) {
                if (temp.rightChild == node) {
                    temp.rightChild = null;
                    return;
                } else
                    queue.enqueue(temp.rightChild);
            }

            if (temp.leftChild != null) {
                if (temp.leftChild == node) {
                    temp.leftChild = null;
                    return;
                } else
                    queue.enqueue(temp.leftChild);
            }
        }
    }

    @Override
    public String toString() {
        if (root == null)
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        Queue<HeapNode<K, V>> queue = new Queue<>();
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            HeapNode<K, V> currentNode = queue.dequeue();

            sb.append("{Key: " + currentNode.key + ", Value: " + currentNode.value + "}");

            if (currentNode.leftChild != null)
                queue.enqueue(currentNode.leftChild);
            if (currentNode.rightChild != null)
                queue.enqueue(currentNode.rightChild);

            if (!queue.isEmpty())
                sb.append(", ");
        }

        sb.append("]");
        return sb.toString();
    }

    public LL<K> inOrder() {
        return inOrder(root);
    }
    LL<K> arbolInOrder = new LL<>();

    private LL<K> inOrder(HeapNode<K,V> HeapNode){
        if (HeapNode ==null) return null;


        inOrder(HeapNode.getLeftChild());

        arbolInOrder.add(HeapNode.key);

        inOrder(HeapNode.getRightChild());
        return arbolInOrder;
    }

    public LL<K> postOrder() {
        return postOrder(root);
    }

    LL<K> arbolPostOrder = new LL<>();

    private LL<K> postOrder(HeapNode<K,V> HeapNode){
        if (HeapNode ==null) return null;

        postOrder(HeapNode.leftChild);
        postOrder(HeapNode.rightChild);

        arbolPostOrder.add(HeapNode.key);


        return arbolPostOrder;
    }


    public LL<K> preOrder() {
        return preOrder(root);
    }
    LL<K> arbolPreOrder = new LL<>();

    private LL<K> preOrder(HeapNode<K,V> HeapNode){
        if (HeapNode ==null) return null;

        arbolPreOrder.add(HeapNode.key);

        preOrder(HeapNode.leftChild);
        preOrder(HeapNode.rightChild);
        return arbolPreOrder;
    }




}
