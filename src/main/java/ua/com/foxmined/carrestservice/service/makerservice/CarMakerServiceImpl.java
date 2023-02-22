package ua.com.foxmined.carrestservice.service.makerservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.dao.maker.CarMakerRepository;
import ua.com.foxmined.carrestservice.model.CarMaker;

import java.util.List;
import java.util.Optional;

@Service
public class CarMakerServiceImpl implements CarMakerService{

    @Autowired
    private CarMakerRepository carMakerRepository;

    @Override
    public void save(CarMaker carMaker) {
        carMakerRepository.save(carMaker);
    }

    @Override
    public void update(CarMaker carMaker) {
        carMakerRepository.save(carMaker);
    }

    @Override
    public void delete(CarMaker carMaker) {
        carMakerRepository.delete(carMaker);
    }

    @Override
    public List<CarMaker> findByName(String name) {
        return carMakerRepository.findByNameLike(name);
    }

    @Override
    public Page<CarMaker> findAll(Pageable pageable) {
        return carMakerRepository.findAll(pageable);
    }

    @Override
    public void deleteAll() {
        carMakerRepository.deleteAll();
    }

    @Override
    public int addManufacturer(String manufacturer) {

        List<CarMaker> carMakers = findByName(manufacturer);

        if (carMakers.size() == 0) {
            CarMaker addCarMaker = new CarMaker();
            addCarMaker.setName(manufacturer);
            save(addCarMaker);
            return 0;
        }

        Optional<CarMaker> findCarMaker = Optional.ofNullable(carMakers.get(0));

        if (findCarMaker.isPresent()) {
            return -1;
        }
        else {
            CarMaker addCarMaker = new CarMaker();
            addCarMaker.setName(manufacturer);
            save(addCarMaker);
            return 0;
        }
    }

}
