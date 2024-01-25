package ru.itmo.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Date;


@Named
@SessionScoped
public class Point implements Serializable {
    private Long id;
    private float x;
    private String y;
    private int r;
    private boolean result;
    private Date creationDate;
    private long executeTime;

    public Point() {
    }

    public Point(long id, float x, String y, int r, boolean result, Date creationDate, long executeTime) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.creationDate = creationDate;
        this.executeTime = executeTime;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }

    public float getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public boolean getResult() {
        return result;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    @Override
    public String toString() {
        return "Point{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", isHit=" + result +
                ", creationDate=" + creationDate +
                ", executeTime=" + executeTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
