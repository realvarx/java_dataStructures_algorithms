package dataStructures;

public interface BSTree<E> {
    boolean isEmpty();

    E getInfo();

    Comparable<Object> getKey();

    BSTree<E> getLeft();

    BSTree<E> getRight();

    String toStringPreOrder();

    String toStringInOrder();

    String toStringPostOrder();

    String toString(); // pre-order

    void insert(Comparable<Object> key, E info);

    BSTree<E> search(Comparable<Object> key);
}