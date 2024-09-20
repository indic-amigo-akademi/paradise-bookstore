package iaa.paradise.paradise_server.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import iaa.paradise.paradise_server.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "users")
public class User implements Serializable {
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";
    
    @Id
    private long id;

    private String name;

    @NotBlank(message = "Username is mandatory.")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters.")
    private String username;

    @NotBlank(message = "Email is mandatory.")
    @Email(message = "Email should be valid.")
    private String email;

    @NotBlank(message = "Phone is mandatory.")
    private String phone;

    @Size(min = 8, message = "Password must be at least 8 characters.")
    private String password;

    private UserRole role;

    @NotNull(message = "Date of birth is mandatory.")
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

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserRole getRole() {
        return role;
    }

    public User setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public Date getDob() {
        return dob;
    }

    public User setDob(Date dob) {
        this.dob = dob;
        return this;
    }

    public User setCreatedTs(Date createdTs) {
        this.createdTs = createdTs;
        return this;
    }

    public User setUpdatedTs(Date updatedTs) {
        this.updatedTs = updatedTs;
        return this;
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
