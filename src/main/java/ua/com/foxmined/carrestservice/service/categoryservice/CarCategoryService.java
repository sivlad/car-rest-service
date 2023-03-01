package ua.com.foxmined.carrestservice.service.categoryservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.exception.EntityNotPresentException;
import ua.com.foxmined.carrestservice.model.CarCategory;

import java.util.List;

@Service
public interface CarCategoryService {

    CarCategory save(CarCategory carCategory) ;

    void update(CarCategory carCategory) ;

    void delete(CarCategory carCategory) ;

    List<CarCategory> findByName(String name);

    Page<CarCategory> findAll(Pageable pageable) ;

    void deleteAll() ;

    CarCategory addCategory(String category);

    CarCategory updateCategory(String oldCategory, String newCategory) throws EntityNotPresentException;

    void deleteCategory(String category) throws EntityNotPresentException;

}
