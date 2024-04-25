package src;

import java.util.Arrays;

/**
 * Class representing a face of the cube
 */
public class Face
{
    private Colour[][] grid = new Colour[3][3];

    /**
     * @brief Instantiate a face
     * @param c Colour enum
     */
    public Face(Colour c) {
        // Colour of face is determined by its middle element
        for (int i = 0; i < grid.length; i++) {
            grid[i] = new Colour[]{c, c, c};
        }
    }

    /**
     * @brief Return colour of face
     * @return Colour of face
     */
    public Colour getColour() {
        return grid[1][1];
    }

    /**
     * @brief Return grid of tiles representing cube face
     * @return 2D array of Colour representing cube face
     */
    public Colour[][] getTiles() {
        return grid;
    }

    /**
     * @brief Return tiles of a row or column
     * @detail Tiles are left to right and top down
     * @param row Boolean specifying a row if true, or a column if false
     * @param xy Integer specifying the row or column (0, 1, 2)
     * @return Three tiles of a row or column
     */
    public Colour[] getThree(boolean row, int xy) {
        Colour[] colours = new Colour[3];
        // Row
        if (row) {
            colours = grid[xy];
        }

        // Column (top tile down)
        else {
            for (int i = 0; i < grid.length; i++) {
                colours[i] = grid[i][xy];
            }
        }
        return colours;
    }

    private Colour[] reverse(Colour[] cs) {
        Colour[] rev = new Colour[cs.length];
        for (int i = 0; i < cs.length; i++) {
            rev[i] = cs[cs.length - i - 1];
        }
        return rev;
    }

    /**
     * @brief Set tiles of a row or column
     * @param row Boolean specifying a row if true, or a column if false
     * @param xy Integer specifying the row or column
     * @throws UnsupportedOperationException if center tile manipulation is attempted
     */
    public void setThree(boolean row, Integer xy, Colour[] cs, boolean reverse) {
        if (xy == 1) {
            throw new UnsupportedOperationException("Cannot alter center tile");
        }

        // Reverse the tiles if reverse argument is true
        Colour[] csCorrected = new Colour[3];
        if (reverse) { csCorrected = reverse(cs); }
        else { csCorrected = cs; }

        // Row
        if (row) {
            grid[xy] = csCorrected;
        }
        // Column
        else {
            for (int i = 0; i < grid.length; i++) {
                grid[i][xy] = csCorrected[i];
            }
        }
    }

    /**
     * @brief Set face colours
     * @param g Grid of colours
     * @throws UnsupportedOperationException if colour of center tile changes
     */
    public void setFace(Colour[][] g) {
        if (this.getColour() != g[1][1]) {
            throw new UnsupportedOperationException("Cannot alter center tile");
        }
        grid = g;
    }
}
