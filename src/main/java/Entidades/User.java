package Entidades;


import TADs.Lista.*;

public class User implements Comparable<User> {

    LL<Tweet> listaTweets = new LL<>();

    public LL<Tweet> getListaTweets() {
        return listaTweets;
    }


    private long id;

    private String name;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(User o) {
        return 0;
    }

    public User(long id, String text) {
        this.id = id;
        this.name = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }


}
