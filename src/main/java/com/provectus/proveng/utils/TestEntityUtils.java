package com.provectus.proveng.utils;

import com.provectus.proveng.domain.*;
import com.provectus.proveng.enumaration.LevelType;
import com.provectus.proveng.enumaration.MaterialType;
import com.provectus.proveng.enumaration.RoleName;
import com.provectus.proveng.enumaration.TestType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestEntityUtils {

    private static Logger logger = LogManager.getLogger(TestEntityUtils.class);

    public static Group createTestGroup(Long id, String level) {

        Group group = new Group();

        group.setName("Group" + id);
        group.setId(id);

        User leader = TestEntityUtils.createTestUser(1L);
        group.setLeader(leader);

        group.setLevel(level);
        group.addEvent(createTestEvent(1L));

        group.addUser(createTestUser(2 * id));
        group.addUser(createTestUser(2 * id + 1));

        group.setPrimaryGroupFlag(false);

        return group;
    }

    public static Group createTestGroup(Long id) {

        Group group = new Group();
        group.setName("Group" + id);
        if (id != null)
            group.setId(id);
        User leader = TestEntityUtils.createTestUser(1L);
        group.setLeader(leader);
        group.setLevel("Intermediate");
        group.addEvent(createTestEvent(1L));
        group.addUser(createTestUser(2L));
        group.addUser(createTestUser(3L));

        return group;
    }

    public static List<Group> createTestGroups() {

        List<Group> list = new ArrayList<>();
        list.add(TestEntityUtils.createTestGroup(1L, "Elementary"));
        list.add(TestEntityUtils.createTestGroup(2L, "Elementary"));
        list.add(TestEntityUtils.createTestGroup(3L, "Intermediate"));
        list.add(TestEntityUtils.createTestGroup(4L, "Intermediate"));
        list.add(TestEntityUtils.createTestGroup(5L, "Upper-intermediate"));
        list.add(TestEntityUtils.createTestGroup(6L, "Pre-intermediate"));
        list.add(TestEntityUtils.createTestGroup(7L, "Advanced"));

        return list;

    }

    public static User createTestUser(Long id) {
        logger.debug(">>> createTestUser()");

        User user = new User();

        Department dep = new Department();
        dep.setName("Provectus");
        dep.setUrl("http://provectus-it.com");
        dep.setDescription("new users");
        dep.setId(1);

        Role role1 = new Role();
        role1.setId(1);
        role1.setName("RoleNameValue1");

        Permission permission1 = new Permission();
        permission1.setId(1);
        permission1.setName("Permission1");
        permission1.setObject("calendar");
        permission1.setAccessFlag("W");

        Permission permission2 = new Permission();
        permission2.setId(2);
        permission2.setName("Permission2");
        permission2.setObject("event");
        permission2.setAccessFlag("R");

        role1.addPermission(permission1);
        role1.addPermission(permission2);
        role1.setId(1);

        user.setId(id);
        user.setDepartment(dep);
        user.setEmail("eMailValue" + id);
        user.setFirstName("Ivan" + id);
        user.setLastName("Ivanov" + id);
        user.setPhone("+38(067)1112223" + id);
        user.setLoginName("email_Login" + id);
        user.addRole(role1);
        user.setSkype("SkypeValue" + id);
        user.setSysStatus(0);
        user.setUrl("http://..........." + id);

        return user;
    }

    public static Event createTestEvent(Long id) {

        Group group = new Group();
        group.setName("Group" + id);
        group.setId(id);
        User leader = TestEntityUtils.createTestUser(1L);
        Event event = new Event(leader, "nameForTest", "Lesson");
        event.setId(id);
        event.setGroup(group);
        return event;
    }

    public static DayBook createTestDayBook(Long id) {

        User user = TestEntityUtils.createTestUser(1L);
        DayBook dayBook = new DayBook(47, "Test", new Date(), null, user, null);
        dayBook.setId(id);
        return dayBook;
    }

    public static Test createTestTest(Long id) {
        Test test = new Test();
        if (id != null)
            test.setId(id);
        test.setName("Start test");
        test.setDuration(3600000);
        test.setWeight(5);
        test.setType(TestType.START);
        test.setMinLevel(LevelType.ELEMENTARY.getNumberValue());
        List<TestCard> testCards = new ArrayList<>();

        TestAnswer answ1 = new TestAnswer();
        answ1.setId(1L);
        answ1.setName("A");
        answ1.setText("There isn’t no");
        TestAnswer answ2 = new TestAnswer();
        answ2.setId(2L);
        answ2.setName("B");
        answ2.setText("There isn’t any");
        TestAnswer answ3 = new TestAnswer();
        answ2.setIsCorrect(true);
        answ3.setId(3L);
        answ3.setName("A");
        answ3.setText("goes to");
        TestAnswer answ4 = new TestAnswer();
        answ4.setId(4L);
        answ4.setName("B");
        answ4.setText("is going to");
        List<TestAnswer> answers1 = new ArrayList<>();
        List<TestAnswer> answers2 = new ArrayList<>();
        answers1.add(answ1);
        answers1.add(answ2);
        answers2.add(answ3);
        answers2.add(answ4);

        TestCard testCard1 = new TestCard();
        testCard1.setId(1L);
        testCard1.setName("Card 1");
        testCard1.setQuestion("..... Caviar in the fridge.");
        testCard1.setTestAnswers(answers1);

        TestCard testCard2 = new TestCard();
        testCard2.setId(2L);
        testCard2.setName("Card 2");
        testCard2.setQuestion("George..... fly to Stockholm tomorrow.");
        testCard2.setTestAnswers(answers2);

        testCards.add(testCard1);
        testCards.add(testCard2);
        test.setTestCards(testCards);
        return test;
    }

    public static Test createTestTest(Long id, String name) {
        Test test = createTestTest(id);
        test.setName(name);
        return test;
    }

    public static Test TestCreateTestTest(Long id, String name, int level) {
        Test test = createTestTest(id, name);
        test.setMinLevel(level);
        return test;
    }

    public static List<com.provectus.proveng.domain.Test> createTestTests() {
        List<Test> tests = new ArrayList<>();
        tests.add(createTestTest(1L));
        return tests;
    }

    public static Role createTestRol(Long id) {

        Role role = new Role();
        if (id != null)
            role.setId(id);
        role.setName(RoleName.GUEST);
        return role;
    }

    public static TestAttempt createTestAttempt(Long id) {
        TestAttempt testAttempt = new TestAttempt();
        if (id != null)
            testAttempt.setId(id);
        testAttempt.setIsPassed(false);
        return testAttempt;
    }

    public static Material createTestMaterial(Long id, MaterialType type) {

        Material material = new Material();
        if (id != null)
            material.setId(id);

        switch (type) {
            case AUDIO:
                material.setName("The Wanderer of the North");
                material.setDescription(
                        "In this story from To Build a Fire and Other Stories, a mysterious stranger "
                                + "tells a few mushers about how he followed his wife's kidnapper from his home"
                                + " in the Aleutian Islands to Tokyo Bay and finally to the Yukon Territory in "
                                + "Canada. Author: Jack London.");
                material.setLink(
                        "https://americanenglish.state.gov/files/ae/resource_files/the-wanderer-ofthe-north.mp3");
                material.setType(MaterialType.AUDIO.toString());
                material.setMinLevel(LevelType.ELEMENTARY.getNumberValue());
                break;
            case VIDEO:
                material.setName("Pronunciation");
                material.setDescription(
                        "Use this video to learn about pronouncing \"than\" in sentences with comparative adjectives.");
                material.setLink(
                        "http://av.voanews.com/Videoroot/Pangeavideo/2016/10/3/33/334705e4-cf26-44d9-aa82-0f4f11a37074.mp4");
                material.setType(MaterialType.VIDEO.toString());
                material.setMinLevel(LevelType.PRE_INTERMEDIATE.getNumberValue());
                break;
            case LINK:
                material.setName("The Wanderer of the North");
                material.setDescription(
                        "In this story from To Build a Fire and Other Stories, a mysterious stranger tells"
                                + " a few mushers about how he followed his wife's kidnapper from his home in the Aleutian Islands "
                                + "to Tokyo Bay and finally to the Yukon Territory in Canada. Author: Jack London ");
                material.setLink(
                        "https://americanenglish.state.gov/files/ae/resource_files/the-wanderer-of-the-north.pdf");
                material.setType(MaterialType.LINK.toString());
                material.setMinLevel(LevelType.INTERMEDIATE.getNumberValue());
                break;
        }

        return material;

    }

    public static List<Material> createTestMaterials() {
        List<Material> materials = new ArrayList<>();
        materials.add(createTestMaterial(1L, MaterialType.AUDIO));
        materials.add(createTestMaterial(2L, MaterialType.VIDEO));
        materials.add(createTestMaterial(3L, MaterialType.LINK));
        return materials;
    }

}
