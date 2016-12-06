package com.provectus.proveng.service.impl;

import com.provectus.proveng.domain.Location;
import com.provectus.proveng.repository.LocationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("locationService")
public class LocationServiceImpl implements com.provectus.proveng.service.LocationService {

    private static Logger logger = LogManager.getLogger(LocationServiceImpl.class);
    @Autowired
    private LocationRepository locationRepository;

    @Override
    public void create(Location location) {

        locationRepository.create(location);
    }

    @Override
    public Location getById(long id) {

        Location location = locationRepository.getById(id);
        return location;
    }

    @Override
    public Location getByName(String name) {

        Location location = locationRepository.getByName(name);
        return location;
    }

    @Override
    public List<Location> getAll() {

        List location = locationRepository.getAll();
        return location;
    }

    @Override
    public Location update(Location location) {
        locationRepository.update(location);
        return location;
    }

    @Override
    public void delete(Location location) {

        locationRepository.delete(location);
    }
}
