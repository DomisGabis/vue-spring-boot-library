package pl.edu.pwr.ztw.books.dto;

public class BookDTO {
    private Long id;
    private String title;
    private Long authorId;
    private String authorFirstName;
    private String authorLastName;
    private int pages;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Long getAuthorId() {
        return authorId;
    }
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
    public String getAuthorFirstName() {
        return authorFirstName;
    }
    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }
    public String getAuthorLastName() {
        return authorLastName;
    }
    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }
    public int getPages() {
        return pages;
    }
    public void setPages(int pages) {
        this.pages = pages;
    }
}

