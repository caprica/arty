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

package uk.co.caprica.arty.generator;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.Rendering;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Component used to generate cover art of various standard sizes.
 * <p>
 * When generating cover art, the sub-path hierarchy (taking into account the base directory) will be preserved.
 * <p>
 * Using appropriately-sized images rather than having the browser downscale a larger image can significantly improve
 * performance of the frontend user interface.
 */
final public class ArtGenerator {

    private final Path sourceRootPath;
    private final Path outputRootPath;
    private final int[] sizes;
    private final String artFilename;
    private final String outputFormat;
    private final boolean allowOverwrite;

    /**
     * Create a configured artwork generator.
     *
     * @param sourceRootPath base directory to search for art
     * @param outputRootPath base directory to store generated art
     * @param sizes sizes (in pixels) for the generated images (art images are assumed to be square)
     * @param artFilename name of the art file
     * @param outputFormat output file format for the generate images, jpg is recommended due to its smaller size
     * @param allowOverwrite <code>true</code> to allow overwriting any existing files; <code>false</code> to forbid
     */
    public ArtGenerator(Path sourceRootPath, Path outputRootPath, int[] sizes, String artFilename, String outputFormat, boolean allowOverwrite) {
        this.sourceRootPath = sourceRootPath;
        this.outputRootPath = outputRootPath;
        this.sizes = sizes;
        this.artFilename = artFilename;
        this.outputFormat = outputFormat;
        this.allowOverwrite = allowOverwrite;
    }

    /**
     * Generate cover art images in the various pre-configured sizes.
     *
     * @param progress callback to report image generation progress
     * @throws IOException if an error occurs
     */
    public void generateArt(ArtGeneratorProgress progress) throws IOException {
        ArtFileVisitor fileVisitor = new ArtFileVisitor(artFilename);
        Files.walkFileTree(sourceRootPath, fileVisitor);
        processFiles(fileVisitor.results(), progress);
    }

    private void processFiles(List<Path> sourceFiles, ArtGeneratorProgress progress) throws IOException {
        int current = 0;
        for (Path sourceFile : sourceFiles) {
            processFile(sourceFile, progress, ++current, sourceFiles.size());
        }
    }

    private void processFile(Path sourceFile, ArtGeneratorProgress progress, int current, int total) throws IOException {
        Path subpath = sourceFile.subpath(sourceRootPath.getNameCount(), sourceFile.getNameCount());
        Path outputDirectoryPath = outputRootPath.resolve(subpath.getParent());
        progress.beforeGenerate(current, total, subpath);
        if (!Files.exists(outputDirectoryPath)) {
            Files.createDirectories(outputDirectoryPath);
        }
        for (int size : sizes) {
            Thumbnails.of(sourceFile.toFile())
                .allowOverwrite(allowOverwrite)
                .forceSize(size, size)
                .rendering(Rendering.QUALITY)
                .scalingMode(ScalingMode.PROGRESSIVE_BILINEAR)
                .outputQuality(1.0)
                .outputFormat(outputFormat)
                .toFiles(outputDirectoryPath.toFile(), new ArtSizeRename(size));
        }
        progress.afterGenerate(current, total, subpath);
    }
}
