import java.util.List;
import java.util.ArrayList;

public class Patch {

    public static int patchId = 0;

    public int id;

    public Face face;
    public Vec3 normal;
    public Vec3 worldPosition;
    public int tx;
    public int ty;

    public double emission = 0.0;
    public double reflectance = 0.8;
    public double incidentLight = 0.0;
    public double excidentLight = 0.0;
    public double totalLight = 0.0;

    public List<PatchInfluence> affectedPatches = new ArrayList<>();

    public Patch() {
        this.id = patchId++;
    }

    public void initializePatch() {
        incidentLight = 0.0;
        excidentLight = emission;
    }
    
    /*
    private final Vec3 rayTmp = new Vec3();
    private final Vec3 closestPoint = new Vec3();

    public void preProcessAllAffectedPatches(World world) {
        for (Patch patch : world.patches) {
            if (patch == this || patch.normal.dot(normal) <= 0) {
                continue;
            }
            Face intersectionFace = world.castRay(worldPosition, patch.worldPosition, closestPoint);
            if (intersectionFace == patch.face) {
                PatchInfluence patchInfluence = new PatchInfluence();
                patchInfluence.patch = patch;
                
                rayTmp.set(worldPosition);
                rayTmp.sub(patch.worldPosition);
                double dist = rayTmp.getLength();
                rayTmp.normalize();

                // factor form
                patchInfluence.influence = (Math.abs(normal.dot(rayTmp)) * Math.abs(patch.normal.dot(rayTmp))) / (Math.PI * dist * dist);

                affectedPatches.add(patchInfluence);
            }
        }

        System.out.println("patch id " + id + " = " + affectedPatches.size());
    }
 */

    public void processRadiosity() {
        for (PatchInfluence patchInfluence : affectedPatches) {
            patchInfluence.patch.incidentLight += patchInfluence.influence * excidentLight;
        }
    }

}
