package com.provectus.proveng.service;

import com.provectus.proveng.domain.Location;

import java.util.List;

public interface LocationService {

    void create(Location location);

    Location getById(long id);

    Location getByName(String name);

    List<Location> getAll();

    Location update(Location location);

    void delete(Location location);
}
