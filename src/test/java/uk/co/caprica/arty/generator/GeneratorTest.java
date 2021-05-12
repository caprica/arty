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
import java.nio.file.Paths;

public class GeneratorTest {

    public static void main(String[] args) throws Exception {
        ArtGenerator generator = new ArtGenerator(
            Paths.get("src/test/resources/generator"),
            Paths.get("target/output/generator"),
            new int[] { 64, 256 },
            "cover.png",
            "png",
            true
        );
        generator.generateArt(new ArtGeneratorProgress() {
            @Override
            public void beforeGenerate(int current, int total, Path filePath) {
                System.out.printf("beforeGenerate(current=%d, total=%d, filePath=%s)%n", current, total, filePath);
            }

            @Override
            public void afterGenerate(int current, int total, Path filePath) {
                System.out.printf("beforeGenerate(current=%d, total=%d, filePath=%s)%n", current, total, filePath);
            }
        });
    }
}
