# Лабораторная работа №8. Финальная
[Предыдущая лабораторная работа](https://github.com/nanikon/lab-7)
## Задание:
Доработать программу из лабораторной работы №7 следующим образом:

Заменить консольный клиент на клиент с графическим интерфейсом пользователя(GUI). 
В функционал клиента должно входить:

1. Окно с авторизацией/регистрацией.
2. Отображение текущего пользователя.
3. Таблица, отображающая все объекты из коллекции
    1. Каждое поле объекта - отдельная колонка таблицы.
    2. Строки таблицы можно фильтровать/сортировать по значениям любой из колонок. Сортировку и фильтрацию значений столбцов реализовать с помощью Streams API.
4. Поддержка всех команд из предыдущих лабораторных работ.
5. Область, визуализирующую объекты коллекции
    1. Объекты должны быть нарисованы с помощью графических примитивов с использованием Graphics, Canvas или аналогичных средств графической библиотеки.
    2. При визуализации использовать данные о координатах и размерах объекта.
    3. Объекты от разных пользователей должны быть нарисованы разными цветами.
    4. При нажатии на объект должна выводиться информация об этом объекте.
    5. При добавлении/удалении/изменении объекта, он должен автоматически появиться/исчезнуть/измениться  на области как владельца, так и всех других клиентов. 
    6. При отрисовке объекта должна воспроизводиться согласованная с преподавателем анимация.
6. Возможность редактирования отдельных полей любого из объектов (принадлежащего пользователю). Переход к редактированию объекта возможен из таблицы с общим списком объектов и из области с визуализацией объекта.
7. Возможность удаления выбранного объекта (даже если команды remove ранее не было).
## Индивидуальная часть задания
1. Интерфейс должен быть реализован с помощью библиотеки JavaFX
2. Графический интерфейс клиентской части должен поддерживать русский, словацкий, датский и испанский (Эквадор) языки / локали. Должно обеспечиваться корректное отображение чисел, даты и времени в соответстии с локалью. 
Переключение языков должно происходить без перезапуска приложения. Локализованные ресурсы должны храниться в классе. (Реализованы только русский и английский языки)