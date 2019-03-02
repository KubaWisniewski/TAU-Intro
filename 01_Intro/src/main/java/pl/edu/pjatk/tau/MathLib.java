package pl.edu.pjatk.tau;

public class MathLib {

    public int add(int x, int y) {
        return x + y;
    }

    public double add() {
        double sum = 0;
        for (int i = 0; i < 10; i++)
            sum += 0.1;
        return sum;
    }
}
