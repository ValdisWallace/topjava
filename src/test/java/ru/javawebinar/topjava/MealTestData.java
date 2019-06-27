package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.inmemory.InMemoryBaseRepository;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ;
    public static final int USER_ID = 100000;
    public static final LocalDate START_DATE = LocalDate.of(2019, 6, 20);
    public static final LocalDate END_DATE = LocalDate.of(2019, 6, 20);
    public static final LocalDateTime START_DATE_TIME = LocalDateTime.of(2019, 6, 20, 20, 0);
    public static final LocalDateTime END_DATE_TIME = LocalDateTime.of(2019, 6, 21, 10, 0);

    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(START_SEQ, LocalDateTime.of(2019, 6, 20, 15, 30), "Milk", 300),
            new Meal(START_SEQ + 1, LocalDateTime.of(2019, 6, 20, 20, 0), "Burger", 500),
            new Meal(START_SEQ + 2, LocalDateTime.of(2019, 6, 21, 8, 15), "Bread", 350),
            new Meal(START_SEQ + 3, LocalDateTime.of(2019, 6, 21, 12, 10), "Borsch", 400)
    );

    public static final Meal MEAL = MEALS.get(0);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(List<Meal> actual, Meal... meals) {
        assertMatch(actual, getAll(meals));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static List<Meal> getAll(Meal... meals) {
        return getAllFiltered(meal -> true, meals);
    }

    public static List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return getAllFiltered(meal -> Util.isBetween(meal.getDateTime(), startDateTime, endDateTime));
    }

    private static List<Meal> getAllFiltered(Predicate<Meal> filter, Meal... meals) {
        List<Meal> result = new ArrayList<>(MEALS);
        Collections.addAll(result, meals);
        return result.stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}
