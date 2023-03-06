package ua.com.foxmined.carrestservice.service.summaryservice;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.exception.EntityNotPresentException;
import ua.com.foxmined.carrestservice.exception.EntityPresentException;
import ua.com.foxmined.carrestservice.model.CarInformation;
import ua.com.foxmined.carrestservice.model.CarMaker;
import ua.com.foxmined.carrestservice.model.CarModel;
import ua.com.foxmined.carrestservice.service.informationservice.CarInformationService;
import ua.com.foxmined.carrestservice.service.makerservice.CarMakerService;
import ua.com.foxmined.carrestservice.service.modelservice.CarModelService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Log4j2
@Service
public class CarSummaryServiceImpl implements CarSummaryService{

    @Autowired
    private CarMakerService carMakerService;

    @Autowired
    private CarModelService carModelService;

    @Autowired
    private CarInformationService carInformationService;

    @Override
    public CarModel addManufacturerAndModel(String manufacturer, String model) {

        CarMaker carMaker = carMakerService.addManufacturer(manufacturer);
        List<CarModel> findCarModels = carModelService.findByNameAndCarMakerLike(model,carMaker.getId());

        CarModel addCarModel = new CarModel();
        addCarModel.setCarMaker(carMaker);
        addCarModel.setName(model);

        if (findCarModels.size() == 0) {
            return carModelService.save(addCarModel);
        }

        Optional<CarModel> findCarModel = Optional.ofNullable(findCarModels.get(0));
        if (findCarModel.isEmpty()) {
            carModelService.save(addCarModel);
            return carModelService.save(addCarModel);
        }
        else {
            return findCarModel.get();
        }
    }

    @Override
    public void deleteManufacturerAndModel(String manufacturer, String model) {
        CarMaker carMaker = carMakerService.addManufacturer(manufacturer);
        List<CarModel> findCarModels = carModelService.findByNameAndCarMakerLike(model,carMaker.getId());

        Optional<CarModel> findCarModel = Optional.ofNullable(findCarModels.get(0));
        if (findCarModel.isPresent()) {
            carModelService.delete(findCarModel.get());
        }

    }

    @Override
    public void updateModelCurrentManufacturer(String manufacturer, String oldModel, String newModel) {
        List<CarMaker> carMakers = carMakerService.findByName(manufacturer);

        if (carMakers.size() == 0) {
            return;
        }

        Optional<CarMaker> findCarMaker = Optional.ofNullable(carMakers.get(0));
        if (findCarMaker.isEmpty()) {
            return;
        }
        CarMaker carMaker = findCarMaker.get();
        List<CarModel> findCarModels = carModelService.findByNameAndCarMakerLike(oldModel,carMaker.getId());

        Optional<CarModel> findCarModel = Optional.ofNullable(findCarModels.get(0));
        if (findCarModel.isPresent()) {
            CarModel updateCarModel = findCarModel.get();
            updateCarModel.setName(newModel);
            carModelService.save(updateCarModel);
        }
    }

    @Override
    public CarInformation addManufacturerAndModelAndYear(String manufacturer, String model, String year) throws EntityNotPresentException {

        CarMaker carMaker = carMakerService.addManufacturer(manufacturer);

        List<CarModel> findCarModels = carModelService.findByNameAndCarMakerLike(model,carMaker.getId());

        CarModel addCarModel = new CarModel();
        addCarModel.setCarMaker(carMaker);
        addCarModel.setName(model);

        if (findCarModels.size() == 0) {
            throw new EntityNotPresentException("There is not current model");
        }

        Optional<CarModel> findCarModel = Optional.ofNullable(findCarModels.get(0));
        if (findCarModel.isEmpty()) {
            throw new EntityNotPresentException("There is not current model");
        }
        else {
            addCarModel = findCarModel.get();
        }

        CarInformation addCarInformation = new CarInformation();
        addCarInformation.setCarModel(addCarModel);
        try {
            addCarInformation.setDateOfManifacture(new SimpleDateFormat("yyyy").parse(year));
        } catch (ParseException e) {
            log.debug("invalid Date format");
        }
        Random randomGenerator = new Random();
        addCarInformation.setObjectId(String.valueOf(randomGenerator.nextInt(1000000)));

        return carInformationService.save(addCarInformation);
    }

}
