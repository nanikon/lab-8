package ru.nanikon.FlatCollection.texts;

import java.util.ListResourceBundle;

public class Text_ru_RU extends ListResourceBundle {
    public Object[][] getContents() { return contents; }
    private Object[][] contents = {
            {"appTitle", "Коллекция квартир"},
            {"russianLanguage", "Русский"},
            {"slovakLanguage", "Словацкий"},
            {"danishLanguage", "Датский"},
            {"spanishLanguage", "Испанский (Эквадор)"},
            {"exit", "Выход"},
            {"logIn", "Авторизация"},
            {"register", "Регистрация"},
            {"back", "Назад"},
            {"login", "Логин"},
            {"password", "Пароль"},
            {"password_again", "Повторите пароль"},
            {"send", "Отправить"},
            {"wrong_password", "Упс, пароль не угадали!"},
            {"wrong_double_password", "Пароли не совпадают!"},
            {"not_found_login", "Пользователь с таким логином не найден"},
            {"wrong_login", "Пользователь с таким логином уже существует"},
            {"logIn_problem", "При авторизации возникли проблемы. Повторите попытку позднее"},
            {"success_logIn", "Вы успешно авторизированы"},
            {"register_problem", "Регистрация не удалась. Повторите попытку позднее"},
            {"success_register", "Пользователь успешно зарегистрирован"},
            {"change_view", "Изменить вид"},
            {"logout", "Выйти из аккаунта"},
            {"add", "Добавить"},
            {"update", "Обновить"},
            {"remove_by_id", "Удалить по id"},
            {"remove_any_by_transport", "Удалить любой по транспорту"},
            {"clear", "Очистить"},
            {"filter", "Отфильтровать"},
            {"average", "Среднее число комнат"},
            {"info", "Информация"},
            {"history", "История"},
            {"help", "Помощь"},
            {"execute_script", "Исполнить скрипт"},
            {"id", "id"},
            {"name", "название"},
            {"x", "Координата x"},
            {"y", "Координата y"},
            {"creationDate", "Дата создания"},
            {"area", "Площадь"},
            {"numberOfRooms", "Количество комнат"},
            {"centralHeating", "Наличие отопления"},
            {"view", "Вид из окон"},
            {"transport", "Уровень транспортного шума"},
            {"houseName", "Имя дома"},
            {"year", "Год основания дома"},
            {"numberOfFloors", "Количество этажей"},
            {"owner", "Владелец"}

    };
}