package projektek.GameSite.dtos.auth;

public class RegistrationDto {
    private final String username;
    private final String password;
    private final String passwordConfirm;
    private final String email;
    private final String firstName;
    private final String lastName;

    public RegistrationDto(String username, String password, String passwordConfirm, String email, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getPasswordConfirm() {
        return passwordConfirm;
    }
    public String getEmail() {
        return email;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
}
