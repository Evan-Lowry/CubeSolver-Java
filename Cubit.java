
public class Cubit {

    private int x;
    private int y;
    private int z;

    public Cubit(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public void rotX(int c) {
        int y = c * this.z;
        int z = c * -1 * this.y;
        this.y = y;
        this.z = z;
    }

    public void rotY(int c) {
        int x = c * -1 * this.z;
        int z = c * this.x;
        this.x = x;
        this.z = z;
    }

    public void rotZ(int c) {
        int x = c * this.y;
        int y = c * -1 * this.x;
        this.x = x;
        this.y = y;
    }

    public void U() {
        if (this.getY() == 1) {
            this.rotY(1);
        }
    }

    public void Up() {
        if (this.getY() == 1) {
            this.rotY(-1);
        }
    }

    public void D() {
        if (this.getY() == -1) {
            this.rotY(-1);
        }
    }

    public void Dp() {
        if (this.getY() == -1) {
            this.rotY(1);
        }
    }
}
