package ru.kkuzmichev.simpleappforespresso;

import androidx.test.espresso.idling.CountingIdlingResource;

public class EspressoIdlingResources {
    private static final String RESOURCE = "GLOBAL"; // Константа для тега, идентифицирующего ресурс ожидания
    public static CountingIdlingResource idlingResource = new CountingIdlingResource(RESOURCE); // Создает статический экземпляр CountingIdlingResource, который будет отслеживать состояния ожидания

    // Метод для увеличения счетчика активных операций
    public static void increment() {
        idlingResource.increment(); // Увеличивает количество операций, сигнализируя о том, что приложение занято
    }

    // Метод для уменьшения счетчика активных операций
    public static void decrement() {  // Проверяет, чтобы счетчик не стал отрицательным
        if(!idlingResource.isIdleNow()) { // Если ресурс уже свободен, бросает исключение
            idlingResource.decrement(); // Уменьшает количество операций, указывая, что приложение снова свободно
        }
    }
}