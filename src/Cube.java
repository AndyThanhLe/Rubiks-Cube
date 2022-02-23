package src;

import java.util.HashMap;
import java.lang.Math;

public class Cube
{
    // Declare and instantiate cube faces
    private static Face front = new Face(Colour.WHITE);
    private static Face back = new Face(Colour.YELLOW);
    private static Face upper = new Face(Colour.GREEN);
    private static Face down = new Face(Colour.BLUE);
    private static Face left = new Face(Colour.RED);
    private static Face right = new Face(Colour.ORANGE);

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
        if (cw)
        {
            // New x  = Old y; New y = x with the math
            for (int y = 0; y < oldGrid.length; y++)
            {
                for (int x = 0; x < oldGrid[y].length; x++)
                {
                    newGrid[y][Math.abs(x - 2)] = oldGrid[x][y];
                }
            }
        }
        else
        {
            // New x = y with the map; New y = Old x
            for (int y = 0; y < oldGrid.length; y++)
            {
                for (int x = 0; x < oldGrid[y].length; x++)
                {
                    newGrid[Math.abs(y - 2)][x] = oldGrid[x][y];
                }
            }
        }
        f.setFace(newGrid);

        // Adjust three tiles of four faces affected
        Face relUpper, relDown, relLeft, relRight;
        // Values are array of length two [row (0) / column (1), row/column number]
        HashMap<String, Integer[]> faceData = new HashMap<String, Integer[]>();
        // Array stating if tiles of upper, down, left, right should be reversed respectively
        boolean[][] reverseTiles;

        switch (f.getColour())
        {
            case WHITE:
                // REV: CCW
                relUpper = upper;
                faceData.put(relUpper.getColour().toString(), new Integer[]{0, 2});
                // REV: CCW
                relDown = down;
                faceData.put(relDown.getColour().toString(), new Integer[]{0, 0});
                // REV: CW
                relLeft = left;
                faceData.put(relLeft.getColour().toString(), new Integer[]{1, 2});
                // REV: CW
                relRight = right;
                faceData.put(relRight.getColour().toString(), new Integer[]{1, 0});

                reverseTiles = new boolean[][]{ {true, false}, {true, false}, {false, true}, {false, true} };
                break;
            case YELLOW:
                // REV: CW
                relUpper = down;
                faceData.put(relUpper.getColour().toString(), new Integer[]{0, 2});
                // REV: CCW
                relDown = upper;
                faceData.put(relDown.getColour().toString(), new Integer[]{0, 0});
                // REV: CCW
                relLeft = left;
                faceData.put(relLeft.getColour().toString(), new Integer[]{1, 0});
                // REV: CCW
                relRight = right;
                faceData.put(relRight.getColour().toString(), new Integer[]{1, 2});

                reverseTiles = new boolean[][]{ {false, true}, {true, false}, {true, false}, {true, false} };
                break;
            case GREEN:
                // REV: CW AND CCW
                relUpper = back;
                faceData.put(relUpper.getColour().toString(), new Integer[]{0, 2});
                // REV: ~
                relDown = front;
                faceData.put(relDown.getColour().toString(), new Integer[]{0, 0});
                // REV: CW
                relLeft = left;
                faceData.put(relLeft.getColour().toString(), new Integer[]{0, 0});
                // REV: CCW
                relRight = right;
                faceData.put(relRight.getColour().toString(), new Integer[]{0, 0});

                reverseTiles = new boolean[][]{ {true, true}, {false, false}, {false, true}, {true, false} };
                break;
            case BLUE:
                // REV: ~
                relUpper = front;
                faceData.put(relUpper.getColour().toString(), new Integer[]{0, 2});
                // REV: CW AND CCW
                relDown = back;
                faceData.put(relDown.getColour().toString(), new Integer[]{0, 0});
                // REV: CCW
                relLeft = left;
                faceData.put(relLeft.getColour().toString(), new Integer[]{0, 2});
                // REV: CCW
                relRight = right;
                faceData.put(relRight.getColour().toString(), new Integer[]{0, 2});

                reverseTiles = new boolean[][]{ {false, false}, {true, true}, {true, false}, {true, false} };
                break;
            case RED:
                // REV: ~
                relUpper = upper;
                faceData.put(relUpper.getColour().toString(), new Integer[]{1, 0});
                // REV: ~
                relDown = down;
                faceData.put(relDown.getColour().toString(), new Integer[]{1, 0});
                // REV: ~
                relLeft = back;
                faceData.put(relLeft.getColour().toString(), new Integer[]{1, 0});
                // REV: ~
                relRight = front;
                faceData.put(relRight.getColour().toString(), new Integer[]{1, 0});

                reverseTiles = new boolean[][]{ {false, false}, {false, false}, {false, false}, {false, false} };
                break;
            case ORANGE:
                // REV: ~
                relUpper = upper;
                faceData.put(relUpper.getColour().toString(), new Integer[]{1, 2});
                // REV: ~
                relDown = down;
                faceData.put(relDown.getColour().toString(), new Integer[]{1, 2});
                // REV: ~
                relLeft = front;
                faceData.put(relLeft.getColour().toString(), new Integer[]{1, 2});
                // REV: ~
                relRight = back;
                faceData.put(relRight.getColour().toString(), new Integer[]{1, 2});

                reverseTiles = new boolean[][]{ {false, false}, {false, false}, {false, false}, {false, false} };
                break;
            default:
                System.out.println("Something went wrong...");
                break;
        }

        Face[] fs;
        if (cw)
        {
            // upper -> right -> down -> left -> upper
            fs = new Face[]{relUpper, relRight, relDown, relLeft};
        }
        else
        {
            // upper -> left -> down -> right -> upper
            fs = new Face[]{relUpper, relLeft, relDown, relRight};
        }

        Face prev = fs[3];
        // Store the tiles that are to be replaced
        boolean row;
        Integer xy;
        Colour[] hold = fs[0].getThree(row, xy);
        for (Face f : fs)
        {
            hold = x.getThree();
        }
    }
}
