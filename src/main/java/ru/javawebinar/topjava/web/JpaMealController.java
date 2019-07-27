package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
@RequestMapping(value = "/meals")
public class JpaMealController {
    private static final Logger log = LoggerFactory.getLogger(JpaMealController.class);

    @Autowired
    private MealService service;

    @PostMapping(params = {"id", "dateTime", "description", "calories"})
    public String save(Model model,
                       @RequestParam(defaultValue = "0") int id,
                       @RequestParam String dateTime,
                       @RequestParam String description,
                       @RequestParam int calories) {
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, calories);

        if (id != 0) {
            assureIdConsistent(meal, id);
            log.info("update {} for user {}", meal, SecurityUtil.authUserId());
            service.update(meal, SecurityUtil.authUserId());
        } else {
            checkNew(meal);
            log.info("create {} for user {}", meal, SecurityUtil.authUserId());
            service.create(meal, SecurityUtil.authUserId());
        }
        return getAll(model);
    }

    @GetMapping
    public String getAll(Model model) {
        log.info("getAll for user {}", SecurityUtil.authUserId());
        model.addAttribute("meals", MealsUtil.getWithExcess(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping(params = "delete")
    public String delete(Model model, @RequestParam("delete") int id) {
        log.info("delete meal {} for user {}", id, SecurityUtil.authUserId());
        service.delete(id, SecurityUtil.authUserId());
        getAll(model);
        return "meals";
    }

    @GetMapping(params = "create")
    public String create(Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        log.info("create {} for user {}", meal, SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        return "/mealForm";
    }

    @GetMapping(params = "update")
    public String update(Model model, @RequestParam("update") int id) {
        final Meal meal = service.get(id, SecurityUtil.authUserId());
        log.info("update {} for user {}", meal, SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        return "/mealForm";
    }

    @GetMapping(params = {"startDate", "endDate", "startTime", "endTime"})
    public String getFiltered(Model model,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                              @RequestParam LocalTime startTime,
                              @RequestParam LocalTime endTime) {
        model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }


    public List<MealTo> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, SecurityUtil.authUserId());

        List<Meal> mealsDateFiltered = service.getBetweenDates(startDate, endDate, SecurityUtil.authUserId());
        return MealsUtil.getFilteredWithExcess(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }
}
