import java.awt.*;
import java.util.ArrayList;

public class MapData {

    private int x;
    private int y;
    private int[][] map;
    private Point pacmanPosition;
    private Point ghostBasePosition;
    private boolean isCustom;
    private ArrayList<Food> foodPositions;
    private ArrayList<PowerUpFood> pufoodPositions;
    private ArrayList<TeleportTunnel> teleports;
    private ArrayList<GhostData> ghostsData;

    public MapData(){
        foodPositions = new ArrayList<>();
        pufoodPositions = new ArrayList<>();
        teleports = new ArrayList<>();
        ghostsData = new ArrayList<>();
    }

    public MapData(int x,int y){
        this.x = x;
        this.y = y;

        foodPositions = new ArrayList<>();
        pufoodPositions = new ArrayList<>();
        teleports = new ArrayList<>();
        ghostsData = new ArrayList<>();
    }

    public MapData(int x, int y,int[][] map,Point pacPosition){
        this.x = x;
        this.y = y;
        this.map = map;
        pacmanPosition = pacPosition;

        foodPositions = new ArrayList<>();
        pufoodPositions = new ArrayList<>();
        teleports = new ArrayList<>();
        ghostsData = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public Point getPacmanPosition() {
        return pacmanPosition;
    }

    public void setPacmanPosition(Point pacmanPosition) {
        this.pacmanPosition = pacmanPosition;
    }

    public Point getGhostBasePosition() {
        return ghostBasePosition;
    }

    public void setGhostBasePosition(Point ghostBasePosition) {
        this.ghostBasePosition = ghostBasePosition;
    }

    public ArrayList<Food> getFoodPositions() {
        return foodPositions;
    }

    public ArrayList<PowerUpFood> getPufoodPositions() {
        return pufoodPositions;
    }

    public ArrayList<TeleportTunnel> getTeleports() {
        return teleports;
    }

    public ArrayList<GhostData> getGhostsData() {
        return ghostsData;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }
}
