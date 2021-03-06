package prj.serenasimon.datas;

import java.io.Serializable;
import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    final static public String EntityName = "User";

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "agerange")
    private Integer agerange;
    @Column(name = "link")
    private URL link;
    @Column(name = "picture")
    private URL picture;

    public User() {
    }

    public User(Long id, String name, String firstname, String lastname, Integer agerange, URL link, URL picture) {
        super();
        this.id = id;
        this.name = name;
        this.firstname = firstname;
        this.lastname = lastname;
        this.agerange = agerange;
        this.link = link;
        this.picture = picture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getAgerange() {
        return agerange;
    }

    public void setAgerange(Integer agerange) {
        this.agerange = agerange;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public URL getPicture() {
        return picture;
    }

    public void setPicture(URL picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object user) {
        if (user == null)
            return false;
        if (user == this)
            return true;
        if (!(user instanceof User))
            return false;
        User inputUser = (User) user;
        return inputUser.getId() == getId()
            && inputUser.getName() == getName()
            && inputUser.getFirstname() == getFirstname()
            && inputUser.getLastname() == getLastname()
            && inputUser.getAgerange() == getAgerange()
            && inputUser.getPicture() == getPicture()
            && inputUser.getLink() == getLink();
    }
}
