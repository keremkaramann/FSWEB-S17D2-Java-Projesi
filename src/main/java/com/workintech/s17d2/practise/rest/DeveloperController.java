package com.workintech.s17d2.practise.rest;

import com.workintech.s17d2.practise.model.Developer;
import com.workintech.s17d2.practise.model.Experience;
import com.workintech.s17d2.practise.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/developers")
public class DeveloperController {
    private Map<Integer, Developer> developers;
    private Taxable taxable;

    @PostConstruct
    public void init() {
        developers = new HashMap<>();
    }

    @Autowired
    public DeveloperController(Taxable taxable) {
        this.taxable = taxable;
    }

    @GetMapping("/")
    public List<Developer> get() {
        return developers.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Developer getById(@PathVariable int id) {
        return developers.get(id);
    }

    @PostMapping("/")
    public Developer create(@RequestBody Developer developer) {
        int id = developer.getId();
        String name = developer.getName();
        double salary = developer.getSalary();
        Experience experience = developer.getExperience();

        Developer savedDeveloper = null;
        switch (experience) {
            case JUNIOR:
            case MID:
            case SENIOR:
                salary -= salary * taxable.getSimpleTaxRate();
                savedDeveloper = new Developer(id, name, salary, experience);
                this.developers.put(id, savedDeveloper);
                break;
            default:
                break;
        }

        return savedDeveloper != null ? savedDeveloper : developer;
    }

    @PutMapping("/{id}")
    public Developer updateDeveloper(@PathVariable int id, @RequestBody Developer developer) {
        this.developers.replace(id, new Developer(id, developer.getName(), developer.getSalary(), developer.getExperience()));
        return developers.get(id);
    }

    @DeleteMapping("/{id}")
    public Developer deleteDeveloper(@PathVariable int id) {
        Developer removedDeveloper = developers.get(id);
        developers.remove(id);
        return removedDeveloper;
    }

}
