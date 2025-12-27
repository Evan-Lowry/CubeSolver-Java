public class Edge extends Cubit {

    private int rot;

    public Edge(int x, int y, int z, int rot) {
        super(x, y, z);
        this.rot = rot;
    }

    public int getRot() {
        return this.rot;
    }

    public void F() {
        if (this.getZ() == 1) {
            this.rot = this.rot ^ 1;
            this.rotZ(1);
        }
    }

    public void Fp() {
        if (this.getZ() == 1) {
            this.rot = this.rot ^ 1;
            this.rotZ(-1);
        }
    }

    public void B() {
        if (this.getZ() == -1) {
            this.rot = this.rot ^ 1;
            this.rotZ(-1);
        }
    }

    public void Bp() {
        if (this.getZ() == -1) {
            this.rot = this.rot ^ 1;
            this.rotZ(1);
        }
    }

    public void R() {
        if (this.getX() == 1) {
            this.rotX(1);
        }
    }

    public void Rp() {
        if (this.getX() == 1) {
            this.rotX(-1);
        }
    }

    public void L() {
        if (this.getX() == -1) {
            this.rotX(-1);
        }
    }

    public void Lp() {
        if (this.getX() == -1) {
            this.rotX(1);
        }
    }
}
