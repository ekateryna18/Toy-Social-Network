package domain;

public class Friendship extends Entity<Long>{
    private Utilizator u1;
    private Utilizator u2;

    public Friendship(Utilizator u1, Utilizator u2) {
        this.u1 = u1;
        this.u2 = u2;
    }

    public Utilizator getU1() {
        return u1;
    }

    public Utilizator getU2() {
        return u2;
    }

    public void setU1(Utilizator u1) {
        this.u1 = u1;
    }

    public void setU2(Utilizator u2) {
        this.u2 = u2;
    }
}
