package ch.epfl.sweng.dp1.solutions.ex6;

public class HouseStark implements House {
    @Override
    public Member createMember() {
        return new StarkMember();
    }

    @Override
    public Bastard createBastard() {
        return new StarkBastard();
    }
}
