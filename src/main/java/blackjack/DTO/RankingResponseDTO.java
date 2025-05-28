package blackjack.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RankingResponseDTO {
  private Long userId;
  private Long wins;
  }

