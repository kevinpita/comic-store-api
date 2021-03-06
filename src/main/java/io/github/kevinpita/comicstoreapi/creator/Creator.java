/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.creator;

import io.github.kevinpita.comicstoreapi.comiccreator.ComicCreator;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "creator")
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ComicCreator> comicCreators;
}
