package by.bsuir.publisherservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatorRequestTo(
        Long id,
        @NotNull @Size(min = 2, max = 64)
        String login,
        @NotNull @Size(min = 8, max = 128)
        String password,
        @NotNull @Size(min = 2, max = 64)
        String firstname,
        @NotNull @Size(min = 2, max = 64)
        String lastname
) {
}
