package ua.com.foxmined.carrestservice.service.makerservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.dao.maker.CarMakerRepository;
import ua.com.foxmined.carrestservice.model.CarMaker;

import java.util.List;

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
}
