package ua.com.foxmined.carrestservice.service.modelcategoryservice;

import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.model.CarModelCategory;

import java.util.List;

@Service
public interface CarModelCategoryService {

    void save(CarModelCategory carModelCategory) ;

    void update(CarModelCategory carModelCategory) ;

    void delete(CarModelCategory carModelCategory) ;

    List<CarModelCategory> findAll() ;

    void deleteAll() ;

}
