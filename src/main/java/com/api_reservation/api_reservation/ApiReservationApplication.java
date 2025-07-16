package com.api_reservation.api_reservation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@OpenAPIDefinition(
		info = @Info(title = "Reservation API", version = "v1", description = "Reservation operations")
)
@SpringBootApplication
@EnableAspectJAutoProxy
public class ApiReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiReservationApplication.class, args);
	}

}
