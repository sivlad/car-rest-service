package ua.com.foxmined.carrestservice.dao.information;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmined.carrestservice.model.CarInformation;
import ua.com.foxmined.carrestservice.model.CarMaker;

import java.util.List;

@Repository
@Transactional
public interface CarInformationRepository extends PagingAndSortingRepository<CarInformation,Long> {

    String FIND_ALL_CARS_FROM_CURRENT_CAR_MAKER =
            """
            SELECT information_.*
            FROM information information_ JOIN model model_ ON information_.model_id = model_.id
            WHERE model_.maker_id = :carMakerId\s""";

    String FIND_ALL_CARS_FROM_CURRENT_CAR_CATEGORY =
            """
            SELECT information_.*
            FROM information information_ JOIN model model_ ON information_.model_id = model_.id
            JOIN modelCategory modelCategory_ ON model_.id = modelCategory_.model_id
            WHERE modelCategory_.category_id = :carCategoryId\s""";

    Page<CarInformation> findAll(Pageable pageable);

    @Query(value = FIND_ALL_CARS_FROM_CURRENT_CAR_MAKER, nativeQuery = true)
    List<CarInformation> findByCarMakerId(@Param("carMakerId") Long id);

    @Query(value = FIND_ALL_CARS_FROM_CURRENT_CAR_CATEGORY, nativeQuery = true)
    Page<CarInformation> findByCarCategoryId(@Param("carCategoryId") Long id, Pageable pageable);

}
