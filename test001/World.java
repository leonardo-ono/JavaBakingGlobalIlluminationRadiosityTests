import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class World {
    
    private final List<Face> faces = new ArrayList<>();

    private final Color[] rayIntensity = new Color[256];

    public List<Patch> patches = new ArrayList<>();


    public World() {
        for (int i = 0; i < rayIntensity.length; i++) {
            rayIntensity[i] = new Color(i, i, i);
        }
    }

    public Face addFace(double x, double y, double z, Face.ORIENTATION orientation, boolean emission) {
        Face face = new Face(new Vec3(x, y, z), orientation, emission);
        faces.add(face);
        return face;
    }
    
    public Face[] addCube(double x, double y,double z, boolean emission) {
        Face f1 = addFace(x, y, z, Face.ORIENTATION.FRONT, emission);
        Face f2 = addFace(x, y, z, Face.ORIENTATION.BACK, emission);
        Face f3 = addFace(x, y, z, Face.ORIENTATION.LEFT, emission);
        Face f4 = addFace(x, y, z, Face.ORIENTATION.RIGHT, emission);
        Face f5 = addFace(x, y, z, Face.ORIENTATION.TOP, emission);
        Face f6 = addFace(x, y, z, Face.ORIENTATION.BOTTOM, emission);        
        return new Face[] { f1, f2, f3, f4, f5, f6 };
    }

    public void generateAllPatches() {
        for (Face face : faces) {
            face.generatePatches(patches);
        }
        
        //for (int i = 0; i < patches.size(); i++) {
        //    Patch patch1 = patches.get(i);
        //    patch1.preProcessAllAffectedPatches(this);
        //}

        preProcessAllAffectedPatches(this);
    }
    
    private final Vec3 rayA = new Vec3();
    private final Vec3 closestPoint = new Vec3();

    public void preProcessAllAffectedPatches(World world) {
        
        for (int i = 0; i < patches.size(); i++) {
            Patch patch1 = patches.get(i);
            for (int j = i + 1; j < patches.size(); j++) {
                Patch patch2 = patches.get(j);
                
                rayA.set(patch1.worldPosition);
                rayA.sub(patch2.worldPosition);
                double dist = rayA.getLength();
                rayA.normalize();

                if (rayA.dot(patch2.normal) <= 0) {
                    continue;
                }

                Face intersectionFace = world.castRay(patch1.worldPosition, patch2.worldPosition, closestPoint);
                if (intersectionFace == patch2.face) {
                    PatchInfluence patchInfluence1 = new PatchInfluence();
                    patchInfluence1.patch = patch2;
                    
                    // factor form
                    //patchInfluence1.influence = (patch1.normal.dot(rayA) * -patch2.normal.dot(rayA)) / (Math.PI * dist * dist);
                    patchInfluence1.influence = (Math.abs(patch1.normal.dot(rayA)) * Math.abs(patch2.normal.dot(rayA))) / (Math.PI * dist * dist);
                    
                    if (patchInfluence1.influence < 0) {
                        patchInfluence1.influence = -patchInfluence1.influence;
                    }

                    patch1.affectedPatches.add(patchInfluence1);

                    PatchInfluence patchInfluence2 = new PatchInfluence();
                    patchInfluence2.patch = patch1;
                    
                    patchInfluence2.influence = patchInfluence1.influence;

                    patch2.affectedPatches.add(patchInfluence2);
                }
            }
            System.out.println("patch id " + patch1.id + " = " + patch1.affectedPatches.size());
        }

        // normalize form factor
        // this is important: normalization ensures that the energy emitted by a patch 
        // is properly distributed among the other influenced patches without loss or gain.
        for (int i = 0; i < patches.size(); i++) {
            Patch patch1 = patches.get(i);
            double totalInfluence = 0;
            for (PatchInfluence influence : patch1.affectedPatches) {
                totalInfluence += influence.influence;
            }
            for (PatchInfluence influence : patch1.affectedPatches) {
                influence.influence /= (totalInfluence);
            }
        }

    }

    private final Vec3 la = new Vec3();
    private final Vec3 lb = new Vec3();
    private final Vec3 lc = new Vec3();

    public Face castRay(Vec3 rayA, Vec3 rayB, Vec3 closestPoint) {
        double minDistance = Double.MAX_VALUE;
        Face intersectionFace = null;

        lb.set(rayB);
        lb.sub(rayA);
        lb.normalize();
        lb.scale(100.00);
        lb.add(rayA);

        for (Face face : faces) {
            if (Geom.getFaceLineIntersectionPoint(face.vertices[0], face.vertices[1], face.vertices[2], rayA, lb, lc)) {
                la.set(rayA);
                la.sub(lc);
                double distance = la.getLength();
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPoint.set(lc);
                    intersectionFace = face;
                }
            }
            
            if (Geom.getFaceLineIntersectionPoint(face.vertices[0], face.vertices[2], face.vertices[3], rayA, lb, lc)) {
                la.set(rayA);
                la.sub(lc);
                double distance = la.getLength();
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPoint.set(lc);
                    intersectionFace = face;
                }
            }
        }

        return intersectionFace;
    }

    public void processRadiosity() {
        System.out.println("processing radiosity start");
    
        for (Patch patch : patches) {
            patch.processRadiosity();
        }

        for (Patch patch : patches) {
            patch.excidentLight = patch.reflectance * patch.incidentLight + patch.emission;
            if (patch.excidentLight < 0) {
                System.out.println("negative light!");
                patch.excidentLight *= -1;
            }
            patch.totalLight += patch.excidentLight;
            patch.incidentLight = 0;
        }

        System.out.println("processing radiosity finished");
    }

    public void bakeRadiosityTextures() {
        System.out.println("bakeRadiosityTextures start");
        for (Patch patch : patches) {
            double c = 255.0 * (patch.totalLight);
            if (c < 0) c = 0;
            if (c > 255) c = 255;
            patch.face.setLightMapValue(patch.ty, patch.tx, c, c, c);
        }
        System.out.println("bakeRadiosityTextures end");

        System.out.println("bakelight start");
        bakeLight();
        System.out.println("bakelight end");
    }

    public void bakeLight() {
        generateAllTexturePngFiles();
        generateWavefrontMaterialFile();
        generateWavefrontFile();
    }

    //private static final String outPath = "F:/leo_hd_d_backup/work/ideia_coisas_para_implementar/java_global_illumination_radiosity/sofware_renderer_3d/src/res/";
    private static final String outPath = "texture/";
    
    private void generateAllTexturePngFiles() {
        for (int f = 0; f < faces.size(); f++) {
            Face face = faces.get(f);
            BufferedImage texture = face.getLightMapImage();
            try {
                ImageIO.write(texture, "png", new File(outPath + "texture" + f + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("error saving texture image file !");
            }
        }
    }

    private void generateWavefrontMaterialFile() {
        try (PrintWriter pw = new PrintWriter(outPath + "test.mtl")) {

            pw.println("# Arquivo de materiais lightmap");
            pw.println("");

            for (int f = 0; f < faces.size(); f++) {
                //Face face = faces.get(f);
                pw.println("newmtl material" + f);
                pw.println("Ka 1.000 1.000 1.000");
                pw.println("Kd 1.000 1.000 1.000");
                pw.println("Ks 0.000 0.000 0.000");
                pw.println("d 1.0");
                pw.println("illum 2");
                pw.println("map_Kd texture" + f +".png");
                pw.println("");
            }
            
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("error saving texture material file!");
        }
    }

    private void generateWavefrontFile() {
        try (PrintWriter pw = new PrintWriter(outPath + "test.obj")) {
            
            pw.println("# Arquivo obj mesh lightmap");
            pw.println("");
            pw.println("mtllib test.mtl");
            pw.println("");

            
            pw.println("# vertices");
            for (int f = 0; f < faces.size(); f++) {
                Face face = faces.get(f);
                for (Vec3 vertex : face.vertices) {
                    pw.println("v " + vertex.x + " " + vertex.y + " " + vertex.z);
                }
            }

            pw.println("");
            pw.println("# texture coordinates");
            for (int f = 0; f < faces.size(); f++) {
                Face face = faces.get(f);
                for (Vec3 uv : face.uvs) {
                    pw.println("vt " + uv.x + " " + uv.y);
                }
            }

            pw.println("");
            pw.println("# polygons");
            pw.println("");
            for (int f = 0; f < faces.size(); f++) {
                
                pw.println("usemtl material" + f);
                //Face face = faces.get(f);
                int vertexIndex = 4 * f + 1;
                int uvIndex = 4 * f + 1;
                pw.println("f " + vertexIndex + "/" + uvIndex + " " + (vertexIndex + 1) + "/" + (uvIndex + 1) + " " + (vertexIndex + 2) + "/" + (uvIndex + 2) + " ");
                pw.println("f " + vertexIndex + "/" + uvIndex + " " + (vertexIndex + 2) + "/" + (uvIndex + 2) + " " + (vertexIndex + 3) + "/" + (uvIndex + 3) + " ");
                pw.println("");
            }

            pw.println("");
            
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("error saving wavefront mesh file!");
        }
    }


}
