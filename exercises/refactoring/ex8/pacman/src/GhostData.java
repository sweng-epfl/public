public class GhostData {
    private int x;
    private int y;
    private ghostType type;

    public GhostData(int x,int y,ghostType type){
        this.x = x;
        this.y = y;
        this.type = type;
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

    public ghostType getType() {
        return type;
    }

    public void setType(ghostType type) {
        this.type = type;
    }
}


