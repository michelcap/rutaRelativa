package Entidades;

public class User implements Comparable<User> {
    private long id;

    private String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int compareTo(User o) {
        return 0;
    }
}
