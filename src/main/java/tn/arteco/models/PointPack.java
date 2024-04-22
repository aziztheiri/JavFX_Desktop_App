package tn.arteco.models;

public class PointPack {
    int points;
    int price;
    String name;

    public PointPack(int points, int price, String name) {
        this.points = points;
        this.price = price;
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
