public class Camera {

    private double[] pos = new double[2];
    private double[] offset = new double[2];
    private double[] target;

    public Camera(double[] target, double[] offset) {
        this.pos[0] = target[0];
        this.pos[1] = target[1];

        this.target = target;
        this.offset[0] = offset[0];
        this.offset[1] = offset[1];
    }

    public double[] getPos() {
        return new double[]{ this.pos[0] - this.offset[0], this.pos[1] - this.offset[1] };
    }

    public void update() {
        double distanceX = this.pos[0] - this.target[0];
        double distanceY = this.pos[1] - this.target[1];

        this.pos[0] -= distanceX / 15;
        this.pos[1] -= distanceY / 15;
    }

}
