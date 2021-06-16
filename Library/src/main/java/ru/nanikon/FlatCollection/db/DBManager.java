package ru.nanikon.FlatCollection.db;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import ru.nanikon.FlatCollection.commands.ServerAnswer;
import ru.nanikon.FlatCollection.data.Flat;
import ru.nanikon.FlatCollection.data.FlatBuilder;
import ru.nanikon.FlatCollection.data.House;
import ru.nanikon.FlatCollection.data.View;
import ru.nanikon.FlatCollection.exceptions.BooleanInputException;

import static java.sql.ResultSet.*;

public class DBManager {
    private final String URL;
    private final String login;
    private final String password;
    private Connection connection;
    private LinkedList<Flat> collection = new LinkedList<>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);


    public DBManager(String URL, String login, String password) {
        this.URL = URL;
        this.login = login;
        this.password = password;
    }

    public void connectToDatabase() throws SQLException {
        connection = DriverManager.getConnection(URL, login, password);
        connection.setAutoCommit(false);
    }

    public LinkedList<Flat> getCollection()  { return collection; }

    public ServerAnswer<String> registerUser(String login, String password) {
        String salt = generateSalt();
        Lock wlock = lock.writeLock();
        wlock.lock();
        ServerAnswer<String> result = new ServerAnswer<>();
        try (PreparedStatement statement = connection.prepareStatement(Requests.INSERT_USER.QUERY)) {
            statement.setString(1, login);
            statement.setString(2, encryptPassword(password + salt));
            statement.setString(3, salt);
            if (statement.executeUpdate() == 1) {
                connection.commit();
                result.setStatus(true);
                result.setAnswer("success_register");
            } else {
                connection.rollback();
                result.setStatus(false);
                result.setAnswer("register_problem");
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {
            }
            result.setStatus(false);
            result.setAnswer("wrong_login");
        } finally {
            wlock.unlock();
        }
        return result;
    }

    public String encryptPassword(String password) {
        String pepper = "lerug7^%q43r";
        return DigestUtils.md5Hex(pepper + password);
    }

    public String generateSalt() {
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRST1234567890!@#$%^*()_+".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public ServerAnswer<String> logIn(String login, String password) {
        Lock rlock = lock.readLock();
        rlock.lock();
        ServerAnswer<String> answer = new ServerAnswer<>();
        try (PreparedStatement statement = connection.prepareStatement(Requests.SELECT_USER.QUERY)) {
            statement.setString(1, login);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                if (result.getString("password").equals(encryptPassword(password + result.getString("salt")))) {
                    answer.setStatus(true);
                    answer.setMessage("success_logI");
                    answer.setAnswer(login);
                } else {
                    answer.setStatus(false);
                    answer.setMessage("wrong_password");
                }
            } else {
                answer.setStatus(false);
                answer.setMessage("not_found_login");
            }
        } catch (SQLException e) {
            answer.setStatus(false);
            answer.setMessage("logIn_problem");
            e.printStackTrace();
        } finally {
            rlock.unlock();
        }
        return answer;
    }

    public boolean chekUser(String login, String password) {
        Lock rlock = lock.readLock();
        rlock.lock();
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(Requests.SELECT_USER.QUERY)) {
            statement.setString(1, login);
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                if (results.getString("password").equals(encryptPassword(password + results.getString("salt")))) {
                    result = true;
                }
            }
        } catch (SQLException throwables) {
            result = false;
        } finally {
            rlock.unlock();
        }
        return result;
    }

    public boolean checkLogin(String login) {
        try (PreparedStatement statement = connection.prepareStatement(Requests.SELECT_USER.QUERY)) {
            statement.setString(1, login);
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException ignored) {}
    }

    public void save() {
        try {
            connection.commit();
        } catch (SQLException ignored) {
        }
    }

    public ServerAnswer<String> addFlat(Flat flat, String login) {
        int house_id;
        int view_id;
        int transport_id;
        int user_id;
        ServerAnswer<String> result = new ServerAnswer<>();
        Lock wlock = lock.writeLock();
        wlock.lock();
        try {
            house_id = getHouseId(flat.getHouse());
            if (house_id == -1) {
                addHouse(flat.getHouse());
                house_id = getHouseId(flat.getHouse());
            }

            PreparedStatement viewStatement = connection.prepareStatement(Requests.SELECT_VIEW.QUERY);
            viewStatement.setString(1, flat.getView().name());
            ResultSet view = viewStatement.executeQuery();
            view.next();
            view_id = view.getInt("id");

            PreparedStatement trStatement = connection.prepareStatement(Requests.SELECT_TRANSPORT.QUERY);
            trStatement.setString(1, flat.getTransport().name());
            ResultSet tr = trStatement.executeQuery();
            tr.next();
            transport_id = tr.getInt("id");

            PreparedStatement userStatement = connection.prepareStatement(Requests.SELECT_USER.QUERY);
            userStatement.setString(1, login);
            ResultSet user = userStatement.executeQuery();
            user.next();
            user_id = user.getInt("id");

            PreparedStatement flatStatement = connection.prepareStatement(Requests.INSERT_FLAT.QUERY);
            flatStatement.setString(1, flat.getName());
            flatStatement.setDouble(2, flat.getX());
            flatStatement.setDouble(3, flat.getY());
            flatStatement.setTimestamp(4, Timestamp.valueOf(flat.getCreationDate().toLocalDateTime()));
            flatStatement.setLong(5, flat.getArea());
            flatStatement.setInt(6, flat.getNumberOfRooms());
            flatStatement.setBoolean(7, flat.isCentralHeating());
            flatStatement.setInt(8, house_id);
            flatStatement.setInt(9, transport_id);
            flatStatement.setInt(10, view_id);
            flatStatement.setInt(11, user_id);
            flatStatement.executeUpdate();
            connection.commit();
            initialCollection();
            result.setStatus(true);
            //return "Элемент {" + flat.toLongString() + "} успешно добавлен в коллекцию";
            result.setAnswer("add_success");
        } catch (SQLException e) {
            //e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ignored) {}
            result.setStatus(false);
            result.setAnswer("add_wrong");
        } catch (Exception e) {
            result.setStatus(false);
            result.setAnswer("add_wrong");
        } finally {
            wlock.unlock();
        }
        return result;
    }

    public void addHouse(House house) throws SQLException {
        PreparedStatement houseStatement = connection.prepareStatement(Requests.INSERT_HOUSE.QUERY);
        if (house.getName() == null) {
            houseStatement.setNull(1, java.sql.Types.NULL);
        } else {
            houseStatement.setString(1, house.getName());
        }
        if (house.getYear() == null) {
            houseStatement.setNull(2, java.sql.Types.NULL);
        } else {
            houseStatement.setLong(2, house.getYear());
        }
        if (house.getNumberOfFloors() == null) {
            houseStatement.setNull(3, java.sql.Types.NULL);
        } else {
            houseStatement.setInt(3, house.getNumberOfFloors());
        }
        houseStatement.executeUpdate();
    }

    public int getHouseId(House house) {
        int result = -1;
        String request = "SELECT * FROM houses WHERE house_name ";
        if (house.getName() == null) {
            request += " is NULL AND year";
        } else {
            request += " = ? AND year";
        }
        if (house.getYear() == null) {
            request += " is NULL AND numberOfFloors";
        } else {
            request += " = ? AND numberOfFloors";
        }
        if (house.getNumberOfFloors() == null) {
            request += " is NULL";
        } else {
            request += " = ?";
        }
        try (PreparedStatement houseSelectStatement = connection.prepareStatement(request)) {
            int i = 1;
            if (!(house.getName() == null)) {
                houseSelectStatement.setString(i, house.getName());
                i++;
            }
            if (!(house.getYear() == null)) {
                houseSelectStatement.setLong(i, house.getYear());
                i++;
            }
            if (!(house.getNumberOfFloors() == null)) {
                houseSelectStatement.setInt(i, house.getNumberOfFloors());
            }
            ResultSet houseRes = houseSelectStatement.executeQuery();
            if (houseRes.next()) {
                result = houseRes.getInt("id");
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return result;
    }

    public String readAll() {
        String result = "";
        try (PreparedStatement flats = connection.prepareStatement(Requests.SELECT_FLAT.QUERY)) {
            connection.commit();
            result = loadCollection(flats.executeQuery());
        } catch (SQLException e) {
            result = "Прочитать данные из БД не удалось, попробуйте позднее";
        } catch(Exception e) {
            result = "Там это, БД сломали, глянь посмотри: " + e.getMessage();
        }
        return result;
    }

    public String readSort() {
        String result = "";
        try (PreparedStatement flats = connection.prepareStatement(Requests.SELECT_SORT_FLAT.QUERY)) {
            result = loadCollection(flats.executeQuery());
            connection.commit();
        } catch (SQLException e) {
            result = "Прочитать данные из БД не удалось, попробуйте позднее";
        } catch(Exception e) {
            result = "Там это, БД сломали, глянь посмотри: " + e.getMessage();
        }
        return result;
    }

    public String readFilter(String filter) {
        String result = "";
        try (PreparedStatement flats = connection.prepareStatement(Requests.SELECT_FILTER_FLAT.QUERY)) {
            flats.setString(1, filter);
            result = loadCollection(flats.executeQuery());
            connection.commit();
        } catch (SQLException e) {
            result = "Прочитать данные из БД не удалось, попробуйте позднее";
        } catch(Exception e) {
            result = "Там это, БД сломали, глянь посмотри: " + e.getMessage();
        }
        return result;
    }

    public String viewFilteredInfo(View view) {
        Lock rlock = lock.readLock();
        rlock.lock();
        StringBuilder result = new StringBuilder();
        for (Flat flat : collection.stream().filter((flat) -> flat.getView().compareTo(view) < 0).sorted((flat1, flat2) -> (int) (flat1.getArea() - flat2.getArea())).collect(Collectors.toList())) {
            result.append(flat.toLongString()).append("\n");
        }
        rlock.unlock();
        return result.toString().trim();
    }

    public void initialCollection() throws SQLException, BooleanInputException {
        PreparedStatement flats = connection.prepareStatement(Requests.SELECT_FLAT.QUERY);
        String result = loadCollection(flats.executeQuery());
        connection.commit();
    }

    public String loadCollection(ResultSet result1) throws SQLException, BooleanInputException {
        collection.clear();
        try {
            while (result1.next()) {
                FlatBuilder builder = new FlatBuilder();
                builder.reset();
                builder.setId(result1.getString("id"));
                builder.setName(result1.getString("name"));
                builder.setX(result1.getString("x"));
                builder.setY(result1.getString("y"));
                builder.setCreationDate(ZonedDateTime.ofInstant(result1.getTimestamp("creationdate").toInstant(), ZoneId.of("UTC")));
                builder.setArea(result1.getString("area"));
                builder.setNumberOfRooms(result1.getString("numberofrooms"));
                builder.setCentralHeating(result1.getBoolean("centralheating") ? "+" : "-");
                builder.setHouseName(result1.getString("house_name"));
                builder.setYear(result1.getString("year"));
                builder.setNumberOfFloors(result1.getString("numberoffloors"));
                builder.setView(result1.getString("view_value"));
                builder.setTransport(result1.getString("transport_value"));
                builder.setOwner(result1.getString("login"));
                collection.add(builder.getResult());
            }
        } catch (Exception e) {
            throw e;
        }
        return toLongString();
    }

    public String toLongString() {
        Lock rlock = lock.readLock();
        rlock.lock();
        StringBuilder info = new StringBuilder();
        for (Flat flat: collection) {
            info.append(flat.toLongString()).append("\n");
        }
        String result = info.toString().trim();
        rlock.unlock();
        return result;
    }

    public String sortCollection() {
        Lock rlock = lock.readLock();
        rlock.lock();
        Collections.sort(collection);
        rlock.unlock();
        return toLongString();
    }

    public ServerAnswer<String> clear(String login) {
        ServerAnswer<String> result = new ServerAnswer<>();
        Lock wlock = lock.writeLock();
        wlock.lock();
        try (PreparedStatement results = connection.prepareStatement(Requests.CLEAR.QUERY)) {
            results.setString(1, login);
            if (results.executeUpdate() == 0) {
                result.setStatus(false);
                result.setAnswer("clear_null");
            } else {
                result.setStatus(true);
                result.setAnswer("clear_success");
            }
            connection.commit();
            initialCollection();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {}
            result.setStatus(false);
            result.setAnswer("clear_fail");
        } catch (Exception e) {
            result.setStatus(false);
            result.setAnswer("clear_fail");
        } finally {
            wlock.unlock();
        }
        return result;
    }

    public String getAverage() {
        Lock rlock = lock.readLock();
        rlock.lock();
        try (PreparedStatement flats = connection.prepareStatement(Requests.AVERAGE_ROOMS.QUERY)) {
            ResultSet results = flats.executeQuery();
            results.next();
            return String.valueOf(results.getDouble("average_room"));
        } catch (SQLException e) {
            return "Прочитать данные из БД не удалось, попробуйте позднее";
        } catch(Exception e) {
            return "Там это, БД сломали, глянь посмотри: " + e.getMessage();
        } finally {
            rlock.unlock();
        }
    }

    public int getAverageNumberOfRooms() {
        Lock rlock = lock.readLock();
        rlock.lock();
        if (getSize().equals("0")) {
            return 0;
        }
        long result = collection.stream().mapToLong(Flat::getNumberOfRooms).sum();
        result = result / Integer.parseInt(getSize());
        rlock.unlock();
        return (int) result;
    }

    public String getSize() {
        return String.valueOf(collection.size());
    }

    public Flat getById(int id) {
        Flat result = null;
        for (Flat flat : collection) {
            if (flat.getId() == id) {
                result = flat;
                break;
            }
        }
        return result;
    }

    public ServerAnswer<String> deleteById(int id, String login) {
        ServerAnswer<String> result = new ServerAnswer<>();
        Lock wlock = lock.writeLock();
        wlock.lock();
        try (PreparedStatement results = connection.prepareStatement(Requests.DELETE_FLAT.QUERY)) {
            results.setInt(1, id);
            results.setString(2, login);
            if (results.executeUpdate() == 0) {
                result.setStatus(false);
                result.setAnswer("delete_not_found");
            } else {
                result.setStatus(true);
                result.setAnswer("delete_success");
            }
            connection.commit();
            initialCollection();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {}
            result.setStatus(false);
            result.setAnswer("delete_wrong");
        } catch (Exception e) {
            result.setStatus(false);
            result.setAnswer("delete_wrong");
        } finally {
            wlock.unlock();
        }
        return result;
    }

    public ServerAnswer<String> deleteByTransport(String transport, String login) {
        ServerAnswer<String> result = new ServerAnswer<>();
        Lock wlock = lock.writeLock();
        wlock.lock();
        try {
            PreparedStatement flats = connection.prepareStatement(Requests.SELECT_TRANSPORT_FLAT.QUERY);
            flats.setString(1, login);
            flats.setString(2, transport);
            ResultSet result1 = flats.executeQuery();
            if (!result1.next()) {
                result.setStatus(false);
                result.setAnswer("not_found_flat_transport");
            } else {
                PreparedStatement result2 = connection.prepareStatement(Requests.DELETE_FLAT.QUERY);
                result2.setInt(1, result1.getInt("id"));
                result2.setString(2, login);
                if (result2.executeUpdate() == 0) {
                    result.setStatus(false);
                    result.setAnswer("delete_not_found");
                } else {
                    result.setStatus(true);
                    result.setAnswer("delete_success");
                }
                connection.commit();
                initialCollection();
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {}
            result.setStatus(false);
            result.setAnswer("delete_wrong");
        } catch (Exception e) {
            result.setStatus(false);
            result.setAnswer("delete_wrong");
        } finally {
            wlock.unlock();
        }
        return result;
    }

    public String update(int id, String login, FlatBuilder builder) {
        String result = "";
        Lock wlock = lock.writeLock();
        wlock.lock();
        try (PreparedStatement flatStatement = connection.prepareStatement(Requests.SELECT_FLAT_ID.QUERY, TYPE_SCROLL_INSENSITIVE, CONCUR_UPDATABLE)) {
            flatStatement.setInt(1, id);
            flatStatement.setString(2, login);
            ResultSet flat = flatStatement.executeQuery();
            if (!flat.next()) { return "Элемента с таким id не существует или он не ваш (ха-ха)"; }
            HashSet<String> newFields = builder.getChange();
            System.out.println(flat.getInt("id"));
            //flat.updateInt("flat_id", id);
            if (newFields.contains("name")) { flat.updateString("name", builder.getName()); }
            if (newFields.contains("x")) { flat.updateDouble("x", builder.getX()); }
            if (newFields.contains("y")) { flat.updateDouble("y", builder.getY()); }
            flat.updateTimestamp(4, Timestamp.valueOf(builder.getCreationDate().toLocalDateTime()));
            if (newFields.contains("area")) { flat.updateLong("area", builder.getArea()); }
            if (newFields.contains("numberOfRooms")) { flat.updateInt("numberofrooms", builder.getNumberOfRooms()); }
            if (newFields.contains("centralHeating")) { flat.updateBoolean(7, builder.isCentralHeating()); }
            if (newFields.contains("view")) {
                PreparedStatement viewStatement = connection.prepareStatement(Requests.SELECT_VIEW.QUERY);
                viewStatement.setString(1, builder.getView().name());
                ResultSet view = viewStatement.executeQuery();
                view.next();
                int view_id = view.getInt("id");
                flat.updateInt("view_id", view_id);
            }
            if (newFields.contains("transport")) {
                PreparedStatement trStatement = connection.prepareStatement(Requests.SELECT_TRANSPORT.QUERY);
                trStatement.setString(1, builder.getTransport().name());
                ResultSet tr = trStatement.executeQuery();
                tr.next();
                int transport_id = tr.getInt("id");
                flat.updateInt("transport_id", transport_id);
            }
            flat.updateRow();
            result = "Элемент успешно обновлен";
            connection.commit();
            initialCollection();
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            result = "Не удалось обносить элемент по непонятной причине. Повторите попытку позднее";
        } catch (Exception e) {
            return "Там это, БД сломали, глянь посмотри: " + e.getMessage();
        } finally {
            wlock.unlock();
        }
        return result;
    }

    public ServerAnswer<String> updateById(int id, String login, FlatBuilder builder) {
        ServerAnswer<String> result = new ServerAnswer<>();
        Lock wlock = lock.writeLock();
        wlock.lock();
        try (PreparedStatement statement = connection.prepareStatement(Requests.UPDATE_FLAT.QUERY)) {
            Flat oldFlat = getById(id);
            HashSet<String> newFields = builder.getChange();
            statement.setString(1, newFields.contains("name") ? builder.getName() : oldFlat.getName());
            statement.setDouble(2, newFields.contains("x") ? builder.getX() : oldFlat.getX());
            statement.setDouble(3, newFields.contains("y") ? builder.getX() : oldFlat.getX());
            statement.setLong(4, newFields.contains("area") ? builder.getArea() : oldFlat.getArea());
            statement.setInt(5, newFields.contains("numberOfRooms") ? builder.getNumberOfRooms() : oldFlat.getNumberOfRooms());
            statement.setBoolean(6, newFields.contains("centralHeating") ? builder.isCentralHeating() : oldFlat.isCentralHeating());
            statement.setString(7, newFields.contains("transport") ? builder.getTransport().name() : oldFlat.getTransport().name());
            statement.setString(8, newFields.contains("view") ? builder.getView().name() : oldFlat.getView().name());
            if (!newFields.contains("nameHouse")) { builder.setHouseName(String.valueOf(oldFlat.getHouseName())); }
            if (!newFields.contains("year")) { builder.setYear(String.valueOf(oldFlat.getYear())); }
            if (!newFields.contains("numberOfFloors")) { builder.setNumberOfFloors(String.valueOf(oldFlat.getNumberOfFloors())); }
            House house = builder.getHouseBuilder().getResult();
            int house_id = getHouseId(house);
            if (house_id == -1) {
                addHouse(house);
                house_id = getHouseId(house);
            }
            statement.setInt(9, house_id);
            statement.setInt(10, id);
            statement.setString(11, login);
            if (statement.executeUpdate() == 0) {
                result.setStatus(false);
                result.setAnswer("update_not_found");
            } else {
                result.setStatus(true);
                result.setAnswer("update_success");
            }
            connection.commit();
            initialCollection();
        } catch (SQLException throwables) {
            result.setStatus(false);
            result.setAnswer("update_wrong");
            try {
                connection.rollback();
            } catch (SQLException ignored) {
            }
        } catch (NullPointerException e) {
            result.setStatus(false);
            result.setAnswer("update_not_found");
        } catch (Exception e) {
            result.setStatus(false);
            result.setAnswer("update_wrong");
            e.printStackTrace();
        } finally {
            wlock.unlock();
        }
        return result;
    }
}
