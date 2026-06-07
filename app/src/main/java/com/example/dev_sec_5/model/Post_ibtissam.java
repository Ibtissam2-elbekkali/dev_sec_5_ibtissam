package com.example.dev_sec_5.model;

public class Post_ibtissam {
    private int id_ibtissam;
    private int userId_ibtissam;
    private String title_ibtissam;
    private String body_ibtissam;

    public int getId_ibtissam() { return id_ibtissam; }
    public void setId_ibtissam(int id_ibtissam) { this.id_ibtissam = id_ibtissam; }

    public int getUserId_ibtissam() { return userId_ibtissam; }
    public void setUserId_ibtissam(int userId_ibtissam) { this.userId_ibtissam = userId_ibtissam; }

    public String getTitle_ibtissam() { return title_ibtissam; }
    public void setTitle_ibtissam(String title_ibtissam) { this.title_ibtissam = title_ibtissam; }

    public String getBody_ibtissam() { return body_ibtissam; }
    public void setBody_ibtissam(String body_ibtissam) { this.body_ibtissam = body_ibtissam; }

    @Override
    public String toString() {
        return "Post_ibtissam{" +
                "id=" + id_ibtissam +
                ", userId=" + userId_ibtissam +
                ", title='" + title_ibtissam + '\'' +
                ", body='" + body_ibtissam + '\'' +
                '}';
    }
}