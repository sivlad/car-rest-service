package ua.com.foxmined.carrestservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.foxmined.carrestservice.model.CarMaker;
import ua.com.foxmined.carrestservice.service.makerservice.CarMakerService;
import ua.com.foxmined.carrestservice.utils.EndPoints;

import java.util.List;

@RestController
@RequestMapping(value = EndPoints.VERSION_1)
@SecurityRequirement(name = "bearerAuth")
public class MakerController {

    @Autowired
    private CarMakerService carMakerService;

    @RequestMapping(value = EndPoints.GET_MANUFACTURERS, method = RequestMethod.GET)
    public ResponseEntity<List<CarMaker>> getMaker(@RequestParam(name="page",defaultValue = "0") Integer page,
                                                   @RequestParam(name="pageSize",defaultValue = "10") Integer pagesize) {
        List<CarMaker> carMakers = carMakerService.findAll(PageRequest.of(page,pagesize)).toList();
        return new ResponseEntity<>(carMakers, HttpStatus.OK);
    }

    @RequestMapping(value = EndPoints.SET_MANUFACTURER, method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('create:items')")
    public ResponseEntity<?> addManufacturer(@PathVariable(name = "manufacturer") String manufacturer) {
        carMakerService.addManufacturer(manufacturer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = EndPoints.UPDATE_MANUFACTURER, method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('update:items')")
    public ResponseEntity<?> updateManufacturer(@PathVariable(name = "oldManufacturer") String oldManufacturer,
                                                @PathVariable(name = "newManufacturer") String newManufacturer) {
        carMakerService.updateManufacturer(oldManufacturer,newManufacturer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = EndPoints.SET_MANUFACTURER, method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('delete:items')")
    public ResponseEntity<?> deleteManufacturer(@PathVariable(name = "manufacturer") String manufacturer) {

        carMakerService.deleteManufacturer(manufacturer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
