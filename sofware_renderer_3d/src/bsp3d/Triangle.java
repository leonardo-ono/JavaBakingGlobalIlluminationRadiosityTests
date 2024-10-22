package bsp3d;

import renderer.math.Vec3;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.io.Serializable;
import renderer.core.Material;
import renderer.core.Renderer;
import renderer.math.Vec2;

/**
 *
 * @author Leo
 */
public class Triangle implements Comparable<Triangle>, Serializable {

    public Vec3 a;
    public Vec3 b;
    public Vec3 c;
    public Vec3 normal = new Vec3();
    
    public Vec2 uvA;
    public Vec2 uvB;
    public Vec2 uvC;
    
    public String materialId;
    public transient Material material;
    
    private final Vec3 p1Tmp = new Vec3();
    
    private static Stroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    
    private static Color[] colors = new Color[256];
    
    static {
        for (int c = 0; c < 256; c++) {
            colors[c] = new Color(c, c, c, 255);
        }
    }
    
    public Triangle(Vec3 a, Vec3 b, Vec3 c, Vec3 n, Vec2 uvA, Vec2 uvB, Vec2 uvC, Material material) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.normal = n;
        this.uvA = uvA;
        this.uvB = uvB;
        this.uvC = uvC;
        this.material = material;
        this.materialId = material.name;
        if (uvA == null || uvB == null || uvC == null || material == null || normal == null || a == null || b == null || c == null) {
            throw new RuntimeException("Triangle will null arguments in constructor !");
        }
    }
    
//    public Triangle(Vec3 a, Vec3 b, Vec3 c) {
//        this.a = a;
//        this.b = b;
//        this.c = c;
//
//        p1Tmp.set(a);
//        p1Tmp.sub(b);
//        normal.set(c);
//        normal.sub(b);
//        normal.cross(p1Tmp);
//    }
    
    private static Polygon polygon = new Polygon();
    
    public void draw(Graphics2D g) {
        polygon.reset();
        polygon.addPoint((int) (a.x + 0), (int) (a.y + 0));
        polygon.addPoint((int) (b.x + 0), (int) (b.y + 0));
        polygon.addPoint((int) (c.x + 0), (int) (c.y + 0));
        g.draw(polygon);
    }

    Vec3 wa = new Vec3();
    Vec3 wb = new Vec3();
    Vec3 wc = new Vec3();
    
    private static Plane plane = new Plane(new Vec3(0, 0, 1.0), new Vec3(0, 0, 0.01));
    Player playerTmp = new Player();

    public void draw3D(Renderer renderer, Player player) {
        renderer.setMaterial(material);
        renderer.begin();
                if (uvA != null) renderer.setTextureCoordinates(uvA.x, uvA.y);
                renderer.setNormal(-1, -1, 0);
                renderer.setVertex(a.x, a.y, a.z);

                if (uvB != null) renderer.setTextureCoordinates(uvB.x, uvB.y);
                renderer.setNormal(-1, 1, 0);
                renderer.setVertex(b.x, b.y, b.z);

                if (uvC != null) renderer.setTextureCoordinates(uvC.x, uvC.y);
                renderer.setNormal(1, 1, 0);
                renderer.setVertex(c.x, c.y, c.z);
                
        renderer.end();        
    }
    
    @Override
    public String toString() {
        return "Triangle{" + "a=" + a + ", b=" + b + ", c=" + c + ", normal=" + normal + '}';
    }

    @Override
    public int compareTo(Triangle o) {
        return (int) Math.signum((o.a.z + o.b.z + o.c.z) / 3 - (a.z + b.z + c.z) / 3);
    }

    public double calculateArea() {
        wa.set(b);
        wa.sub(a);
        wb.set(c);
        wb.sub(a);
        wa.cross(wb);
        return wa.getLength();
    }

}
