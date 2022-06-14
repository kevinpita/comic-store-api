/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.creator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CreatorRepository extends JpaRepository<Creator, Long> {

    @Query(
            "select count(c) from Creator c where c.name = ?1 and (c.lastName = ?2 or c.lastName"
                    + " like CONCAT(?2, ' (%)'))")
    int countByNameAndLastName(String name, String lastName);
}
