package Graphs;

import Maps.Map;
import Maps.ProbeHashMap;
import PositionalLists.LinkedPositionalList;
import PositionalLists.Position;
import PositionalLists.PositionalList;

public class AdjacencyMapGraph<E, V> implements Graph<E, V> {

    private class InnerVertex<V> implements Vertex<V> {
        private V element;
        private Position<Vertex<V>> pos;
        private Map<Vertex<V>, Edge<E>> outgoing, incoming;

        public InnerVertex(V element, boolean isDirected) {
            this.element = element;
            outgoing = new ProbeHashMap<>();

            if (isDirected)
                incoming = new ProbeHashMap<>();
            else
                incoming = outgoing;
        }

        public boolean validate(Graph<E, V> graph) {
            return (AdjacencyMapGraph.this == graph && pos != null);
        }


        @Override
        public V getElement() {
            return element;
        }

        public Position<Vertex<V>> getPos() {
            return pos;
        }

        public Map<Vertex<V>, Edge<E>> getOutgoing() {
            return outgoing;
        }

        public Map<Vertex<V>, Edge<E>> getIncoming() {
            return incoming;
        }

        public void setPos(Position<Vertex<V>> pos) {
            this.pos = pos;
        }
    }

    private class InnerEdge<E> implements Edge<E> {
        private E element;
        private Position<Edge<E>> pos;
        private Vertex<V>[] endpoints;

        @SuppressWarnings({"unchecked"})
        public InnerEdge(E element, Vertex<V> u, Vertex<V> v) {
            this.element = element;
            endpoints = (Vertex<V>[]) new Vertex[]{u, v};
        }

        public boolean validate(Graph<E, V> graph) {
            return AdjacencyMapGraph.this == graph && pos != null;
        }

        public void setPos(Position<Edge<E>> pos) {
            this.pos = pos;
        }

        public Position<Edge<E>> getPos() {
            return pos;
        }

        public Vertex<V>[] getEndpoints() {
            return endpoints;
        }

        @Override
        public E getElement() {
            return element;
        }
    }

    private boolean isDirected;
    private PositionalList<Vertex<V>> vertices = new LinkedPositionalList<>();
    private PositionalList<Edge<E>> edges = new LinkedPositionalList<>();

    public AdjacencyMapGraph(boolean isDirected) {
        this.isDirected = isDirected;
    }

    private InnerVertex<V> validate(Vertex<V> v) {
        if (!(v instanceof InnerVertex)) throw new IllegalArgumentException("Invalid vertex");
        InnerVertex<V> vert = (InnerVertex<V>) v;
        if (!vert.validate(this)) throw new IllegalArgumentException("Invalid vertex");
        return vert;
    }

    private InnerEdge<E> validate(Edge<E> e) {
        if (!(e instanceof InnerEdge)) throw new IllegalArgumentException("Invalid edge");
        InnerEdge<E> edge = (InnerEdge<E>) e;
        if (!edge.validate(this)) throw new IllegalArgumentException("Invalid edge");
        return edge;
    }

    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public int numEdges() {
        return edges.size();
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        return vertices;
    }

    @Override
    public Iterable<Edge<E>> edges() {
        return edges;
    }

    @Override
    public int outDegree(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vertex = validate(v);
        return vertex.getOutgoing().size();
    }

    @Override
    public int inDegree(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vertex = validate(v);
        return vertex.getIncoming().size();
    }

    @Override
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vertex = validate(v);
        return vertex.getOutgoing().valueSet();
    }

    @Override
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vertex = validate(v);
        return vertex.getIncoming().valueSet();
    }

    @Override
    public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> origin = validate(u);
        return origin.getOutgoing().get(v);
    }

    @Override
    public Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
        return edge.getEndpoints();
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endpoint = edge.getEndpoints();
        if (endpoint[0] == v)
            return endpoint[1];
        if (endpoint[1] == v)
            return endpoint[0];
        throw new IllegalArgumentException("v is not a incident to this edge");
    }

    @Override
    public Vertex<V> insertVertex(V element) {
        InnerVertex<V> vertex = new InnerVertex<>(element, isDirected);
        vertex.setPos(vertices.addLast(vertex));
        return vertex;
    }

    @Override
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException {
        InnerEdge<E> edge = new InnerEdge<>(element, u, v);
        edge.setPos(edges.addLast(edge));
        InnerVertex<V> origin = validate(u);
        InnerVertex<V> destination = validate(v);
        origin.getOutgoing().put(v, edge);
        destination.getIncoming().put(u, edge);
        return edge;
    }

    @Override
    public void removeVertex(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vertex = validate(v);
        for (Edge<E> edge : vertex.getIncoming().valueSet())
            removeEdge(edge);
        for (Edge<E> edge : vertex.getOutgoing().valueSet())
            removeEdge(edge);
        vertices.remove(vertex.getPos());
        vertex.setPos(null);
    }

    @Override
    public void removeEdge(Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
        InnerVertex<V>[] verts = (InnerVertex<V>[]) edge.getEndpoints();
        verts[0].getOutgoing().remove(verts[1]);
        verts[1].getIncoming().remove(verts[0]);
        edges.remove(edge.getPos());
        edge.setPos(null);
    }
}
