package com.example.nagoyameshi.form;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationRegisterForm {
	@NotNull
    private Integer restaurantId;

    @NotNull
    private Integer userId;

    @NotNull
    private LocalDate reservationDate;

    @NotNull
    private Integer reservationHour;

    @NotNull
    private Integer reservationMinute;

    @NotNull
    private Integer numberOfPeople;
    
    

    // Getter and Setter methods

    public LocalTime getReservationTime() {
        return LocalTime.of(reservationHour, reservationMinute);
    }

	
}
