/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comiccopy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.github.kevinpita.comicstoreapi.comic.Comic;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "comic_copy")
public class ComicCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate purchaseDate;

    private String state;

    private String cover;

    private BigDecimal price;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comic_id", foreignKey = @ForeignKey(name = "fk_comic_copy_comic"))
    private Comic comic;
}
