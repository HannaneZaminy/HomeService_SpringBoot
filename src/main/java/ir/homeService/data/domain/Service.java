package ir.homeService.data.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "Type_Of_Service")
//@DiscriminatorValue(value = "Service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @OneToMany(mappedBy = "service")
    private List<SubService> subServices = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public Service setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Service setName(String name) {
        this.name = name;
        return this;
    }

    public List<SubService> getSubServices() {
        return subServices;
    }

    public Service setSubServices(List<SubService> subServices) {
        this.subServices = subServices;
        return this;
    }


}
