package ua.com.foxmined.carrestservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxmined.carrestservice.model.CarCategory;
import ua.com.foxmined.carrestservice.model.CarInformation;
import ua.com.foxmined.carrestservice.model.CarMaker;
import ua.com.foxmined.carrestservice.service.categoryservice.CarCategoryService;
import ua.com.foxmined.carrestservice.service.informationservice.CarInformationService;
import ua.com.foxmined.carrestservice.service.makerservice.CarMakerService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class CarController {

    @Autowired
    private CarInformationService carInformationService;

    @Autowired
    private CarMakerService carMakerService;

    @Autowired
    private CarCategoryService carCategoryService;

    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    public ResponseEntity<List<CarInformation>> getCars(
                                        @RequestParam(value = "manufacturer", required = false) String manufacturerName,
                                        @RequestParam(value = "minYear", required = false) Integer minYear,
                                        @RequestParam(value = "maxYear", required = false) Integer maxYear,
                                        @RequestParam(value = "category", required = false) String category,
                                        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        if (manufacturerName == null && minYear == null && maxYear == null && category != null) {

            List<CarCategory> categories = carCategoryService.findByName(category);

            Optional<CarCategory> findCarCategory = Optional.ofNullable(categories.get(0));

            if (findCarCategory.isPresent()) {
                List<CarInformation> carInformationResult =
                        carInformationService.findByCarCategoryId(findCarCategory.get().getId(), PageRequest.of(page,pageSize, Sort.by("year"))).toList();
                return new ResponseEntity<>(carInformationResult, HttpStatus.OK);
            }
        }

        List<CarMaker> carMakers = carMakerService.findByName(manufacturerName);

        if (carMakers.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<CarMaker> findCarMaker = Optional.ofNullable(carMakers.get(0));

        List<CarInformation> carInformationResult = new ArrayList<>();
        if (findCarMaker.isPresent()) {
            carInformationResult = carInformationService.findByCarMakerId(findCarMaker.get().getId());
        }

        List<CarInformation> carInformationResultMinYear = new ArrayList<>();

        if (minYear!=null) {

            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            for (var currentCarInformation : carInformationResult) {
                int year = Integer.parseInt(format.format(currentCarInformation.getDateOfManifacture()));
                if (year > minYear) {
                    carInformationResultMinYear.add(currentCarInformation);
                }
            }
            return new ResponseEntity<>(carInformationResultMinYear, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(carInformationResult, HttpStatus.OK);
        }

    }

}
