package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> mealListWithExceed = new ArrayList<>();

        Map<LocalDateTime, Boolean> needDaysMap = new HashMap<>();

        //get needed days to needDayMap
        mealList.stream()
                .filter(m -> TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime))
                .forEach(m -> needDaysMap.putIfAbsent(m.getDateTime(), false));

        //get boolean exceed and put to needDaysMap
        needDaysMap.forEach((k, v) -> {
            int count = mealList.stream()
                    .filter(m -> m.getDateTime().toLocalDate().equals(k.toLocalDate()))
                    .mapToInt(UserMeal::getCalories).sum();

            needDaysMap.put(k, count > caloriesPerDay);

        });

        //fill mealListWithExceed
        mealList.stream()
                .filter(m -> TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime))
                .forEach(m -> mealListWithExceed.add(new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), needDaysMap.getOrDefault(m.getDateTime(), false))));

        return mealListWithExceed;
    }
}
