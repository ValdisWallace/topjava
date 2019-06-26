package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.jdbc.core.RowMapper;
import ru.javawebinar.topjava.model.Meal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class MealRowMapper implements RowMapper<Meal> {
    @Override
    public Meal mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        LocalDateTime dateTime = rs.getObject("datetime", LocalDateTime.class);
        String description = rs.getString("description");
        int calories = rs.getInt("calories");

        return new Meal(id, dateTime, description, calories);
    }
}
