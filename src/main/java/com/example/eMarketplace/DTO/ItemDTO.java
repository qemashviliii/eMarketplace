package com.example.eMarketplace.DTO;

import java.time.LocalDateTime;

public record ItemDTO(

        Long id,
        String name,
        Long price,
        String description,
        LocalDateTime submissionTime,
        String photoUrl,
        String username

) {
}
