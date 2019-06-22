package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }


    @Override
    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        ValidationUtil.checkMealUserId(repository.get(id), userId);
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        ValidationUtil.checkMealUserId(repository.get(id), userId);
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public void update(Meal meal, int userId) {
        ValidationUtil.checkMealUserId(meal, userId);
        checkNotFoundWithId(repository.save(meal), meal.getId());
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }
}