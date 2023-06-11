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

    private String favoritos;


    public long getId() {
        return id;
    }

    public String getVerificado() {
        return verificado;
    }

    public String getName() {
        return name;
    }

    public String getFavoritos() {
        return favoritos;
    }

    public User(String name, String favoritos, String verificado) {
        this.name = name;
        this.verificado = verificado;
        this.favoritos = favoritos;
    }

    @Override
    public int compareTo(User o) {
        return this.name.compareTo(o.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name);
    }
}
