package uk.ac.cf.spring.nhs.Credentials.ProviderCredentials;

import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.*;

@Entity
@DynamicUpdate
@Table (name = "ProviderCredentials")
public class ProviderCredentials {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ProviderID")
    private Long providerId;
    @Column(name = "ProviderName")
    private String providerName;
    @Column(name = "ProviderPassword")
    private String providerPassword;
    @Column(name = "ProviderRole")
    private String providerRole;

    //Getters and setters
    public Long getProviderId(){
        return providerId;
    }
    public String getProviderName(){
        return providerName;
    }
    public void setProviderName(String name){
        this.providerName = name;
    }
    public String getProviderPassword(){
        return providerPassword;
    }
    public void setProviderPassword(String pass){
        this.providerName = pass;
    }
    public String getProviderRole(){
        return providerRole;
    }
    public void setProviderRole(String roles){
        this.providerRole = roles;
    }
}
