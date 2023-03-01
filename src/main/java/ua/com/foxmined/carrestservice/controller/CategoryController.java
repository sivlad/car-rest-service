package ua.com.foxmined.carrestservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.com.foxmined.carrestservice.exception.EntityNotPresentException;
import ua.com.foxmined.carrestservice.service.categoryservice.CarCategoryService;
import ua.com.foxmined.carrestservice.utils.EndPoints;

@RestController
@RequestMapping(value = EndPoints.VERSION_1)
public class CategoryController {

    @Autowired
    private CarCategoryService сarCategoryService;

    @RequestMapping(value = EndPoints.SET_CATEGORY, method = RequestMethod.POST)
    public ResponseEntity<?> addCategory(@PathVariable(name = "category") String category) {
        сarCategoryService.addCategory(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = EndPoints.UPDATE_CATEGORY, method = RequestMethod.PUT)
    public ResponseEntity<?> addCategory(@PathVariable(name = "oldCategory") String oldCategory,
                                         @PathVariable(name = "newCategory") String newCategory) {
        try {
            сarCategoryService.updateCategory(oldCategory, newCategory);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (EntityNotPresentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = EndPoints.SET_CATEGORY, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCategory(@PathVariable(name = "category") String category) {

        try {
            сarCategoryService.deleteCategory(category);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (EntityNotPresentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
