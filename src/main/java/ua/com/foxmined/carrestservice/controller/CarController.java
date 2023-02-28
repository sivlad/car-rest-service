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
import ua.com.foxmined.carrestservice.utils.EndPoints;

import java.util.*;

@RestController
@RequestMapping(value = EndPoints.VERSION_1)
public class CarController {

    @Autowired
    private CarInformationService carInformationService;

    @Autowired
    private CarSummaryService carSummaryService;

    @Autowired
    private CarMakerService carMakerService;

    @RequestMapping(value = EndPoints.GET_CARS, method = RequestMethod.GET)
    public ResponseEntity<List<CarInformation>> getCars(CarDto sourceCarFilter) {
        List<CarInformation> carInformationResult = carInformationService.findByCarFilters(sourceCarFilter).toList();
        return new ResponseEntity<>(carInformationResult, HttpStatus.OK);
    }

    @RequestMapping(value = EndPoints.SET_MANUFACTURER, method = RequestMethod.POST)
    public ResponseEntity<?> addManufacturer(@PathVariable(name = "manufacturer") String manufacturer) {
            carMakerService.addManufacturer(manufacturer);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = EndPoints.SET_MANUFACTURER, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteManufacturer(@PathVariable(name = "manufacturer") String manufacturer) {

        carMakerService.deleteManufacturer(manufacturer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = EndPoints.SET_MANUFACTURER_AND_MODEL, method = RequestMethod.POST)
    public ResponseEntity<?> addManufacturerAndModel(@PathVariable(name = "manufacturer") String manufacturer,
                                             @PathVariable(name = "model") String model) {
        carSummaryService.addManufacturerAndModel(manufacturer,model);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = EndPoints.SET_MANUFACTURER_AND_MODEL, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteManufacturerAndModel(@PathVariable(name = "manufacturer") String manufacturer,
                                                     @PathVariable(name = "model") String model) {
        carSummaryService.deleteManufacturerAndModel(manufacturer,model);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = EndPoints.UPDATE_MANUFACTURER, method = RequestMethod.PUT)
    public ResponseEntity<?> updateManufacturer(@PathVariable(name = "oldManufacturer") String oldManufacturer,
                                                        @PathVariable(name = "newManufacturer") String newManufacturer) {
        carMakerService.updateManufacturer(oldManufacturer,newManufacturer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = EndPoints.UPDATE_MODEL, method = RequestMethod.PUT)
    public ResponseEntity<?> updateModel(@PathVariable(name = "manufacturer") String manufacturer,
                                        @PathVariable(name = "oldModel") String oldModel,
                                        @PathVariable(name = "newModel") String newModel) {

        carSummaryService.updateModelCurrentManufacturer(manufacturer,oldModel,newModel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
