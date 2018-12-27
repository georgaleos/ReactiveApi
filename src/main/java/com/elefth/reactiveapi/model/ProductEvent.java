package com.elefth.reactiveapi.model;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author <a href="mailto:george@elefth.com">Eleftheriadis Georgios</a>
 */
public class ProductEvent {

    private Long id;

    private String type;

    public ProductEvent() {
    }

    public ProductEvent(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEvent that = (ProductEvent) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProductEvent.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("type='" + type + "'")
                .toString();
    }
}
