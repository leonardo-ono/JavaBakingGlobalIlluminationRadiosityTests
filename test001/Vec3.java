public class Vec3 {
    
    public double x;
    public double y;
    public double z;

    public Vec3() {
    }

    public Vec3(Vec3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Vec3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public void add(Vec3 v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
    }

    public void sub(Vec3 v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
    }

    public void scale(double s) {
        this.x *= s;
        this.y *= s;
        this.z *= s;
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y + z * z);
    }
    
    public void normalize() {
        double length = getLength();
        if (length != 0.0) {
            scale(1 / length);
        }
    }

    public double dot(Vec3 v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public Vec3 cross(Vec3 vi) {
        Vec3 vo = new Vec3();
        cross(vi, vo);
        return vo;
    }

    public void cross(Vec3 vi, Vec3 vo) {
        vo.x = y * vi.z - z * vi.y;
        vo.y = z * vi.x - x * vi.z;
        vo.z = x * vi.y - y * vi.x;
    }

    public Vec3 reflect(Vec3 vn) {
        Vec3 vo = new Vec3();
        reflect(vn, vo);
        return vo;
    }

    private Vec3 vTmp;

    public void reflect(Vec3 vn, Vec3 vo) {
        if (vTmp == null) {
            vTmp = new Vec3();            
        }
        vTmp.set(vn);
        vTmp.normalize();
        vTmp.scale(-2 * vTmp.dot(this));
        vo.set(this);
        vo.add(vTmp);
    }

    @Override
    public String toString() {
        return "Vec3 [x=" + x + ", y=" + y + ", z=" + z + "]";
    }
    
}
