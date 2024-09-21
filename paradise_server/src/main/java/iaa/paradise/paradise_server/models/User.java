package iaa.paradise.paradise_server.models;

import org.springframework.context.annotation.PropertySource;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import iaa.paradise.paradise_server.enums.UserRole;
import iaa.paradise.paradise_server.utils.validation.FieldValueExists;
import iaa.paradise.paradise_server.utils.validation.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "users")
@PropertySource("classpath:validation.properties")
@JsonIgnoreProperties({ "id", "createdTs", "updatedTs" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private long id;

    private String name;

    @NotBlank(message = "${validation.username.NotBlank}")
    @Size(min = 3, max = 20, message = "${validation.username.Size}")
    @Unique(message = "${validation.username.Unique}", service = FieldValueExists.class, fieldName = "username")
    private String username;

    @NotBlank(message = "${validation.email.NotBlank}")
    @Email(message = "${validation.email.Email}")
    private String email;

    @NotBlank(message = "${validation.phone.NotBlank}")
    private String phone;

    @NotBlank(message = "${validation.password.NotBlank}")
    @Size(min = 8, message = "${validation.password.Size}")
    private String password;

    private UserRole role;

    @NotNull(message = "${validation.dob.NotNull}")
    @Past(message = "${validation.dob.Past}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
