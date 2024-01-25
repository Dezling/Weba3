package ru.itmo.bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.validation.ValidationException;
import org.hibernate.HibernateError;
import ru.itmo.somethingRelatedToDatabases.DataBaseController;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.lang.Math.abs;

@Named
@ApplicationScoped
public class PointController implements Serializable {
    private List<Point> pointList;
    List<Integer> R_LIST = new ArrayList(Arrays.asList(1, 2, 3, 4, 5));

    private final DataBaseController dataBaseController;

    public List<Point> getPointList() {
        return pointList;
    }

    public PointController() {
        pointList = new ArrayList<>();
        Connection connection = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/points";
            String username = "s373319";
            String password = "Jqbp=3890";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.dataBaseController = new DataBaseController(connection);
    }

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }

    public void creatPoint(float x, String y, int r) {
        try {
            if (anotherValidate(r, x, y)) {
                float omegaY = Float.parseFloat(y);
                Point newPoint = new Point();
                newPoint.setX(x);
                newPoint.setY(y);
                newPoint.setR(r);
                LocalDateTime localDateTime = LocalDateTime.now();
                Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                newPoint.setCreationDate(date);
                long startExecute = System.nanoTime();
                newPoint.setResult(isHit(x, omegaY, r));
                long endExecute = System.nanoTime();
                newPoint.setExecuteTime(endExecute - startExecute);
                pointList.add(newPoint);
                dataBaseController.addPoint(newPoint);
            } else {
                throw new ValidationException("Ошибка валидации, исправьте, пожалуйста.");
            }
        } catch (HibernateError ignored) {
        }
    }

    public static boolean isHit(float x, float y, float r) {
        return isOrangeZone(x, y, r) ||
                isBlueZone(x, y, r) ||
                isRedZone(x, y, r);
    }

    public static boolean isOrangeZone(float x, float y, float r) {
        return x >= 0 && x <= r && y >= 0 && y <= r && (x * x + y * y <= (r) * (r));
    }

    public static boolean isBlueZone(float x, float y, float r) {
        return x <= 0 && x >= -r && y >= 0 && y <= r;
    }

    public static boolean isRedZone(float x, float y, float r) {
        return x <= 0 && x >= -r / 2 && y <= 0 && y >= -r && x >= -(r - abs(y)) / 2;
    }

    public String getJsonPointList() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        pointList.clear();
        pointList.addAll(dataBaseController.getPointsFromBD());
        String json = objectMapper.writeValueAsString(pointList);
        return json;
    }

    public boolean anotherValidate(int r, float x, String y) {
        return (validateR(r) || validateX(x) || validateY(y));
    }

    public boolean validateR(int r) {
        return R_LIST.contains(r);
    }

    public boolean validateX(float x) {
        if (x > 2 || x < -2) {
            return false;
        }
        return true;
    }

    public boolean validateY(String y) {
        try {
            float omegaY = Float.parseFloat(y);
            if (omegaY > 3 || omegaY < -5) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}