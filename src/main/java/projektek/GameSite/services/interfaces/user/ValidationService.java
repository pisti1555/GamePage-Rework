package projektek.GameSite.services.interfaces.user;

import projektek.GameSite.dtos.auth.RegistrationDto;

public interface ValidationService {
    boolean validateRegistration(RegistrationDto dto);
}
