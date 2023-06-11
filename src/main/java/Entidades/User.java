package Entidades;


import TADs.Lista.*;

public class User implements Comparable<User> {

    LL<Tweet> listaTweets = new LL<>();

    public LL<Tweet> getListaTweets() {
        return listaTweets;
    }


    private long id;

    private String name;

    private String verificado;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVerificado() {
        return verificado;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(User o) {
        return this.name.compareTo(o.name);
    }

    public User(String text, String verificado) {
        this.name = text;
        this.verificado = verificado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name);
    }


}
