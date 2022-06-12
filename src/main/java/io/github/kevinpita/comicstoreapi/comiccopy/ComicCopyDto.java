/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comiccopy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComicCopyDto implements Serializable {
    private final Long id;
    private final LocalDate purchaseDate;
    private final String state;
    private final BigDecimal price;
    private final ComicDto comic;

    @Data
    @Builder
    public static class ComicDto implements Serializable {
        private final Long id;
        private final String title;
        private final String description;
        private final int issueNumber;
    }
}
