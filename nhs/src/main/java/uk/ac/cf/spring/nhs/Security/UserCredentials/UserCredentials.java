package uk.ac.cf.spring.nhs.Security.UserCredentials;

import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.*;

@Entity
@DynamicUpdate
@Table (name = "UserCredentials")
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Long userId;
    @Column(name = "UserName")
    private String userName;
    @Column(name = "UserPassword")
    private String userPassword;
    @Column(name = "UserRole")
    private String userRole;
    @Column(name = "PasswordSetupToken")
    private String passwordSetupToken;

    //Getters and setters
    public Long getUserId(){
        return userId;
    }
    public String getUserName(){
        return userName;
    }
    public void setUserName(String name){
        this.userName = name;
    }
    public String getUserPassword(){
        return userPassword;
    }
    public void setUserPassword(String pass){
        this.userPassword = pass;
    }
    public String getUserRole(){
        return userRole;
    }
    public void setUserRole(String roles){
        this.userRole = roles;
    }
    public String getPasswordSetupToken() {return passwordSetupToken;}

    public void setPasswordSetupToken(String passwordSetupToken) {this.passwordSetupToken = passwordSetupToken;}
}
