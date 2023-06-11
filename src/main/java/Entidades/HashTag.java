package Entidades;

import java.util.Objects;

public class HashTag {

    private long id;

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashTag hashTag = (HashTag) o;
        return Objects.equals(text, hashTag.text);
    }

    public HashTag() {}
    public HashTag(String text) {
        this.text = text;
    }
}