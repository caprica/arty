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

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * A file visitor implementation that recursively searches for cover art files.
 * <p>
 * Instances of this class should not be reused.
 */
final class ArtFileVisitor implements FileVisitor<Path> {

    /**
     * Cover art filename to search for.
     */
    private final String artFilename;

    /**
     * Discovered list of cover art file paths.
     */
    private final List<Path> results = new ArrayList<>(1000);

    /**
     * Create a file visitor.
     *
     * @param artFilename standard name of the cover art file to search for, e.g. "cover.jpg"
     */
    ArtFileVisitor(String artFilename) {
        this.artFilename = artFilename;
    }

    /**
     * Get the paths for all of the discovered cover art files.
     *
     * @return list of cover art file paths
     */
    List<Path> results() {
        return results;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        String filename = file.getFileName().toString();
        if (filename.equals(artFilename)) {
            results.add(file);
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        // Continue with the next file, a failure should never happen
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return CONTINUE;
    }
}
