package com.provectus.proveng.service.impl;

import com.provectus.proveng.domain.Developer;
import com.provectus.proveng.repository.DeveloperRepository;
import com.provectus.proveng.service.DeveloperService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    private static Logger logger = LogManager.getLogger(DeveloperServiceImpl.class);

    static {
        logger.info(">>> DeveloperService loaded");
    }

    @Autowired
    private DeveloperRepository developerRepository;

    @Override
    public Developer create(Developer developer) {
        developerRepository.create(developer);
        return developer;
    }

    @Override
    public Developer getById(long id) {

        return developerRepository.getById(id);
    }

    @Override
    public List<Developer> getByEmail(String email) {

        return developerRepository.getByEmail(email);
    }

    @Override
    public Developer update(Developer developer) {
        developerRepository.update(developer);
        return developer;
    }

    @Override
    public void delete(Developer developer) {
        developerRepository.delete(developer);
    }

    @Override
    public void turnOffDeveloper(String email) {
        developerRepository.turnOffDeveloper(email);
    }

    @Override
    public Developer refresh(Developer developer) {
        return developerRepository.refresh(developer);
    }

    @Override
    public List<String> getAllDevelopersEmail() {
        List<Developer> developers = developerRepository.getAllDevelopers();
        List<String> result = new ArrayList<>();
        for (Developer dev : developers) {
            result.add(dev.getEmail());
        }
        return result;
    }

    @Override
    public boolean checkIfDeveloper(String email) {
        return developerRepository.checkIfDeveloper(email).size() != 0;
    }
}
