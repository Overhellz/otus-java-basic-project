package com.rodiond26.overhellz.otus.basic.repository.impl;

import com.rodiond26.overhellz.otus.basic.config.DbConfig;
import com.rodiond26.overhellz.otus.basic.model.Item;
import com.rodiond26.overhellz.otus.basic.repository.ItemRepository;
import com.rodiond26.overhellz.otus.basic.repository.pagination.Page;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.rodiond26.overhellz.otus.basic.repository.script.ItemCrudSqlScript.*;

public class ItemJdbcRepositoryImpl implements ItemRepository {

    private final DbConfig config;
    private Connection connection;

    public ItemJdbcRepositoryImpl(DbConfig config) throws SQLException {
        this.config = config;
        connection = getConnection();
        if (connection != null) {
            System.out.println("Успешное подключение к базе данных: " + config.getDatabaseUrl()); // TODO logger
        }
    }

    @Override
    public List<Item> findAll(Page page) {
        List<Item> resultList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ITEMS_WITH_LIMIT_AND_OFFSET)) {
            preparedStatement.setInt(1, page.getPageSize());
            preparedStatement.setInt(2, getOffset(page));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    resultList.add(getItem(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println("Произошла ошибка при получении всех пользователей"); // TODO fix
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Optional<Item> findById(Long itemId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ITEM_BY_ID)) {
            preparedStatement.setLong(1, itemId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getItem(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println("Произошла ошибка при получении пользователя по имени: " + "name"); // TODO fix
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Item> findByTitle(String title, Page page) {
        List<Item> resultList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ITEM_BY_TITLE)) {
            preparedStatement.setString(1, title);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    resultList.add(getItem(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println("Произошла ошибка при получении всех пользователей"); // TODO fix
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<Item> findByPrice(BigDecimal min, BigDecimal max, Page page) {
        List<Item> resultList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ITEM_BY_PRICE)) {
            preparedStatement.setBigDecimal(1, min);
            preparedStatement.setBigDecimal(2, max);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    resultList.add(getItem(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println("Произошла ошибка при получении всех пользователей"); // TODO fix
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<Item> findByTitleAndPrice(String title, BigDecimal min, BigDecimal max, Page page) {
        List<Item> resultList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ITEM_BY_TITLE_AND_PRICE)) {
            preparedStatement.setString(1, title);
            preparedStatement.setBigDecimal(2, min);
            preparedStatement.setBigDecimal(3, max);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    resultList.add(getItem(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println("Произошла ошибка при получении всех пользователей"); // TODO fix
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public boolean create(Item item) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ITEM)) {
            preparedStatement.setString(1, item.getTitle());
            preparedStatement.setBigDecimal(2, item.getPrice());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Добавлен новый пользователь: " + "userProfile"); // TODO fix
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Произошла ошибка при добавлении пользователя: " + "userProfile"); // TODO fix
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Item item) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ITEM_BY_ID)) {
            preparedStatement.setString(1, item.getTitle());
            preparedStatement.setBigDecimal(2, item.getPrice());
            preparedStatement.setLong(3, item.getId());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Добавлен новый пользователь: " + "userProfile"); // TODO fix
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Произошла ошибка при добавлении пользователя: " + "userProfile"); // TODO fix
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ITEM_BY_ID)) {
            preparedStatement.setLong(1, id);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Добавлен новый пользователь: " + "userProfile"); // TODO fix
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Произошла ошибка при добавлении пользователя: " + "userProfile"); // TODO fix
            e.printStackTrace();
        }
        return false;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                config.getDatabaseUrl(),
                config.getUserName(),
                config.getUserPassword()
        );
    }

    private int getOffset(Page page) {
        return (page.getPageNum() - 1) * page.getPageSize();
    }

    private Item getItem(ResultSet resultSet) throws SQLException {
        return new Item(
                resultSet.getLong("item_id"),
                resultSet.getString("title"),
                resultSet.getBigDecimal("price")
        );
    }
}
