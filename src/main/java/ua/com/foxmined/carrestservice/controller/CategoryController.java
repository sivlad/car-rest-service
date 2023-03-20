package ua.com.foxmined.carrestservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.foxmined.carrestservice.exception.EntityNotPresentException;
import ua.com.foxmined.carrestservice.model.CarCategory;
import ua.com.foxmined.carrestservice.service.categoryservice.CarCategoryService;
import ua.com.foxmined.carrestservice.utils.EndPoints;

import java.util.List;

@RestController
@RequestMapping(value = EndPoints.VERSION_1)
public class CategoryController {

    @Autowired
    private CarCategoryService сarCategoryService;

    @RequestMapping(value = EndPoints.GET_CATEGORIES, method = RequestMethod.GET)
    public ResponseEntity<List<CarCategory>> getCategories(@RequestParam(name="page",defaultValue = "0") Integer page,
                                                           @RequestParam(name="pageSize",defaultValue = "10") Integer pagesize) {
        List<CarCategory> carCategories = сarCategoryService.findAll(PageRequest.of(page,pagesize)).toList();
        return new ResponseEntity<>(carCategories, HttpStatus.OK);
    }

    @RequestMapping(value = EndPoints.SET_CATEGORY, method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('create:items')")
    public ResponseEntity<?> addCategory(@PathVariable(name = "category") String category) {
        сarCategoryService.addCategory(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = EndPoints.UPDATE_CATEGORY, method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('update:items')")
    public ResponseEntity<?> updateCategory(@PathVariable(name = "oldCategory") String oldCategory,
                                         @PathVariable(name = "newCategory") String newCategory) {
        try {
            сarCategoryService.updateCategory(oldCategory, newCategory);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (EntityNotPresentException e) {
            return new ResponseEntity<>("There is not current category",HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = EndPoints.SET_CATEGORY, method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('delete:items')")
    public ResponseEntity<?> deleteCategory(@PathVariable(name = "category") String category) {

        try {
            сarCategoryService.deleteCategory(category);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (EntityNotPresentException e) {
            return new ResponseEntity<>("There is not current category",HttpStatus.NOT_FOUND);
        }
    }
}
