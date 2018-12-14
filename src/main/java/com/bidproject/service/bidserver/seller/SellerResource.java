package com.bidproject.service.bidserver.seller;

import com.bidproject.service.bidserver.project.Project;
import com.bidproject.service.bidserver.project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
public class SellerResource {

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping("/sellers")
    public ResponseEntity<Object> createSeller(@Valid @RequestBody Seller seller) {
        Seller savedSeller = sellerRepository.save(seller);
        //gets /sellers from the above uri and appends /{id} to it after which replaces the id template with stored seller id
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedSeller.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/seller/{id}/project")
    public ResponseEntity<Object> createSeller(@PathVariable int id, @RequestBody Project project) throws NotFoundException {


        Optional<Seller> sellerOptional =  sellerRepository.findById(id);
        if(!sellerOptional.isPresent()){
            throw new NotFoundException("Seller "+ id + " not found");
        }
        Seller seller = sellerOptional.get();
        project.setSeller(seller);
        projectRepository.save(project);

        //gets /sellers from the above uri and appends /{id} to it after which replaces the id template with stored seller id
        URI location = ServletUriComponentsBuilder.fromPath("/project/{id}").buildAndExpand(project.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/sellers")
    public List<Seller> retrieveAllSeller() {
        return sellerRepository.findAll();
    }

    @GetMapping("/seller/{id}")
    public Seller getSeller(@PathVariable int id) throws NotFoundException {
        Optional<Seller> seller =  sellerRepository.findById(id);
        if(!seller.isPresent()){
            throw new NotFoundException("Seller "+ id + " not found");
        }
        return seller.get();
    }

    @GetMapping("/seller/{id}/projects")
    public List<Project> getSellerProjects(@PathVariable int id) throws NotFoundException {
        Optional<Seller> seller =  sellerRepository.findById(id);
        if(!seller.isPresent()){
            throw new NotFoundException("Seller "+ id + " not found");
        }
        return seller.get().getProjects();
    }

    @GetMapping("/hello")
    public String hello(@RequestHeader(name="Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("hello.morning.message", null, locale);
    }
}
