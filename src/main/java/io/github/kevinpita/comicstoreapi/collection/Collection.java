/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.collection;

import io.github.kevinpita.comicstoreapi.comic.Comic;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(
        name = "collection",
        uniqueConstraints = {@UniqueConstraint(name = "uq_collection_name", columnNames = "name")})
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank private String name;

    @Column(name = "description", length = 512)
    private String description;

    @NotBlank private String publisher;

    @OneToMany(mappedBy = "collection", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comic> comics;
}
