/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comiccreator;

import io.github.kevinpita.comicstoreapi.creator.CreatorDto;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComicCreatorDto implements Serializable {
    private final Long id;
    private final CreatorDto creator;
    private final String role;
}
