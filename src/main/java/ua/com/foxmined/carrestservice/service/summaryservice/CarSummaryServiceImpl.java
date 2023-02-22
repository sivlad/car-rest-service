package ua.com.foxmined.carrestservice.service.summaryservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.model.CarMaker;
import ua.com.foxmined.carrestservice.model.CarModel;
import ua.com.foxmined.carrestservice.service.makerservice.CarMakerService;
import ua.com.foxmined.carrestservice.service.modelservice.CarModelService;

import java.util.List;
import java.util.Optional;

@Service
public class CarSummaryServiceImpl implements CarSummaryService{

    @Autowired
    private CarMakerService carMakerService;

    @Autowired
    private CarModelService carModelService;

    @Override
    public int AddManufacturerAndModel(String manufacturer, String model) {

        carMakerService.addManufacturer(manufacturer);

        List<CarMaker> findCarMakers = carMakerService.findByName(manufacturer);

        Optional<CarMaker> carMaker = Optional.ofNullable(findCarMakers.get(0));

        if (carMaker.isEmpty()) {
            return -1;
        }

        List<CarModel> findCarModels = carModelService.findByNameAndCarMakerLike(model,carMaker.get().getId());

        CarModel addCarmodel = new CarModel();
        addCarmodel.setCarMaker(carMaker.get());
        addCarmodel.setName(model);

        if (findCarModels.size() == 0) {
            carModelService.save(addCarmodel);
            return 0;
        }
        else {
            return 1;
        }
    }

}
