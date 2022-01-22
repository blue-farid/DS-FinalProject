package model;

import model.graph.Node;

import java.util.Objects;

public class Person {
    private Node node;

    public Person(Node node) {
        this.node = node;
    }
    public Node getNode() {
        return node;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(node, person.node);
    }

    @Override
    public String toString() {
        return node.toString();
    }
}
