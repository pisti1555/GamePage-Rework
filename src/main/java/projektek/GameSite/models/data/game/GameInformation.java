package projektek.GameSite.models.data.game;

import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class GameInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;
    private String thumbnailUrl;
    private String imageUrl;
    private int maxPlayers;
    private String description;

    public GameInformation() {}

    public GameInformation(
            String name, String url,
            String thumbnailUrl, String imageUrl,
            int maxPlayers, String description
    ) {
        this.name = name;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
        this.imageUrl = imageUrl;
        this.maxPlayers = maxPlayers;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getDescription() {
        return description;
    }
}
