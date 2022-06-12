/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.collection;

import io.github.kevinpita.comicstoreapi.comic.ComicDto;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
public class CollectionDto implements Serializable {
    private final Long id;
    @NotBlank private final String name;
    private final String description;
    @NotBlank private final String publisher;
    private final List<ComicDto> comics;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String imageUrl;

    public String getImageUrl() {
        return String.format("/images/comics/%d.png", id);
    }
}
