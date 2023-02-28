package ua.com.foxmined.carrestservice.service.makerservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.exception.EntityPresentException;
import ua.com.foxmined.carrestservice.model.CarMaker;

import java.util.List;

@Service
public interface CarMakerService {

    CarMaker save(CarMaker carMaker) ;

    void update(CarMaker carMaker) ;

    void delete(CarMaker carMaker) ;

    List<CarMaker> findByName(String name);

    Page<CarMaker> findAll(Pageable pageable) ;

    void deleteAll() ;

    CarMaker addManufacturer(String manufacturer);

    void deleteManufacturer(String manufacturer);

    void updateManufacturer(String oldManufacturer,String newManufacturer);

}
