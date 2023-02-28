package ua.com.foxmined.carrestservice.service.modelservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.model.CarMaker;
import ua.com.foxmined.carrestservice.model.CarModel;

import java.util.List;

@Service
public interface CarModelService {

    CarModel save(CarModel carModel) ;

    void update(CarModel carModel) ;

    void delete(CarModel carModel) ;

    List<CarModel> findByName(String name);

    List<CarModel> findByNameAndCarMakerLike(String name, Long carMakerId);

    Page<CarModel> findAll(Pageable pageable) ;

    void deleteAll() ;

}
