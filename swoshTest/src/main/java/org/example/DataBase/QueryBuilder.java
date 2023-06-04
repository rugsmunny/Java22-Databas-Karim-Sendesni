package org.example.DataBase;

public class QueryBuilder {
    private final StringBuilder query;

    public QueryBuilder() {
        query = new StringBuilder();
    }

    public QueryBuilder select(String table, String... columns) {

        query.append("SELECT ");

        if (columns.length > 0) {
            setColumns(columns);
        } else {
            query.append("*");
        }

        query.append(" FROM ").append(table);
        return this;
    }

    public void insertInto(String table, String... columns) {

        query.append("INSERT INTO ").append(table).append(" (");

        setColumns(columns);

        query.append(") VALUES (");

        setValuePlaceholders(columns);

        query.append(")");
    }

    public QueryBuilder update(String table, String... columns) {

        query.append("UPDATE ").append(table).append(" SET ");

        setColumnValuePlaceholders(false, false, columns);
        return this;
    }

    public QueryBuilder updateAccount(String table, String column, String type) {

        query.append("UPDATE ").append(table).append(" SET ").append(column)
                .append(" = ").append(column).append(type).append(" ? ");

        return this;
    }

    public QueryBuilder delete(String table) {

        query.append("DELETE FROM ").append(table);
        return this;
    }

    public QueryBuilder where(Boolean sub, String... columns) {

        query.append(" WHERE ");

        setColumnValuePlaceholders(sub,true, columns);
        return this;
    }

    public void subQuery(String subQuery) {
        query.append("(").append(subQuery).append(")");
    }
    private void setValuePlaceholders(String... columns) {
        for (int i = 0; i < columns.length; i++) {
            query.append("?");
            if (i < columns.length - 1) {
                query.append(", ");
            }
        }
    }

    private void setColumns(String... columns) {
        for (int i = 0; i < columns.length; i++) {
            query.append(columns[i]);
            if (i < columns.length - 1) {
                query.append(", ");
            }
        }
    }

    private void setColumnValuePlaceholders(Boolean sub, Boolean and, String... columnsAndValues) {
        for (int i = 0; i < columnsAndValues.length; i++) {
            String column = columnsAndValues[i];

            if(!sub){
                query.append(column).append(" = ").append("?");

                if (i < columnsAndValues.length - 1) {
                    if (and) {
                        query.append(" AND ");
                    } else {
                        query.append(", ");
                    }
                }
            }


        }
    }

    public String build() {
        String queryString = query.toString();
        query.setLength(0);
        return queryString;
    }
}