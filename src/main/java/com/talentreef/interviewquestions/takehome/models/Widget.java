package com.talentreef.interviewquestions.takehome.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Table(
        name = "widgets",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"})
)
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder(toBuilder = true)
public class Widget {
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Name is required.")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters.")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Description is required.")
    @Size(min = 5, max = 1000, message = "Description must be between 5 and 1000 characters.")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Price is required.")
    @DecimalMin(value = "1.00", message = "Price must be at least 1.")
    @DecimalMax(value = "20000.00", message = "Price must not exceed 20000.")
    private Double price;
}