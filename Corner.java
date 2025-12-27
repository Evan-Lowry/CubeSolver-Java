public class Corner extends Cubit {

    private int rot;

    public Corner(int x, int y, int z, int rot) {
        super(x, y, z);
        this.rot = rot;
    }

    public int getRot() {
        return rot;
    }

    public void F() {

        if (this.getZ() == 1) {
            // if corner is in top right or bottom left
            if (this.getX() * this.getY() == 1) {
                rot = (rot + 1) % 3;
                // else corner is in top left or bottom right
            } else {
                rot = (rot + 2) % 3;
            }

            this.rotZ(1);
        }
    }

    public void Fp() {

        if (this.getZ() == 1) {
            // if corner is in top right or bottom left
            if (this.getX() * this.getY() == 1) {
                rot = (rot + 1) % 3;
                // else corner is in top left or bottom right
            } else {
                rot = (rot + 2) % 3;
            }

            this.rotZ(-1);
        }
    }

    public void B() {

        if (this.getZ() == -1) {
            // if corner is in top right or bottom left
            if (this.getX() * this.getY() == 1) {
                rot = (rot + 2) % 3;
                // else corner is in top left or bottom right
            } else {
                rot = (rot + 1) % 3;
            }

            this.rotZ(-1);
        }
    }

    public void Bp() {

        if (this.getZ() == -1) {
            // if corner is in top right or bottom left
            if (this.getX() * this.getY() == 1) {
                rot = (rot + 2) % 3;
                // else corner is in top left or bottom right
            } else {
                rot = (rot + 1) % 3;
            }

            this.rotZ(1);
        }
    }

    public void R() {

        if (this.getX() == 1) {
            // if corner is in top right or bottom left
            if (this.getY() * this.getZ() == 1) {
                rot = (rot + 2) % 3;
                // else corner is in top left or bottom right
            } else {
                rot = (rot + 1) % 3;
            }

            this.rotX(1);
        }
    }

    public void Rp() {

        if (this.getX() == 1) {
            // if corner is in top right or bottom left
            if (this.getY() * this.getZ() == 1) {
                rot = (rot + 2) % 3;
                // else corner is in top left or bottom right
            } else {
                rot = (rot + 1) % 3;
            }

            this.rotX(-1);
        }
    }

    public void L() {

        if (this.getX() == -1) {
            // if corner is in top right or bottom left
            if (this.getY() * this.getZ() == 1) {
                rot = (rot + 1) % 3;
                // else corner is in top left or bottom right
            } else {
                rot = (rot + 2) % 3;
            }

            this.rotX(-1);
        }
    }

    public void Lp() {

        if (this.getX() == -1) {
            // if corner is in top right or bottom left
            if (this.getY() * this.getZ() == 1) {
                rot = (rot + 1) % 3;
                // else corner is in top left or bottom right
            } else {
                rot = (rot + 2) % 3;
            }

            this.rotX(1);
        }
    }
}
