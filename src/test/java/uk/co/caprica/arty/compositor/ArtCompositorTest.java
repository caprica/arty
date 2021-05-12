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
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ArtCompositorTest {

    public static void main(String[] args) throws IOException {
        ArtCompositor compositor = new ArtCompositor();
        List<Path> paths;

        int targetSize = 513;

        paths = new ArrayList<>();
        paths.add(Paths.get("src/test/resources/compositor/1.png"));
        paths.add(Paths.get("src/test/resources/2.png"));
        ImageIO.write(
            compositor.compose(paths, targetSize, 4),
            "png",
            new File("target/output/compositor/2x2-composite-2.png")
        );

        paths = new ArrayList<>();
        paths.add(Paths.get("src/test/resources/compositor/1.png"));
        paths.add(Paths.get("src/test/resources/compositor/2.png"));
        paths.add(Paths.get("src/test/resources/compositor/3.png"));
        ImageIO.write(
            compositor.compose(paths, targetSize, 4),
            "png",
            new File("target/output/compositor/2x2-composite-3.png")
        );

        paths = new ArrayList<>();
        paths.add(Paths.get("src/test/resources/compositor/1.png"));
        paths.add(Paths.get("src/test/resources/compositor/2.png"));
        paths.add(Paths.get("src/test/resources/compositor/3.png"));
        paths.add(Paths.get("src/test/resources/compositor/4.png"));
        ImageIO.write(
            compositor.compose(paths, targetSize, 4),
            "png",
            new File("target/output/compositor/2x2-composite-4.png")
        );

        paths = new ArrayList<>();
        paths.add(Paths.get("src/test/resources/compositor/1.png"));
        paths.add(Paths.get("src/test/resources/compositor/2.png"));
        paths.add(Paths.get("src/test/resources/compositor/3.png"));
        paths.add(Paths.get("src/test/resources/compositor/4.png"));
        paths.add(Paths.get("src/test/resources/compositor/5.png"));
        ImageIO.write(
            compositor.compose(paths, targetSize, 9),
            "png",
            new File("target/output/compositor/2x2-composite-5.png")
        );
    }
}
