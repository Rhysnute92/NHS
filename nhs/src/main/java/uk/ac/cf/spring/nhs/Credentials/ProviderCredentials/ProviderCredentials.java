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
    private int providerId;
    @Column(name = "ProviderName")
    private String providerName;
    @Column(name = "ProviderPassword")
    private String providerPassword;

    //Getters and setters
    public int getProviderId(){
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
}
