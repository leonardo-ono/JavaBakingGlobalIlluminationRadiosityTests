public class Level {
    
    public static final int LEVEL_HEIGHT = 5;
    public static final int LEVEL_ROWS = 10;
    public static final int LEVEL_COLS = 10;

    // [y][z][x]
    public static int[][][] map = {
        {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 0, 1, 1, 1, 1, 1, 0, 0, 0}, // 5
            {0, 0, 1, 1, 1, 1, 1, 0, 0, 0}, // 5
            {0, 0, 1, 1, 1, 1, 1, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
        },         
        {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 1, 1, 1, 1, 1, 1, 1, 0, 0}, // 5
            {0, 1, 0, 0, 1, 0, 0, 1, 0, 0}, // 5
            {0, 1, 0, 0, 0, 0, 0, 1, 0, 0}, // 5
            {0, 1, 0, 0, 1, 0, 0, 1, 0, 0}, // 5
            {0, 1, 1, 1, 1, 1, 1, 1, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
        },        
        {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 1, 1, 1, 1, 1, 1, 1, 0, 0}, // 5
            {0, 1, 0, 0, 1, 0, 0, 1, 0, 0}, // 5
            {0, 1, 0, 0, 0, 0, 0, 1, 0, 0}, // 5
            {0, 1, 0, 0, 1, 0, 0, 1, 0, 0}, // 5
            {0, 1, 1, 1, 1, 1, 1, 1, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
        },        
        {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 1, 1, 1, 1, 1, 1, 1, 0, 0}, // 5
            {0, 1, 0, 0, 1, 0, 0, 1, 0, 0}, // 5
            {0, 1, 0, 0, 1, 0, 0, 1, 0, 0}, // 5
            {0, 1, 0, 0, 1, 0, 0, 1, 0, 0}, // 5
            {0, 1, 1, 1, 1, 1, 1, 1, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
        },        
        {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 1, 1, 1, 1, 1, 1, 1, 0, 0}, // 5
            {0, 1, 1, 1, 1, 1, 1, 1, 0, 0}, // 5
            {0, 1, 1, 1, 1, 1, 1, 1, 0, 0}, // 5
            {0, 1, 1, 1, 1, 1, 1, 1, 0, 0}, // 5
            {0, 1, 1, 1, 1, 1, 1, 1, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 5
        },        
    };


    public static int get(int x, int y, int z) {
        if (x < 0 || x > LEVEL_COLS - 1) return 0;
        if (y < 0 || y > LEVEL_HEIGHT - 1) return 0;
        if (z < 0 || z > LEVEL_ROWS - 1) return 0;
        return map[y][z][x];
    }
    public static void generate(World world, double tx, double ty, double tz) {
        for (int y = 0; y < LEVEL_HEIGHT; y++) {
            for (int z = 0; z < LEVEL_ROWS; z++) {
                for (int x = 0; x < LEVEL_COLS; x++) {
                    if (get(x, y, z) == 1) {
                        double cx = x + tx;
                        double cy = y + ty;
                        double cz = z + tz;
                        
                        if (get(x, y, z + 1) == 0) world.addFace(cx, cy, cz, Face.ORIENTATION.FRONT, false);
                        if (get(x, y, z - 1) == 0) world.addFace(cx, cy, cz, Face.ORIENTATION.BACK, false);
                        if (get(x - 1, y, z) == 0) world.addFace(cx, cy, cz, Face.ORIENTATION.LEFT, false);
                        if (get(x + 1, y, z) == 0) world.addFace(cx, cy, cz, Face.ORIENTATION.RIGHT, false);
                        if (get(x, y + 1, z) == 0) world.addFace(cx, cy, cz, Face.ORIENTATION.TOP, false);
                        if (get(x, y - 1, z) == 0) world.addFace(cx, cy, cz, Face.ORIENTATION.BOTTOM, false);                            
                    }
                }
            }
        }
    }
}
