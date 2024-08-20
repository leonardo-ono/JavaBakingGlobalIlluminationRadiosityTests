public class Geom {
    
    private static Vec3 vTmpA = new Vec3();
    private static Vec3 vTmpB = new Vec3();
    private static Vec3 vTmpC = new Vec3();

    // it's possible to make some tests here https://www.geogebra.org/m/VaCVEMav
    // to get real vertices values
    public static boolean getFaceLineIntersectionPoint(Vec3 pa, Vec3 pb, Vec3 pc, Vec3 la, Vec3 lb, Vec3 ip) {
        // calculate the normal of face
        vTmpA.set(pb);
        vTmpA.sub(pa);
        vTmpB.set(pc);
        vTmpB.sub(pa);
        vTmpA.cross(vTmpB, vTmpC);
        vTmpC.normalize();

        vTmpA.set(la);
        vTmpA.sub(pa);
        vTmpB.set(lb);
        vTmpB.sub(pa);

        double ya = vTmpC.dot(vTmpA);
        double yb = vTmpC.dot(vTmpB);

        if (ya * yb >= 0.0) {
            return false;
        }

        vTmpA.set(la);
        vTmpA.sub(lb);
        double ll = vTmpA.getLength();

        vTmpA.normalize();
        vTmpA.scale((yb / (yb - ya)) * ll);

        ip.set(lb);
        ip.add(vTmpA);

        // check point inside triangle
        return isPointInTriangle(ip, pa, pb, pc, vTmpC);
    }
    
    private static Vec3 c = new Vec3();
    private static Vec3 t = new Vec3();
    private static Vec3 n = new Vec3();

    public static boolean isPointInTriangle(Vec3 ip, Vec3 ta, Vec3 tb, Vec3 tc, Vec3 tn) {

        // vectors between ip and ta, tb & tc
        t.set(tb);
        t.sub(ta);
        c.set(ip);
        c.sub(ta);
        c.cross(t, n);
        double d0 = tn.dot(n);

        t.set(tc);
        t.sub(tb);
        c.set(ip);
        c.sub(tb);
        c.cross(t, n);
        double d1 = tn.dot(n);

        t.set(ta);
        t.sub(tc);
        c.set(ip);
        c.sub(tc);
        c.cross(t, n);
        double d2 = tn.dot(n);
        
        // check all crossed vectors are in the same directions as face normal
        return d0 <= 0 && d1 <= 0 && d2 <= 0;
    }

}
