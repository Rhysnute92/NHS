package uk.ac.cf.spring.nhs.Provider.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.Provider.Entity.Provider;
import uk.ac.cf.spring.nhs.Provider.Repository.ProviderRepository;

@Service
public class ProviderService {

    @Autowired
    private ProviderRepository providersRepository;

    public Provider getProviderById(Long userId){
        return providersRepository.getReferenceById(userId);
    }

    public String getFullName(Provider provider){
        String fullname = provider.getProviderTitle() + " " + provider.getProviderName() + " " + provider.getProviderLastName();
        return fullname;
    }
}
