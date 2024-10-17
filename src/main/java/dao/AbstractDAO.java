package dao;

import model.Client;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import static dao.ConnectionFactory.getConnection;

/**
 * The AbstractDAO class provides generic data access operations for CRUD (Create, Read, Update, Delete)
 * operations on database entities. It is designed to work with any Java bean class representing a database entity.
 *
 * @param <T> the type of entity handled by this DAO
 */

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    public AbstractDAO(Class<T> type) {
        this.type = type;
    }


    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }


    /**
     * Finds an entity by its ID in the database.
     *
     * @param id the ID of the entity to find
     * @return the entity with the specified ID, or null if not found
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "error" + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Finds all entities of the specified type in the database.
     *
     * @return a list of all entities of the specified type
     */
    public List<T> findAll() {
        List<T> resultList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                T instance = type.getDeclaredConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value = resultSet.getObject(field.getName());
                    field.set(instance, value);
                }
                resultList.add(instance);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            LOGGER.log(Level.WARNING, "Error", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return resultList;
    }

    /**
     * Inserts a new entity into the database.
     *
     * @param t The entity to insert into the database.
     * @throws SQLException If an SQL exception occurs during the operation.
     */
    public void insert(T t) throws SQLException {
        // Construct the insert query dynamically using reflection
        StringBuilder query = new StringBuilder("INSERT INTO warehousedb." + t.getClass().getSimpleName().toLowerCase(Locale.ROOT) + " (");
        StringBuilder values = new StringBuilder(" VALUES (");

        // Get the fields of the entity class
        Field[] fields = t.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            query.append(fieldName);
            values.append("?");
            if (i < fields.length - 1) {
                query.append(", ");
                values.append(", ");
            } else {
                query.append(")");
                values.append(")");
            }
        }
        query.append(values);

        // Execute the dynamic query
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(query.toString());

            // Set parameter values using reflection
            int parameterIndex = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(t);
                statement.setObject(parameterIndex++, value);
            }

            // Execute the insert operation
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, "Error", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.WARNING, "Error closing statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.WARNING, "Error closing connection", e);
                }
            }
        }
    }

    /**
     * Updates an existing entity in the database or inserts it if it does not exist.
     *
     * @param t The entity to update or insert into the database.
     * @throws SQLException If an SQL exception occurs during the operation.
     */
    public void update(T t) throws SQLException {
        // Construct the insert on duplicate key update query dynamically using reflection
        StringBuilder query = new StringBuilder("INSERT INTO warehousedb." + t.getClass().getSimpleName().toLowerCase(Locale.ROOT) + " (");
        StringBuilder values = new StringBuilder(" VALUES (");

        // Get the fields of the entity class
        Field[] fields = t.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            query.append(fieldName);
            values.append("?");
            if (i < fields.length - 1) {
                query.append(", ");
                values.append(", ");
            } else {
                query.append(")");
                values.append(")");
            }
        }
        query.append(values);

        // Append on duplicate key update clause
        query.append(" ON DUPLICATE KEY UPDATE ");
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            query.append(fieldName).append(" = VALUES(").append(fieldName).append(")");
            if (i < fields.length - 1) {
                query.append(", ");
            }
        }

        // Execute the dynamic query
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(query.toString());

            // Set parameter values using reflection
            int parameterIndex = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(t);
                statement.setObject(parameterIndex++, value);
            }

            // Execute the update operation
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, "Error", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.WARNING, "Error closing statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.WARNING, "Error closing connection", e);
                }
            }
        }

    }


    /**
     * Deletes an entity from the database.
     *
     * @param t The entity to delete from the database.
     * @throws SQLException If an SQL exception occurs during the operation.
     */
    public void delete(T t) throws SQLException {
        // Construct the DELETE query dynamically using reflection
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            String tableName = type.getSimpleName();
            String idFieldName = (t instanceof Client) ? "clientId" : "productId";

            String query = String.format("DELETE FROM warehousedb.%s WHERE %s=?", tableName.toLowerCase(Locale.ROOT), idFieldName);

            // Prepare and execute the DELETE statement
            statement = connection.prepareStatement(query);
            Field idField = t.getClass().getDeclaredField(idFieldName);
            idField.setAccessible(true);
            Object idValue = idField.get(t);

            statement.setObject(1, idValue);
            statement.executeUpdate();
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, "Error", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

}
