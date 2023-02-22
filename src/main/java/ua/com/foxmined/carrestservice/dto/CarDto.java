package ua.com.foxmined.carrestservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDto {

    private String manufacturer;
    private Long minYear;
    private Long maxYear;
    private String category;
    private String model;
    private Integer page = 0;
    private Integer pageSize = 10;

}
