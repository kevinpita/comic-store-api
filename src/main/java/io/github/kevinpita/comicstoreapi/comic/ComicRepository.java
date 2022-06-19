/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ComicRepository extends JpaRepository<Comic, Long> {
    @Query("SELECT count(c)>0 FROM Comic c WHERE c.issueNumber = ?1 and c.collection.id = ?2")
    boolean issueNumberExists(int issueNumber, Long id);
}
