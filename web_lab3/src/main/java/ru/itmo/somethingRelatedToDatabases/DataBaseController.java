package ru.itmo.somethingRelatedToDatabases;

import ru.itmo.bean.Point;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseController {

    private Connection connection;

    public DataBaseController(Connection connection) {
        this.connection = connection;
    }

    public void addPoint(Point point) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Points (x, y, r, result, creationDate, executeTime) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setFloat(1, point.getX());
            statement.setString(2, point.getY());
            statement.setInt(3, point.getR());
            statement.setBoolean(4, point.getResult());
            statement.setDate(5, new java.sql.Date(point.getCreationDate().getTime()));
            statement.setLong(6, point.getExecuteTime());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Point> getPointsFromBD() {
        List<Point> points = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Points");
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                float x = resultSet.getFloat("x");
                String y = resultSet.getString("y");
                int r = resultSet.getInt("r");
                boolean result = resultSet.getBoolean("result");
                Date creationDate = resultSet.getDate("creationDate");
                long executeTime = resultSet.getLong("executeTime");
                Point point = new Point(id, x, y, r, result, creationDate, executeTime);
                points.add(point);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return points;
    }
}