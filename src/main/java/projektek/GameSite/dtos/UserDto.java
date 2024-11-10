package projektek.GameSite.dtos;

public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
    private String description;
    private FitwStatsDto fitwStats;
    private TicTacToeStatsDto ticTacToeStats;

    public UserDto() {
    }

    public UserDto(Long id, String username, String email, String firstName, String lastName, String avatar, String description) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FitwStatsDto getFitwStats() {
        return fitwStats;
    }

    public void setFitwStats(FitwStatsDto fitwStats) {
        this.fitwStats = fitwStats;
    }

    public TicTacToeStatsDto getTicTacToeStats() {
        return ticTacToeStats;
    }

    public void setTicTacToeStats(TicTacToeStatsDto ticTacToeStats) {
        this.ticTacToeStats = ticTacToeStats;
    }
}
