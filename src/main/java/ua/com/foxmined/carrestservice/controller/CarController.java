package ua.com.foxmined.carrestservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.foxmined.carrestservice.dto.CarDto;
import ua.com.foxmined.carrestservice.model.CarInformation;
import ua.com.foxmined.carrestservice.service.informationservice.CarInformationService;
import ua.com.foxmined.carrestservice.service.makerservice.CarMakerService;
import ua.com.foxmined.carrestservice.service.summaryservice.CarSummaryService;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class CarController {

    @Autowired
    private CarInformationService carInformationService;

    @Autowired
    private CarSummaryService carSummaryService;

    @Autowired
    private CarMakerService carMakerService;

    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    public ResponseEntity<List<CarInformation>> getCars(CarDto sourceCarFilter) {
        List<CarInformation> carInformationResult = carInformationService.findByCarFilters(sourceCarFilter).toList();
        return new ResponseEntity<>(carInformationResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/manufacturers/{manufacturer}", method = RequestMethod.POST)
    public ResponseEntity<?> addManufacturer(@PathVariable(name = "manufacturer") String manufacturer) {

        int resultStatus = carMakerService.addManufacturer(manufacturer);

        if (resultStatus == 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("The manufacturer is present", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/manufacturers/{manufacturer}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteManufacturer(@PathVariable(name = "manufacturer") String manufacturer) {

        int resultStatus = carMakerService.addManufacturer(manufacturer);

        if (resultStatus == 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("The manufacturer is present", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/manufacturers/{manufacturer}/models/{model}", method = RequestMethod.POST)
    public ResponseEntity<?> addManufacturer(@PathVariable(name = "manufacturer") String manufacturer,
                                             @PathVariable(name = "model") String model) {

        int resultStatus = carSummaryService.AddManufacturerAndModel(manufacturer,model);

        if (resultStatus == 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else if (resultStatus == 1) {
            return new ResponseEntity<>("The model is present", HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
