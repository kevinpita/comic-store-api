/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comiccreator;

import io.github.kevinpita.comicstoreapi.creator.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ComicCreatorRepository extends JpaRepository<ComicCreator, Long> {
    @Query("select count(DISTINCT c.comic) from ComicCreator c where c.creator = ?1")
    int countByCreator(Creator creator);
}
