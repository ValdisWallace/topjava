package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
        })
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "NewDesc", 999);
        Meal repMeal = service.create(newMeal, USER_ID);
        newMeal.setId(repMeal.getId());
        assertMatch(service.getAll(USER_ID), newMeal);
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL_ID, USER_ID);
        assertMatch(meal, MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        service.delete(1, 1);
    }

    @Test
    public void getBetweenDates() throws Exception {
        assertMatch(service.getBetweenDates(START_DATE, END_DATE, USER_ID),
                MealTestData.getBetween(DateTimeUtil.adjustStartDateTime(START_DATE), DateTimeUtil.adjustEndDateTime(END_DATE)));
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        assertMatch(service.getBetweenDateTimes(START_DATE_TIME, END_DATE_TIME, USER_ID),
                MealTestData.getBetween(START_DATE_TIME, END_DATE_TIME));
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL);
        updated.setDescription("TestDesc");
        updated.setCalories(666);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), MealTestData.getAll());
    }
}