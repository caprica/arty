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

import java.nio.file.Path;

/**
 * Specification for a component that wants to be notified of progress when generating artwork.
 */
public interface ArtGeneratorProgress {

    /**
     * Art generation is about to begin for a particular file.
     *
     * @param current index of the current file within the collection
     * @param total total number of files in the collection
     * @param filePath path of the current file being processed, relative to the source root path
     */
    void beforeGenerate(int current, int total, Path filePath);

    /**
     * Art generation is done for a particular file.
     *
     * @param current index of the current file within the collection
     * @param total total number of files in the collection
     * @param filePath path of the current file being processed, relative to the source root path
     */
    void afterGenerate(int current, int total, Path filePath);
}
