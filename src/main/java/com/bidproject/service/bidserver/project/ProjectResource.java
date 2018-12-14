package com.bidproject.service.bidserver.project;

import com.bidproject.service.bidserver.bid.Bid;
import com.bidproject.service.bidserver.seller.NotFoundException;
import com.bidproject.service.bidserver.seller.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProjectResource {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/project/{id}")
    public Project getProject(@PathVariable int id) throws NotFoundException {
        Optional<Project> project =  projectRepository.findById(id);
        if(!project.isPresent()){
            throw new NotFoundException("Project "+ id + " not found");
        }
        return project.get();
    }

    @GetMapping("/project/{id}/bids")
    public List<Bid> getBids(@PathVariable int id) throws NotFoundException {
        Optional<Project> project =  projectRepository.findById(id);
        if(!project.isPresent()){
            throw new NotFoundException("Seller "+ id + " not found");
        }
        return project.get().getBids();
    }
}
