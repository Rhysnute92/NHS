package uk.ac.cf.spring.nhs.Provider.Entity;

import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.*;

@Entity
@DynamicUpdate
@Table(name = "Providers")
public class Provider {
    @Id
    @Column(name = "UserID")
    private Long userId;
    @Column(name = "ProviderFirstName")
    private String providerName;
    @Column(name = "ProviderLastName")
    private String providerLastName;
    @Column(name = "ProviderTitle")
    private String providerTitle;
    @Column(name = "ProviderOccupation")
    private String providerOccupation;

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getProviderName() {
        return providerName;
    }
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
    public String getProviderLastName() {
        return providerLastName;
    }
    public void setProviderLastName(String providerLastName) {
        this.providerLastName = providerLastName;
    }
    public String getProviderTitle() {
        return providerTitle;
    }
    public void setProviderTitle(String providerTitle) {
        this.providerTitle = providerTitle;
    }
    public String getProviderOccupation() {
        return providerOccupation;
    }
    public void setProviderOccupation(String providerOccupation) {
        this.providerOccupation = providerOccupation;
    }
}
