package com.provectus.proveng;

import com.provectus.proveng.domain.Location;
import com.provectus.proveng.service.LocationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@SqlGroup(value = {
        @Sql("/database-scripts/schema.sql"),
        @Sql("/database-scripts/dataForTest.sql"),
})
public class LocationAndDBTests extends Assert {

    @Autowired
    LocationService locationService;

    @Test
    public void InjectLocationToDBTest() {
        Location location = new Location("nameForTest", "Odessa, Tower, floor 3", 25);
        locationService.create(location);
        Location newlocation = locationService.getByName("nameForTest");
        assertNotNull(newlocation);
    }

    @Test
    public void FindLocationByName() {
        Location location = locationService.getByName("Chicago, USA");
        assertNotNull(location);
        assertEquals("Chicago, USA", location.getPlace());
    }

    @Test
    public void FindLocationByIdDBTest() {
        Location location = locationService.getById(3);
        assertNotNull(location);
        assertEquals("Chicago, USA", location.getName());
        assertEquals(8, location.getRoominess());
    }

    @Test
    public void DeleteLocationFromDBTest() {
        Location location = locationService.getById(1);
        locationService.delete(location);
        Location locationResult = locationService.getById(1);
        assertNull(locationResult);
    }

    @Test
    public void UpdateLocationDBTest() {
        Location location = locationService.getById(5);
        location.setName("newNameForTest");
        location.setSysStatus(-1);
        location.setRoominess(12);
        locationService.update(location);
        assertEquals("newNameForTest", location.getName());
        assertEquals(-1, location.getSysStatus());
        assertEquals("Cardiff, UK", location.getPlace());
    }


}
