package ua.com.foxmined.carrestservice.dao.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmined.carrestservice.model.CarModel;

import java.util.List;

@Repository
@Transactional
public interface CarModelRepository extends PagingAndSortingRepository<CarModel,Long> {

    Page<CarModel> findAll(Pageable pageable);

    List<CarModel> findByNameLike(String name);

}
