package com.bidproject.service.bidserver.seller;

import com.bidproject.service.bidserver.project.Project;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Seller {

    @Id
    @GeneratedValue
    private Integer id;

    @Size(min=2, message="Name should be 2 characters long")
    @ApiModelProperty(notes = "Name should be 2 characters long")
    private String name;

    @OneToMany(mappedBy="seller")
    private List<Project> projects;

    protected Seller(){

    }

    public Seller(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
