
public class Cubit {

    private int x;
    private int y;
    private int z;
    private int rot;

    public Cubit(int x, int y, int z, int rot) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rot = rot;
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

    private void rotX(int c) {
        int y = c * -1 * this.z;
        int z = c * this.y;
        this.y = y;
        this.z = z;
    }

    private void rotY(int c) {
        int x = c * this.z;
        int z = c * -1 * this.x;
        this.x = x;
        this.z = z;
    }

    private void rotZ(int c) {
        int x = c * -1 * this.y;
        int y = c * this.x;
        this.x = x;
        this.y = y;
    }

    public void F() {
        if (this.z == 1) {
            rotZ(1);
        }
    }

    public void Fp() {
        if (this.z == 1) {
            rotZ(-1);
        }
    }

    public void F2() {
        if (this.z == 1) {
            rotZ(1);
            rotZ(1);
        }
    }

    public void B() {
        if (this.z == -1) {
            rotZ(-1);
        }
    }

    public void Bp() {
        if (this.z == -1) {
            rotZ(1);
        }
    }

    public void B2() {
        if (this.z == -1) {
            rotZ(1);
            rotZ(1);
        }
    }

    public void U() {
        if (this.y == 1) {
            rotY(1);
        }
    }

    public void Up() {
        if (this.y == 1) {
            rotY(-1);
        }
    }

    public void U2() {
        if (this.y == 1) {
            rotY(1);
            rotY(1);
        }
    }

    public void D() {
        if (this.y == -1) {
            rotY(-1);
        }
    }

    public void Dp() {
        if (this.y == -1) {
            rotY(-1);
        }
    }

    public void D2() {
        if (this.z == -1) {
            rotY(1);
            rotY(1);
        }
    }

    public void R() {
        if (this.x == 1) {
            rotX(1);
        }
    }

    public void Rp() {
        if (this.x == 1) {
            rotX(-1);
        }
    }

    public void R2() {
        if (this.x == 1) {
            rotX(1);
            rotX(1);
        }
    }

    public void L() {
        if (this.x == -1) {
            rotX(-1);
        }
    }

    public void Lp() {
        if (this.x == -1) {
            rotX(1);
        }
    }

    public void L2() {
        if (this.x == -1) {
            rotX(1);
            rotX(1);
        }
    }
}
