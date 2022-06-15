/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    @Query("SELECT count(c)>0 FROM Collection c WHERE c.name = ?1")
    boolean existsByName(String name);
}
