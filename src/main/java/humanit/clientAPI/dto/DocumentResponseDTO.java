package humanit.clientAPI.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentResponseDTO {
    private Long id;
    private String number;
    private String description;
    private LocalDate expirationDate;
}
