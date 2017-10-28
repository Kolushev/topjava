package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        List<UserMealWithExceed> result = new ArrayList<>();

Map <LocalDate,Integer> map = mealList
        .stream()
        .collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));

result = mealList
        .stream()
        .filter (meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(),startTime,endTime))
        .map(meal -> (createWithExceed(meal, map.get(meal.getDate()) > caloriesPerDay)))


        .collect(Collectors.toList());

        for (UserMealWithExceed meal : result) {

            System.out.println(meal.getDescription() + " " + meal.getCalories() + " " + meal.getDateTime().toString() + " " + meal.isExceed());
        }
return result;

    }


/*

    public static List<UserMealWithExceed>  getFilteredWithExceededByCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> result = new ArrayList<>();


        Map<LocalDate,Integer> map =  new HashMap<LocalDate,Integer>();

        for (UserMeal userMeal: mealList){

            if ((map.get(userMeal.getDateTime().toLocalDate()))==null) { map.put((userMeal.getDateTime().toLocalDate()),userMeal.getCalories());}

            else  {  map.put((userMeal.getDateTime().toLocalDate()),(map.get(userMeal.getDateTime().toLocalDate()))+userMeal.getCalories());  }


            }

            for (Map.Entry<LocalDate,Integer> entry: map.entrySet()){
                System.out.println("Date =  " + entry.getKey() + " калории " + entry.getValue());
            }

for (UserMeal meal: mealList){
                if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(),startTime,endTime)) {

result.add(new UserMealWithExceed(meal.getDateTime(),meal.getDescription(),meal.getCalories(), (map.get(meal.getDateTime().toLocalDate())>caloriesPerDay)));

    }

}


for (UserMealWithExceed meal : result) {

    System.out.println(meal.getDescription()+" "+meal.getCalories()+" "+meal.getDateTime().toString()+" "+meal.isExceed());
}



        return result;
    }
    */




    public static UserMealWithExceed createWithExceed(UserMeal meal, boolean exceeded) {
        return new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }


}
