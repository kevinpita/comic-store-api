/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comic;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.github.kevinpita.comicstoreapi.collection.Collection;
import io.github.kevinpita.comicstoreapi.comiccopy.ComicCopy;
import io.github.kevinpita.comicstoreapi.comiccreator.ComicCreator;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "comic",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uq_comic_collection_issue_number",
                    columnNames = {"collection_id", "issue_number"})
        })
public class Comic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(name = "issue_number")
    private int issueNumber;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id", foreignKey = @ForeignKey(name = "fk_comic_collection"))
    private Collection collection;

    @OneToMany(mappedBy = "comic", fetch = FetchType.LAZY)
    private List<ComicCreator> comicCreators;

    @OneToMany(mappedBy = "comic", fetch = FetchType.LAZY)
    private List<ComicCopy> copies;
}
