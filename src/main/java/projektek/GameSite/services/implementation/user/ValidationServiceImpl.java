package projektek.GameSite.services.implementation.user;

import org.springframework.stereotype.Service;
import projektek.GameSite.dtos.auth.RegistrationDto;
import projektek.GameSite.services.interfaces.user.ValidationService;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Override
    public boolean validateRegistration(RegistrationDto dto) {
        return false;
    }
}
