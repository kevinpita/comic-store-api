/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.creator;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatorDto implements Serializable {
    private final Long id;
    private final String fullName;
}
