package ua.com.foxmined.carrestservice.service.categoryservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.dao.category.CarCategoryRepository;
import ua.com.foxmined.carrestservice.model.CarCategory;

import java.util.List;


@Service
public class CarCategoryServiceImpl implements CarCategoryService{

    @Autowired
    private CarCategoryRepository carCategoryRepository;

    @Override
    public CarCategory save(CarCategory carCategory) {
        return carCategoryRepository.save(carCategory);
    }

    @Override
    public void update(CarCategory carCategory) {
        carCategoryRepository.save(carCategory);
    }

    @Override
    public void delete(CarCategory carCategory) {
        carCategoryRepository.delete(carCategory);
    }

    @Override
    public List<CarCategory> findByName(String name) {
        return carCategoryRepository.findByNameLike(name);
    }

    @Override
    public Page<CarCategory> findAll(Pageable pageable) {
        return carCategoryRepository.findAll(pageable);
    }

    @Override
    public void deleteAll() {
        carCategoryRepository.deleteAll();
    }
}
