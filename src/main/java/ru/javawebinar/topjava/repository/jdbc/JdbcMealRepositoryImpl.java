package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("datetime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories());

        if (meal.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        }
        else if (namedParameterJdbcTemplate.update(
                "UPDATE meals SET datetime=:datetime, description=:description, calories=:calories WHERE id=:id", map) == 0) {
            return null;
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        String query = "DELETE FROM meals WHERE id=? AND user_id=?";
        return jdbcTemplate.update(query, id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        String query = "SELECT * FROM meals WHERE id=? AND user_id=?";
        return jdbcTemplate.queryForObject(query, new Object[] {id, userId}, new MealRowMapper());
    }

    @Override
    public List<Meal> getAll(int userId) {
        String query = "SELECT * FROM meals WHERE user_id=? ORDER BY datetime DESC";
        return jdbcTemplate.query(query, new Object[] {userId}, new MealRowMapper());
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        String query = "SELECT * FROM meals WHERE user_id=? AND datetime BETWEEN ? AND ? ORDER BY datetime DESC";
        return jdbcTemplate.query(query, new Object[] {userId, startDate, endDate}, new MealRowMapper());
    }
}
