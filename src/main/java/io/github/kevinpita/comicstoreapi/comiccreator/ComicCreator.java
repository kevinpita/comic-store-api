/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comiccreator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.github.kevinpita.comicstoreapi.comic.Comic;
import io.github.kevinpita.comicstoreapi.creator.Creator;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "comic_creator",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uq_comic_creator_role_creator_comic",
                    columnNames = {"role", "creator_id", "comic_id"})
        })
public class ComicCreator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comic_id", foreignKey = @ForeignKey(name = "fk_comic_creator_comic_id"))
    private Comic comic;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", foreignKey = @ForeignKey(name = "fk_comic_creator_creator_id"))
    private Creator creator;

    private String role;
}
