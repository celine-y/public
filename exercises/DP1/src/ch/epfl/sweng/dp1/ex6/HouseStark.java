package ch.epfl.sweng.dp1.ex6;

public class HouseStark implements House {
    @Override
    public Member createMember() {
        return new StarkMember();
    }
}
