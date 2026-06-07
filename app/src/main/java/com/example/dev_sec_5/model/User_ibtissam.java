package com.example.dev_sec_5.model;

public class User_ibtissam {
    private int id_ibtissam;
    private String name_ibtissam;
    private String email_ibtissam;

    public int getId_ibtissam() { return id_ibtissam; }
    public void setId_ibtissam(int id_ibtissam) { this.id_ibtissam = id_ibtissam; }

    public String getName_ibtissam() { return name_ibtissam; }
    public void setName_ibtissam(String name_ibtissam) { this.name_ibtissam = name_ibtissam; }

    public String getEmail_ibtissam() { return email_ibtissam; }
    public void setEmail_ibtissam(String email_ibtissam) { this.email_ibtissam = email_ibtissam; }

    @Override
    public String toString() {
        return "User_ibtissam{" +
                "id=" + id_ibtissam +
                ", name='" + name_ibtissam + '\'' +
                ", email='" + email_ibtissam + '\'' +
                '}';
    }
}