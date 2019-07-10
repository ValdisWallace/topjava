package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {
    @Autowired
    private CrudMealRepository crudRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null)
            return null;

        User ref = crudUserRepository.getOne(userId);
        meal.setUser(ref);

        return crudRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.findByIdAndUserId(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findByUser_IdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository.findByUser_IdAndDateTimeBetweenOrderByDateTimeDesc(userId, startDate, endDate);
    }
}
