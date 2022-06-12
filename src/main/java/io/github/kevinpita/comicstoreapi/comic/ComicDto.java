/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comic;

import io.github.kevinpita.comicstoreapi.comiccreator.ComicCreatorDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
public class ComicDto implements Serializable {
    private final Long id;
    private final String title;
    private final String description;
    private final int issueNumber;
    private final CollectionDto collection;
    private final List<ComicCreatorDto> comicCreators;
    private final List<ComicCopyDto> copies;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String imageUrl;

    public String getImageUrl() {
        return String.format("/images/comics/%d.png", id);
    }

    @Data
    @Builder
    public static class CollectionDto implements Serializable {
        private final Long id;
        @NotBlank private final String name;
    }

    @Data
    @Builder
    public static class ComicCopyDto implements Serializable {
        private final Long id;
        private final LocalDate purchaseDate;
        private final String state;
        private final BigDecimal price;
    }
}
