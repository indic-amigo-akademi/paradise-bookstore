package iaa.paradise.paradise_server.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import iaa.paradise.paradise_server.enums.UserRole;
import java.util.Date;

@Document(collection = "users")
public class User {
    @Id
    private long id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String password;
    private UserRole role;
    private Date dob;
    private Date createdTs;
    private Date updatedTs;

    public User() {
    }

    public User(String name, String username, String email, String phone, String password, Date dob) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.dob = dob;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getCreatedTs() {
        return createdTs;
    }

    public Date getUpdatedTs() {
        return updatedTs;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", phone=" + phone
                + ", password=" + password + ", role=" + role + ", dob=" + dob + ", createdTs=" + createdTs
                + ", updatedTs=" + updatedTs + "]";
    }

}
