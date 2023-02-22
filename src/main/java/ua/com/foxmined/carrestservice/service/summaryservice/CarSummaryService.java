package ua.com.foxmined.carrestservice.service.summaryservice;

import org.springframework.stereotype.Service;

@Service
public interface CarSummaryService {

    int AddManufacturerAndModel(String manufacturer, String Model);

}
