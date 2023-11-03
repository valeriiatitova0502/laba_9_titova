package org.example;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class AnnotationProcessor {

    public static void createTable(Object cl) throws Exception {
        Class<?> clClass = cl.getClass();
        if (!clClass.isAnnotationPresent(Table.class)) {
            throw new Exception("Класс не содержит аннотации @Table");
        }
        Table table = clClass.getAnnotation(Table.class);
        StringBuilder sql = new StringBuilder("CREATE TABLE " + table.title() + " (");
        Field[] fields = clClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                sql.append(field.getName()).append(" ");
                if (field.getType() == int.class) {
                    sql.append("INT");
                } else if (field.getType() == String.class || field.getType().isEnum()) {
                    sql.append("TEXT");
                }
                else {
                    sql.append("TEXT");
                }
                sql.append(",");
            }
        }
        if (sql.charAt(sql.length() - 1) == ',') {
            sql.deleteCharAt(sql.length() - 1);
        }
        sql.append(");");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:lr9.db");
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS " + table.title() + ";");
            statement.execute(sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void insertIntoTable(Object cl) {
        Class<?> clClass = cl.getClass();
        if (!clClass.isAnnotationPresent(Table.class)) {
            throw new RuntimeException("Класс не содержит аннотации @Table");
        }
        String tableName = clClass.getAnnotation(Table.class).title();
        Field[] fields = clClass.getDeclaredFields();
        StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " (");
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                query.append(field.getName()).append(",");
            }
        }
        if (query.charAt(query.length() - 1) == ',') {
            query.deleteCharAt(query.length() - 1);
        }
        query.append(") VALUES (");
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                try {
                    query.append("'");
                    if (field.getType() == int.class) {
                        query.append(field.getInt(cl));
                    } else if (field.getType() == String.class || field.getType().isEnum()) {
                        query.append(field.get(cl));
                    }
                    query.append("',");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        if (query.charAt(query.length() - 1) == ',') {
            query.deleteCharAt(query.length() - 1);
        }
        query.append(")");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:lr9.db");
            Statement statement = connection.createStatement();
            statement.execute(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}