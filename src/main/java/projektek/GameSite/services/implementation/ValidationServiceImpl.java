package projektek.GameSite.services.implementation;

import org.springframework.stereotype.Service;
import projektek.GameSite.dtos.auth.RegistrationDto;
import projektek.GameSite.services.interfaces.ValidationService;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Override
    public boolean validateRegistration(RegistrationDto dto) {
        return false;
    }
}
