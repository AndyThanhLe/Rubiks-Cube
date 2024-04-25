package src;

import java.util.HashMap;
import java.lang.Math;


enum Relative {
    UPPER, DOWN, LEFT, RIGHT;
}

class RelativeFace {
    public Face face;
    public boolean row;
    public int xy;
    public boolean reverse;

    public RelativeFace(Face face, boolean row, int xy, boolean reverse) {
        this.face = face;
        this.row = row;
        this.xy = xy;
        this.reverse = reverse;
    }
}

public class Cube
{
    // Declare and instantiate cube faces
    private static final Face front = new Face(Colour.WHITE);
    private static final Face back = new Face(Colour.YELLOW);
    private static final Face upper = new Face(Colour.GREEN);
    private static final Face down = new Face(Colour.BLUE);
    private static final Face left = new Face(Colour.RED);
    private static final Face right = new Face(Colour.ORANGE);

    /**
     * @brief Rotate a face of the cube
     * @param f Face that will be rotated
     * @param cw Clockwise if true, counterclockwise otherwise
     */
    public static void qRotate(Face f, boolean cw)
    {
        // Deal with the current rotated face
        Colour[][] oldGrid = f.getTiles();
        Colour[][] newGrid = new Colour[3][3];
        if (cw) {
            // New x  = Old y; New y = x with the math
            for (int y = 0; y < oldGrid.length; y++) {
                for (int x = 0; x < oldGrid[y].length; x++) {
                    newGrid[y][Math.abs(x - 2)] = oldGrid[x][y];
                }
            }
        }
        else {
            // New x = y with the map; New y = Old x
            for (int y = 0; y < oldGrid.length; y++) {
                for (int x = 0; x < oldGrid[y].length; x++) {
                    newGrid[Math.abs(y - 2)][x] = oldGrid[x][y];
                }
            }
        }
        f.setFace(newGrid);


        // Adjust three tiles of four adjacent faces
        HashMap<Relative, RelativeFace> relativeInfo  = new HashMap<Relative, RelativeFace>();
        switch (f.getColour()) {
            case WHITE:
                // RELATIVE UPPER
                relativeInfo.put(
                        Relative.UPPER,
                        new RelativeFace(
                                upper,
                                true,
                                2,
                                !cw));
                // RELATIVE LOWER
                relativeInfo.put(
                        Relative.DOWN,
                        new RelativeFace(
                                down,
                                true,
                                0,
                                !cw));
                // RELATIVE LEFT
                relativeInfo.put(
                        Relative.LEFT,
                        new RelativeFace(
                                left,
                                false,
                                2,
                                cw));
                // RELATIVE RIGHT
                relativeInfo.put(
                        Relative.RIGHT,
                        new RelativeFace(
                                right,
                                false,
                                0,
                                cw));
                break;

            case YELLOW:
                // RELATIVE UPPER
                relativeInfo.put(
                        Relative.UPPER,
                        new RelativeFace(
                                down,
                                true,
                                2,
                                cw));
                // RELATIVE LOWER
                relativeInfo.put(
                        Relative.DOWN,
                        new RelativeFace(
                                upper,
                                true,
                                0,
                                cw));
                // RELATIVE LEFT
                relativeInfo.put(
                        Relative.LEFT,
                        new RelativeFace(
                                left,
                                false,
                                0,
                                !cw));
                // RELATIVE RIGHT
                relativeInfo.put(
                        Relative.RIGHT,
                        new RelativeFace(
                                right,
                                false,
                                2,
                                !cw));
                break;

            case GREEN:
                // RELATIVE UPPER
                relativeInfo.put(
                        Relative.UPPER,
                        new RelativeFace(
                                back,
                                true,
                                2,
                                true));
                // RELATIVE LOWER
                relativeInfo.put(
                        Relative.DOWN,
                        new RelativeFace(
                                front,
                                true,
                                0,
                                false));
                // RELATIVE LEFT
                relativeInfo.put(
                        Relative.LEFT,
                        new RelativeFace(
                                left,
                                true,
                                0,
                                cw));
                // RELATIVE RIGHT
                relativeInfo.put(
                        Relative.RIGHT,
                        new RelativeFace(
                                right,
                                true,
                                0,
                                !cw));
                break;

            case BLUE:
                // RELATIVE UPPER
                relativeInfo.put(
                        Relative.UPPER,
                        new RelativeFace(
                                front,
                                true,
                                2,
                                false));
                // RELATIVE LOWER
                relativeInfo.put(
                        Relative.DOWN,
                        new RelativeFace(
                                back,
                                true,
                                0,
                                true));
                // RELATIVE LEFT
                relativeInfo.put(
                        Relative.LEFT,
                        new RelativeFace(
                                left,
                                true,
                                2,
                                !cw));
                // RELATIVE RIGHT
                relativeInfo.put(
                        Relative.RIGHT,
                        new RelativeFace(
                                right,
                                true,
                                2,
                                cw));
                break;

            case RED:
                // RELATIVE UPPER
                relativeInfo.put(
                        Relative.UPPER,
                        new RelativeFace(
                                upper,
                                false,
                                0,
                                false));
                // RELATIVE LOWER
                relativeInfo.put(
                        Relative.DOWN,
                        new RelativeFace(
                                down,
                                false,
                                0,
                                false));
                // RELATIVE LEFT
                relativeInfo.put(
                        Relative.LEFT,
                        new RelativeFace(
                                back,
                                false,
                                0,
                                false));
                // RELATIVE RIGHT
                relativeInfo.put(
                        Relative.RIGHT,
                        new RelativeFace(
                                front,
                                false,
                                0,
                                false));
                break;

            case ORANGE:
                // RELATIVE UPPER
                relativeInfo.put(
                        Relative.UPPER,
                        new RelativeFace(
                                upper,
                                false,
                                2,
                                false));
                // RELATIVE LOWER
                relativeInfo.put(
                        Relative.DOWN,
                        new RelativeFace(
                                down,
                                false,
                                2,
                                false));
                // RELATIVE LEFT
                relativeInfo.put(
                        Relative.LEFT,
                        new RelativeFace(
                                front,
                                false,
                                2,
                                false));
                // RELATIVE RIGHT
                relativeInfo.put(
                        Relative.RIGHT,
                        new RelativeFace(
                                back,
                                false,
                                2,
                                false));
                break;

            default:
                break;
        }

        HashMap<Relative, Colour[]> relativeTiles = new HashMap<Relative, Colour[]>();
        // Get all the tiles
        for (Relative r : Relative.values()) {
            RelativeFace rf = relativeInfo.get(r);
            relativeTiles.put(r, rf.face.getThree(rf.row, rf.xy));
        }

        // Perform the assignments
        HashMap<Relative, Relative> assignedFrom = new HashMap<Relative, Relative>();
        if (cw) {
            assignedFrom.put(Relative.UPPER, Relative.LEFT);
            assignedFrom.put(Relative.LEFT, Relative.DOWN);
            assignedFrom.put(Relative.DOWN, Relative.RIGHT);
            assignedFrom.put(Relative.RIGHT, Relative.UPPER);
        }
        else {
            assignedFrom.put(Relative.UPPER, Relative.RIGHT);
            assignedFrom.put(Relative.LEFT, Relative.UPPER);
            assignedFrom.put(Relative.DOWN, Relative.LEFT);
            assignedFrom.put(Relative.RIGHT, Relative.DOWN);
        }

        for (Relative r : Relative.values()) {
            RelativeFace dest = relativeInfo.get(r);
            RelativeFace src = relativeInfo.get(assignedFrom.get(r));

            dest.face.setThree(dest.row, dest.xy, relativeTiles.get(assignedFrom.get(r)), src.reverse);
        }
    }

    private static void printOne(Face f) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j % 2 == 1) {
                    Colour[] tiles = f.getThree(true, i);
                    System.out.printf("[ %s %s %s ]", tiles[0].toString().charAt(0), tiles[1].toString().charAt(0), tiles[2].toString().charAt(0));
                }
                else {
                    System.out.print("[       ]");
                }
            }
            System.out.println();
        }
    }

    private static void printThree(Face[] fs) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Colour[] tiles = fs[j].getThree(true, i);
                System.out.printf("[ %s %s %s ]", tiles[0].toString().charAt(0), tiles[1].toString().charAt(0), tiles[2].toString().charAt(0));
            }
            System.out.println();
        }
    }

    public static void printState() {
        // Yellow
        printOne(down);
        printThree(new Face[]{left, back, right});
        printOne(upper);

        qRotate(back, true);

        // Yellow
        printOne(down);
        printThree(new Face[]{left, back, right});
        printOne(upper);

        qRotate(right, false);

        // Yellow
        printOne(down);
        printThree(new Face[]{left, back, right});
        printOne(upper);
    }

    public static void main(String[] args) {
        printState();
    }
}
