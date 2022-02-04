package com.iaa.paradise_server.Entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.context.annotation.PropertySource;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.iaa.paradise_server.Validation.FieldValueExists;
import com.iaa.paradise_server.Validation.Unique;
import com.iaa.paradise_server.Validation.ValidPassword;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@PropertySource("classpath:validation.properties")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private static final long serialVersionUID = 6529685098267757690L;
    @NotEmpty(message = "{validation.username.NotEmpty}")
    @Size(min = 6, max = 64, message = "{validation.username.Size}")
    @Unique(message = "{validation.username.Unique}", service = FieldValueExists.class, fieldName = "username")
    private String username;
    @Size(min = 3, max = 256, message = "{validation.name.Size}")
    private String name;
    @NotEmpty(message = "{validation.email.NotEmpty}")
    @Email(message = "{validation.email.Type}")
    @Unique(message = "{validation.email.Unique}", service = FieldValueExists.class, fieldName = "email")
    private String email;
    @NotEmpty(message = "{validation.password.NotEmpty}")
    @ValidPassword
    private String password;
    @Past(message = "{validation.birthDate.Past}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
}