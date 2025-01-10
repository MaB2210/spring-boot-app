package io.reactivestax.spring_boot_app.dto;

import java.time.LocalDate;

import io.reactivestax.spring_boot_app.validation.ValidDateRange;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@ValidDateRange
@Data
public class EventDTO {

    @NotNull(message = "Start date must not be null")
    private LocalDate startDate;

    @NotNull(message = "End date must not be null")
    private LocalDate endDate;
  
}
