public class Clause {
    private String title;
    private String content;

    public Clause(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Clause(Clause other) {
        this.title = other.title;
        this.content = other.content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Clause{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
