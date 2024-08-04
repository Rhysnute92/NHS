package uk.ac.cf.spring.nhs.Credentials.PatientCredentials;

import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.*;

@Entity
@DynamicUpdate
@Table (name = "PatientCredentials")
public class PatientCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UserID")
    private Long userId;
    @Column(name = "UserName")
    private String userName;
    @Column(name = "UserPassword")
    private String userPassword;
    @Column(name = "UserRole")
    private String userRole;

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
        this.userName = pass;
    }
    public String getUserRole(){
        return userRole;
    }
    public void setUserRole(String roles){
        this.userRole = roles;
    }
}
