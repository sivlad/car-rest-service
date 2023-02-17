package ua.com.foxmined.carrestservice.service.modelservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.dao.model.CarModelRepository;
import ua.com.foxmined.carrestservice.model.CarModel;

import java.util.List;

@Service
public class CarModelServiceImpl implements CarModelService{

    @Autowired
    private CarModelRepository carModelRepository;

    @Override
    public void save(CarModel carModel) {
        carModelRepository.save(carModel);
    }

    @Override
    public void update(CarModel carModel) {
        carModelRepository.save(carModel);
    }

    @Override
    public void delete(CarModel carModel) {
        carModelRepository.delete(carModel);
    }

    @Override
    public List<CarModel> findByName(String name) {
        return carModelRepository.findByNameLike(name);
    }

    @Override
    public Page<CarModel> findAll(Pageable pageable) {
        return carModelRepository.findAll(pageable);
    }

    @Override
    public void deleteAll() {
        carModelRepository.deleteAll();
    }
}
