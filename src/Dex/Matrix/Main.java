package Dex.Matrix;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String lang = System.getProperty("user.language");
        List<String> localization;
        switch (lang)
        {
            case "ru":
                localization = Arrays.asList("Обновить информацию","Пятнашки","Счёт","Меню","Новая игра","Выход","Поздравляю, вы победили!","Победа!");
                break;
            case "ua":
                localization = Arrays.asList("Оновити інформацію","П'ятнашки","Рахунок","Меню","Нова гра","Вихід","Вітаю, ви перемогли!","Перемога!");
                break;
            default:
                localization = Arrays.asList("Update information","Fifteen", "Points", "Menu", "New game", "Exit", "Congratulations, you won!", "Victory!");
                break;
        }

        new GUI(localization);


    }

}
