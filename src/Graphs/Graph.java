package Graphs;

public interface Graph<E, V> {

    int numVertices();

    int numEdges();

    Iterable<Vertex<V>> vertices();

    Iterable<Edge<E>> edges();

    int outDegree(Vertex<V> v) throws IllegalArgumentException;

    int inDegree(Vertex<V> v) throws IllegalArgumentException;

    Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException;

    Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException;

    Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException;

    Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException;

    Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException;

    Vertex<V> insertVertex(V element);

    Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException;

    void removeVertex(Vertex<V> v) throws IllegalArgumentException;

    void removeEdge(Edge<E> e) throws IllegalArgumentException;
}
