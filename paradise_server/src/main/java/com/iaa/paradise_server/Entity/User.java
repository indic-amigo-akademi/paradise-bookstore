package com.iaa.paradise_server.Entity;

import java.io.Serializable;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
//No @Entity concept here
@Getter
@Setter
@ToString
public class User implements Serializable {


        private Integer userId;
        private String userName;

        public Integer getUserId() {
                return userId;
        }

        public void setUserId(Integer userId) {
                this.userId = userId;
        }

        public String getUserName() {
                return userName;
        }

        public void setUserName(String userName) {
                this.userName = userName;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        private String email;
        private String password;


}