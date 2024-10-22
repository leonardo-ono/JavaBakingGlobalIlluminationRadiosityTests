package renderer.core;

import java.util.ArrayList;
import java.util.List;
import renderer.rasterizer.Vertex;

/**
 *
 * @author leonardo
 */
public class Shader {

    public Vertex[] vertices;
    
    public double[] uniforms; // valores uniformes

    // Edge 1
    public double[] dVarsE1; // variacao vars a cada y
    public double[] varsE1; // current vars
    // Edge 2
    public double[] dVarsE2; // variacao vars a cada y
    public double[] varsE2; // current vars
    // Scan
    public double[] dVarsScan; // variacao vars a cada y
    public double[] varsScan; // current vars
    
    public int[] color = new int[] { 255, 255, 255, 255 };
    public int[] color2 = new int[] { 255, 255, 255, 255 };
    public int[] color3 = new int[] { 255, 255, 255, 255 };
    public int[] color4 = new int[] { 255, 255, 255, 255 };
    public int[] color5 = new int[] { 255, 255, 255, 255 };
    public int[] color6 = new int[] { 255, 255, 255, 255 };
    public int[] color7 = new int[] { 255, 255, 255, 255 };
    
    public void lerp(int[] color1, int[] color2, int[] color3, double p) {
        color3[0] = (int) (color1[0] + p * (color2[0] - color1[0]));
        color3[1] = (int) (color1[1] + p * (color2[1] - color1[1]));
        color3[2] = (int) (color1[2] + p * (color2[2] - color1[2]));
        color3[3] = (int) (color1[3] + p * (color2[3] - color1[3]));
    }

    private int vertexExtraDatasSize;
    private int vertexVarsSize;
    
    public Shader(int uniformsSize, int vertexExtraDatasSize, int vertexVarsSize) {
        this.vertexExtraDatasSize = vertexExtraDatasSize;
        this.vertexVarsSize = vertexVarsSize;
        
        vertices = new Vertex[3];
        for (int i=0; i<3; i++) {
            vertices[i] = new Vertex(vertexExtraDatasSize, vertexVarsSize);
        }
        
        uniforms = new double[uniformsSize];
        dVarsE1 = new double[vertexVarsSize];
        varsE1 = new double[vertexVarsSize];
        dVarsE2 = new double[vertexVarsSize];
        varsE2 = new double[vertexVarsSize];
        dVarsScan = new double[vertexVarsSize];
        varsScan = new double[vertexVarsSize];
    }
    
    public void preDraw(Renderer renderer, Vertex v1, Vertex v2, Vertex v3) {
        // override your code here
    }

    public void postDraw(Renderer renderer, Vertex v1, Vertex v2, Vertex v3) {
        // override your code here
    }
    
    public void processVertex(Renderer renderer, Vertex v) {
        // override your vertex shader here
    }
    
    public void processPixel(Renderer renderer, int xMin, int xMax, int x, int y, double[] vars) {
        // override your fragment shader here
    }
    
    // Cached vertices
    
    private int vertexCacheIndex = 0;
    private List<Vertex> vertexCache = new ArrayList<Vertex>();

    public void initVertexCache() {
        vertexCacheIndex = 0;
    }
    
    public Vertex getVertexFromCache() {
        Vertex cachedVertex = null;
        if (vertexCacheIndex > (vertexCache.size() - 1)) {
            cachedVertex = new Vertex(vertexExtraDatasSize, vertexVarsSize);
            vertexCache.add(cachedVertex);
            vertexCacheIndex++;
        }
        else {
            cachedVertex = vertexCache.get(vertexCacheIndex++);
        }
        return cachedVertex;
    }
    
}
