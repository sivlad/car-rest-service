package ua.com.foxmined.carrestservice.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.exception.FileException;
import ua.com.foxmined.carrestservice.model.*;
import ua.com.foxmined.carrestservice.service.categoryservice.CarCategoryService;
import ua.com.foxmined.carrestservice.service.informationservice.CarInformationService;
import ua.com.foxmined.carrestservice.service.makerservice.CarMakerService;
import ua.com.foxmined.carrestservice.service.modelcategoryservice.CarModelCategoryService;
import ua.com.foxmined.carrestservice.service.modelservice.CarModelService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Service
public class CarDBInitializer {

    @Value("${data.carsource}")
    private String carsource;

    @Autowired
    private CarCategoryService carCategoryService;

    @Autowired
    private CarInformationService carInformationService;

    @Autowired
    private CarMakerService carMakerService;

    @Autowired
    private CarModelService carModelService;

    @Autowired
    private CarModelCategoryService carModelCategoryService;

    public void deleteAllRowsInDB() {
        carCategoryService.deleteAll();
        carInformationService.deleteAll();
        carMakerService.deleteAll();
        carModelService.deleteAll();
        log.debug("All rows in DB was deleted");
    }

    public void createRowsInDb() throws FileException {

        Page<CarInformation> testCarInformation = carInformationService.findAll(PageRequest.of(0,10));
        if (testCarInformation.toList().size() != 0) {
            log.info("DB initialize complete");
            return;
        }

        int firstRow = 0;

        try (Stream<String> lineStream = Files.lines(Paths.get(carsource))) {
            for (var currentString : lineStream.collect(Collectors.toList())) {

                if (firstRow==0) {
                    firstRow= 1;
                    continue;
                }
                String fragments[] = currentString.split(",");

                if (fragments.length < 5) {
                    continue;
                }

                CarMaker findCarMaker = resiveMaker(fragments[1]);

                CarModel findModel;

                Optional<CarModel> optionalFindModel = resiveModel(fragments[3]);
                List<CarCategory> addCategories;

                if (optionalFindModel.isPresent()) {
                    findModel = optionalFindModel.get();
                }
                else {
                    CarModel addCarModel = new CarModel();
                    addCarModel.setName(fragments[3]);
                    addCarModel.setCarMaker(findCarMaker);
                    carModelService.save(addCarModel);
                    findModel = carModelService.findByName(fragments[3]).get(0);
                }

                if (fragments.length == 5) {
                    addCategories = createListCategory(fragments[4]);
                }
                else {
                    String fragmentsCategory[] = currentString.split("\"");
                    String categoryString = fragmentsCategory[1];
                    addCategories = createListCategory(categoryString);
                }

                if (optionalFindModel.isEmpty()) {
                    SaveCategoriesToModel(findModel, addCategories);
                }

                SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy");

                CarInformation addCarInformation = new CarInformation();
                addCarInformation.setCarModel(findModel);
                addCarInformation.setDateOfManifacture(dateFormater.parse(fragments[2]));
                addCarInformation.setObjectId(fragments[0]);

                carInformationService.save(addCarInformation);
            }
            log.info("DB initialize complete");
        } catch (IOException exception) {
            log.error("Error open source courses file");
            throw new FileException("Error with source courses file");
        } catch (ParseException e) {
            log.error("Invalid Date format");
        }
    }

    private void SaveCategoriesToModel(CarModel carModel, List<CarCategory> categories) {
        for (var currentCategory : categories) {
            CarModelCategory addCategoryToModel = new CarModelCategory();
            addCategoryToModel.setCarModel(carModel);
            addCategoryToModel.setCarCategory(currentCategory);
            carModelCategoryService.save(addCategoryToModel);
        }
    }

    private List<CarCategory> createListCategory(String categories) {

        String fragments[] = categories.split(",");

        List<CarCategory> returnCategories = new ArrayList<>();

        for (var currentCategory : fragments) {
            currentCategory = currentCategory.trim();
            CarCategory findCategory = resiveCategory(currentCategory);
            returnCategories.add(findCategory);
        }
        return returnCategories;
    }

    private CarMaker resiveMaker(String makerName) {

        List<CarMaker> findMakers = carMakerService.findByName(makerName);

        if (findMakers.size() == 0) {
            CarMaker newCarMaker = new CarMaker();
            newCarMaker.setName(makerName);
            carMakerService.save(newCarMaker);
        }
        else {

            Optional<CarMaker> addCarMaker = Optional.ofNullable(findMakers.get(0));
            if (addCarMaker.isPresent()) {
                return addCarMaker.get();
            }
            else {
                CarMaker newCarMaker = new CarMaker();
                newCarMaker.setName(makerName);
                carMakerService.save(newCarMaker);
            }
        }

        findMakers = carMakerService.findByName(makerName);
        return findMakers.get(0);
    }

    private Optional<CarModel> resiveModel(String modelName) {

        List<CarModel> findModels = carModelService.findByName(modelName);

        if (findModels.size() == 0) {
            return Optional.empty();
        }
        else {

            Optional<CarModel> addCarModel = Optional.ofNullable(findModels.get(0));
            if (addCarModel.isPresent()) {
                return addCarModel;
            }
            else {
                return Optional.empty();
            }
        }
    }

    private CarCategory resiveCategory(String categoryName) {

        List<CarCategory> findCategories = carCategoryService.findByName(categoryName);

        if (findCategories.size() == 0) {
            CarCategory newCarCategory = new CarCategory();
            newCarCategory.setName(categoryName);
            carCategoryService.save(newCarCategory);
        }
        else {

            Optional<CarCategory> addCarCategory = Optional.ofNullable(findCategories.get(0));
            if (addCarCategory.isPresent()) {
                return addCarCategory.get();
            }
            else {
                CarCategory newCarCategory = new CarCategory();
                newCarCategory.setName(categoryName);
                carCategoryService.save(newCarCategory);
            }
        }

        findCategories = carCategoryService.findByName(categoryName);
        return findCategories.get(0);
    }

}
