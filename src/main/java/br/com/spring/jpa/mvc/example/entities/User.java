package br.com.spring.jpa.mvc.example.entities;

import javax.persistence.*;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Entity
@Table(name = "USER")
@NamedQueries({
        @NamedQuery(name = User.FIND_BY_USERNAME_AND_PASSWORD, query = "FROM User u where u.username = :username and u.password = :password")
})
public class User {
    public static final String FIND_BY_USERNAME_AND_PASSWORD = "FIND_BY_USERNAME_AND_PASSWORD";

    @Id
    @Column(name = "IDT_USER")
    @GeneratedValue
    private Long id;

    @Column(name = "DES_USERNAME")
    private String username;

    @Column(name = "DES_PASSWORD")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
