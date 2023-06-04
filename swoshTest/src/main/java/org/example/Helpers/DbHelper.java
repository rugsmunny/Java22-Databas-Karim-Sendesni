package org.example.Helpers;

import org.example.DataBase.DatabaseConnection;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.Helpers.ViewHelper.sout;
import static org.example.Helpers.ViewHelper.warning;

public class DbHelper {

    private static DbHelper singleton;

    private DbHelper() {

    }

    public static synchronized DbHelper getInstance() {
        if (singleton == null) {
            singleton = new DbHelper();
        }
        return singleton;
    }

    public Connection getConnection() throws SQLException {

        return DatabaseConnection.getInstance().getConnection();
    }


    public boolean executeUpdate(String sql, Object... parameters) {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (int i = 0; i < parameters.length; i++) {

                preparedStatement.setObject(i + 1, parameters[i]);
            }

            int x = preparedStatement.executeUpdate();
            return x > 0;

        } catch (SQLException sqlEx) {
            warning();
            sout("Execute update exception -> " + sqlEx.getMessage());
        }

        return false;
    }

    public <T> List<T> executeQuery(String sql, Class<T> targetType, Object... parameters) {
        List<T> list = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }


            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {
                list.add(processResultSet(rs, targetType));

            }
            return list;

        } catch (SQLException sqlEx) {
            warning();
            sout("Exception -> " + sqlEx);
        }

        return null;
    }

    public <T> T processResultSet(ResultSet resultSet, Class<T> modelClass) {

        try {

            T model = modelClass.getDeclaredConstructor().newInstance();

            for (Field field : modelClass.getDeclaredFields()) {

                field.setAccessible(true);
                field.set(model, resultSet.getObject(field.getName()));

            }

            return model;

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException | SQLException e) {
            warning();
            throw new RuntimeException("Failed to process resultSet, see exception -> ", e);
        }
    }


}
