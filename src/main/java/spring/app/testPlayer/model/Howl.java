package spring.app.testPlayer.model;

public class Howl {
    private String title;
    private String file;
    private String howl;

    public Howl() {
    }

    public Howl(String title, String file, String howl) {
        this.title = title;
        this.file = file;
        this.howl = howl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getHowl() {
        return howl;
    }

    public void setHowl(String howl) {
        this.howl = howl;
    }

    @Override
    public String toString() {
        return "Howl{" +
                "title='" + title + '\'' +
                ", file='" + file + '\'' +
                ", howl='" + howl + '\'' +
                '}';
    }
}
