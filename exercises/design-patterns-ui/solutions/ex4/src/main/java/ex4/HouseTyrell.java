package ex4;

public class HouseTyrell implements House {
    @Override
    public Member createMember() {
        return new TyrellMember();
    }

    @Override
    public Bastard createBastard() {
        return new TyrellBastard();
    }
}
