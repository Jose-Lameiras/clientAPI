package humanit.clientAPI.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentRequestDTO {
    private String number;
    private String description;
    private java.time.LocalDate expirationDate;
}
