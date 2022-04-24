/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComicRepository extends JpaRepository<Comic, Long> {}
