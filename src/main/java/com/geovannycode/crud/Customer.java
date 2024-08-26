package com.geovannycode.crud;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Setter
@Getter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "This field can not be blank")
    @Size(message = "The size must be between 1 and 50", min = 1, max = 50)
    private String firstName;

    @JsonIgnore
    @NotNull(message = "This field can not be blank")
    @Size(message = "The size must be between 1 and 50", min = 1, max = 50)
    private String lastName;

    @Size(message = "The maximum size is 50", max = 50)
    private String email;

    @NotNull(message = "This field can not be blank")
    @Size(message = "The size must be between 1 and 50", min = 1, max = 50)
    private String numberPhone;

    @NotNull(message = "This field can not be blank.")
    private LocalDate dateOfBirth;

    @Size(message = "The maximum size is 100", max = 100)
    private String profession;

    @Size(message = "The maximum size is 100", max = 100)
    private String gender;

    private String pictureUrl;

    private boolean status;
}
