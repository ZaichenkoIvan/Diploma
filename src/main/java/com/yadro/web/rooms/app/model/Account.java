package com.yadro.web.rooms.app.model;

import lombok.*;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Account {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @NotNull
    @Size(min = 3, max = 100, message = "Username must have at least 3 characters.")
    private String userName;

    @NotNull
    @Size(min = 3, max = 100, message = "Password must have at least 3 characters.")
    private String password;

    @Transient
    private String confirmPassword;

    @Email(message = "Email address is not valid.")
    @NotNull
    private String email;

    private String token;

    private String role = "ROLE_USER";

    private String firstName;

    private String lastName;

    private String address;

    private String groupName;

    private String lastLogin;

    private int countEvent = 0;

    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    private University university;

    public Boolean isAdmin() {
        return this.role.equals("ROLE_ADMIN");
    }

    public void addCountEvent() {
        countEvent++;
    }

    public Boolean isMatchingPasswords() {
        return this.password.equals(this.confirmPassword);
    }
}
