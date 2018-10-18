package ch.epfl.sweng.dp1.solutions.ex3;

public class Screen implements Observer {

    private void display(String data){
        System.out.println("Screen - New data : " + data);
    }

    @Override
    public void update(String data) {
        this.display(data);
    }
}
