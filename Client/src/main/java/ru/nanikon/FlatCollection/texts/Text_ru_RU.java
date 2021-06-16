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
            {"englishLanguage", "Английский"},
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
            {"name", "Название"},
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
            {"owner", "Владелец"},
            {"flat", "Квартира"},
            {"as_who", "Вы зашли как: "},
            {"log_out_message", "Вы успешно вышли из (окна) аккаунта"},
            {"cancel", "Отмена"},
            {"doYouWantToExit", "Вы точно хотите покинуть приложение?"},
            {"clear_null", "Не удалось очистить коллекцию: у вас в ней нет ни одного элемента"},
            {"clear_success", "Коллекция успешно очищена (но лишь от ваших элементов)"},
            {"clear_fail", "Не удалось очистить коллекцию"},
            {"login_password_wrong", "Ой, вы там в приложении что-то напортачили и мы то ли логин не найдем, то ли пароль для него не тот. Перезайдите нормально!"},
            {"continueOK", "Ок"},
            {"info_title", "Информация о коллекции:"},
            {"type_info", "тип хранимых объектов: "},
            {"size_info", "количество элементов: "},
            {"help_title", "Справка по командам скрипта:"},
            {"info_help", "вывести в стандартный поток вывода информацию о коллекции (тип, количество элементов)"},
            {"help_help", "вывести справку по доступным командам"},
            {"add_help", "добавить новый элемент в коллекцию"},
            {"average_help", "вывести среднее значение поля numberOfRooms для всех элементов коллекции"},
            {"clear_help", "очистить коллекцию"},
            {"exit_help", "завершить программу"},
            {"filter_help", "вывести элементы, значение поля view которых меньше заданного"},
            {"history_help", "вывести последние 8 команд (без их аргументов)"},
            {"insert_help", "добавить новый элемент в заданную позицию"},
            {"login_help", "авторизироваться"},
            {"logout_help", "выйти из системы, но не завершать работу приложения"},
            {"register_help", "зарегистрировать пользователя с введенным логином и паролем"},
            {"remove_transport_help", "удалить из коллекции один элемент, значение поля transport которого эквивалентно заданному"},
            {"remove_id_help", "удалить элемент из коллекции по его id"},
            {"save_help", "сохранить коллекцию в файл"},
            {"show_help", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении"},
            {"sort_help", "отсортировать коллекцию в естественном порядке"},
            {"update_help", "обновить значение элемента коллекции, id которого равен заданному"},
            {"add_title", "Добавление объекта"},
            {"add_success", "Элемент успешно добавлен в коллекцию"},
            {"add_wrong", "При добавлении объекта произошла ошибка"},
            {"not_null", "не может быть пустым"},
            {"must_double_number", "должно быть вещественным числом"},
            {"must_int_number", "должно быть целым числом"},
            {"must_int_plus_number", "должно быть натуральным числом"},
            {"get_id", "Введите id:"},
            {"delete_success", "Элемент успешно удален"},
            {"delete_not_found", "Не удалось удалить элемент: он не принадлежит вам, или не найден стаким id"},
            {"delete_wrong", "Не удалось удалить элемент по неизвестной причине. Попробуйте позднее"},
            {"update_not_found", "Элемента с таким id не существует или он не ваш (ха-ха)"},
            {"update_success", "Элемент успешно обновлен"},
            {"update_wrong", "Не удалось обносить элемент по непонятной причине. Повторите попытку позднее"},
            {"get_transport", "Введите транспорт"},
            {"not_found_flat_transport", "У вас нет элемента с таким видом транспорта"}




    };
}