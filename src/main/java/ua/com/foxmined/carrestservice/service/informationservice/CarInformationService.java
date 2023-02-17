package ua.com.foxmined.carrestservice.service.informationservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.model.CarInformation;

import java.util.List;

@Service
public interface CarInformationService {

    void save(CarInformation carInformation) ;

    void update(CarInformation carInformation) ;

    void delete(CarInformation carInformation) ;

    Page<CarInformation> findAll(Pageable pageable) ;

    void deleteAll() ;

    List<CarInformation> findByCarMakerId(Long id);

    Page<CarInformation> findByCarCategoryId(Long id, Pageable pageable);

}
