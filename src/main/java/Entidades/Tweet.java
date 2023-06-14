package Entidades;

public class Tweet implements Comparable<Tweet>{

    private long id;
    private String content;
    private String  source;
    private boolean isRetweet;
    private String usuarioDelTweet;

    // getters and setters...


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isRetweet() {
        return isRetweet;
    }

    public void setRetweet(boolean retweet) {
        isRetweet = retweet;
    }

    public String getUsuarioDelTweet() {
        return usuarioDelTweet;
    }

    public void setUsuarioDelTweet(String usuarioDelTweet) {
        this.usuarioDelTweet = usuarioDelTweet;
    }

    public Tweet(long id, String usuario) {

        this.id = id;
        this.usuarioDelTweet = usuario;
    }

    @Override
    public int compareTo(Tweet otherTweet) {
        return Long.compare(this.id, otherTweet.id);
    }
}
