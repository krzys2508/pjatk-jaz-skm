package pl.pjatk.skmapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.skmapi.exception.NotFoundException;
import pl.pjatk.skmapi.service.CrudService;
import pl.pjatk.skmapi.service.DbEntity;

import java.util.List;

public abstract class CrudController<T extends DbEntity> {
    @Autowired
    private final CrudService<T> service;

    protected CrudController(CrudService<T> service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<T>> getAll() throws NotFoundException {
        var payload = service.getAll();
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable("id") long id) throws NotFoundException {
        try {
            var payload = service.getById(id);
            return new ResponseEntity<>(payload, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") long id) throws NotFoundException {
        try {
            service.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }

    @PostMapping
    public ResponseEntity<T> add(@RequestBody T t) {
        var payload = service.add(t);
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }

    // IDK
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleInternalError(NotFoundException e) {
//        e.printStackTrace();
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}