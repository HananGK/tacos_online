package com.hanan.tacosonline.web.api;

import com.hanan.tacosonline.data.TacoRepository;
import com.hanan.tacosonline.exceptions.TacoNotFoundException;
import com.hanan.tacosonline.model.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api", produces = "application/json")
@CrossOrigin(origins = "*")
public class ApiTacosController {

    @Autowired
    TacoRepository tacoRepository;


//    @GetMapping("/tacos/{id}")
//    public Taco tacoById(@PathVariable("id") Long id){
//        Optional<Taco> optTaco = tacoRepository.findById(id);
//        if(optTaco.isPresent()) {
//            return optTaco.get();
//        }
//        return null;
//    }

    @GetMapping("/tacos/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id){
        Optional<Taco> optTaco = tacoRepository.findById(id);
        if(optTaco.isPresent()) {
            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @PostMapping(path = "/tacos", consumes = "application/json")
    public ResponseEntity<Taco> saveTaco(@RequestBody Taco taco){
        Taco savedTaco = tacoRepository.save(taco);
        if (savedTaco != null){
            return new ResponseEntity<>(savedTaco, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/tacos/{id}")
    public ResponseEntity<Taco> deleteTaco(@PathVariable("id") Long id){
        Optional<Taco> optTaco = tacoRepository.findById(id);
        if(optTaco.isPresent()) {
            tacoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/tacos")
    public ResponseEntity<Taco> updateTaco(@RequestBody Taco taco){
        tacoRepository.findById(taco.getId()).orElseThrow(() -> new TacoNotFoundException("Taco no existe", "taco field", null));
        tacoRepository.save(taco);
        return new ResponseEntity<>(taco, HttpStatus.OK);
    }

    @GetMapping("/tacos")
    public ResponseEntity<Iterable<Taco>> getAllTacos(){
        return new ResponseEntity<>(tacoRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/tacos/recents/{page}")
    public ResponseEntity<Iterable<Taco>> getAllTacosOrderedAndPages(@PathVariable("page") Integer page){
        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by("createdAt").descending());
        return new ResponseEntity<>(tacoRepository.findAll(pageRequest), HttpStatus.OK);
    }


}
