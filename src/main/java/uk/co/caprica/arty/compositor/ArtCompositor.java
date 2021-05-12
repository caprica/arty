/*
 * This file is part of Arty.
 *
 * Arty is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Arty is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Arty.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2021 Caprica Software Limited.
 */

package uk.co.caprica.arty.compositor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Component used to generate cover art by compositing a collection of images (from files) into a grid.
 */
final public class ArtCompositor {

    public BufferedImage compose(List<Path> paths, int targetSize, int maxGridCells) throws IOException {
        return compose(paths, targetSize, maxGridCells, null);
    }

    /**
     * Compose a collection of image files into a grid.
     *
     * @param paths collection if image file paths
     * @param targetSize size of the target image (a square grid, so width and height will be the same)
     * @param maxGridCells maximum number of grid cells to use
     * @param backgroundColour optional background colour for the image (in case of gaps due to integer division when calculating grid cell sizes)
     * @return composite image
     */
    public BufferedImage compose(List<Path> paths, int targetSize, int maxGridCells, Color backgroundColour) throws IOException {
        BufferedImage targetImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) targetImage.getGraphics();
        if (backgroundColour != null) {
            g2.setColor(backgroundColour);
            g2.fillRect(0, 0, targetImage.getWidth(), targetImage.getHeight());
        }
        int gridCells = calculateGridCells(paths.size(), maxGridCells);
        int[] gridCellSizes = calculateGridCellSizes(gridCells, targetSize);
        int i = 0;
        int y = 0;
        int imageCount = paths.size();
        for (int row = 0; row < gridCells; row++) {
            int x = 0;
            for (int col = 0; col < gridCells; col++) {
                // Special case for two images in a two by two grid, we want to alternate the images on each row, and
                // we can accomplish that by using the XOR operator to derive the image index
                if (imageCount == 2) {
                    i = row ^ col;
                }
                BufferedImage sourceImage = ImageIO.read(paths.get(i % imageCount).toFile());
                int gridCellSize = gridCellSizes[col];
                Image scaledImage = sourceImage.getScaledInstance(gridCellSize, gridCellSize, Image.SCALE_SMOOTH);
                g2.drawImage(scaledImage, x, y, null);
                x += gridCellSize;
                i++;
            }
            y += gridCellSizes[row];
        }
        return targetImage;
    }

    /**
     * Calculate the necessary number of grid cells (in each direction).
     *
     * @param itemCount total number of items to fit into the grid
     * @param maxGridCells maximum number of grid cells to use
     * @return minimum number of grid cells needed to fit all of the items
     */
    private static int calculateGridCells(int itemCount, int maxGridCells) {
        return Math.min(maxGridCells, (int) Math.ceil(Math.sqrt(itemCount)));
    }

    /**
     * Calculate the size of each grid cell such that space is evenly divided as much as possible, then distributing any
     * remainder (due to integer division) evenly amongst the cells as much as possible.
     *
     * @param gridCells number of grid cells in each direction, e.g. 3 for a 3x3 grid
     * @param targetSize width for the target image, in whole pixels
     * @return array of evenly distributed cell widths
     */
    private static int[] calculateGridCellSizes(int gridCells, int targetSize) {
        int gridCellSize = targetSize / gridCells;
        int gridCellRemainder = targetSize % gridCells;
        int[] gridCellSizes = new int[gridCells];
        for (int i = 0; i < gridCells; i++) {
            // Set the grid cell size, accounting for an extra pixel width where needed due to integer division
            gridCellSizes[i] = i < gridCellRemainder ? gridCellSize+1 : gridCellSize;
        }
        return gridCellSizes;
    }
}
