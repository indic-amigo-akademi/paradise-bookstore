package com.iaa.paradise_server.Entity;

import java.io.Serializable;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private String Id;
        private String userName;
        private static final long serialVersionUID = 6529685098267757690L;
        private String name;
        private String email;
        private String password;
        private String dob;
}