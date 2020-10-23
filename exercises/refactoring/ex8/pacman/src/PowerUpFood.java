import java.awt.*;

public class PowerUpFood {

    public Point position;

    public PowerUpFood(int x,int y,int type){
        position = new Point(x,y);
        this.type = type;
    }

    public int type; //0-4

}
