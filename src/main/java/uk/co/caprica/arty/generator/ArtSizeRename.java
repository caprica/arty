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

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.name.Rename;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of a Thumnbnailator {@link Rename} function that renames the art file to include its size in the
 * filename.
 * <p>
 * For example, "cover.jpg" will become "cover-512.jpg", "cover-128.jpg" and so on.
 */
final class ArtSizeRename extends Rename {

    /**
     * Regular expression to match and capture the part of the filename before the "." and the extension.
     * <p>
     * For example, for "cover.jpg" this will capture "cover".
     */
    private static final Pattern PATTERN = Pattern.compile("(.+)\\..+");

    /**
     * Art size value to include in the new filename.
     */
    private final int size;

    /**
     * Create a filename renaming component.
     *
     * @param size art size value to include in the new filename
     */
    ArtSizeRename(int size) {
        this.size = size;
    }

    @Override
    public String apply(String s, ThumbnailParameter thumbnailParameter) {
        Matcher matcher = PATTERN.matcher(s);
        if (matcher.matches()) {
            return matcher.group(1).concat("-").concat(Integer.toString(size));
        } else {
            // If the pattern does not match we can't do much else than return the original filename (but really it's
            // some issue/bug if we don't get a match)
            return s;
        }
    }
}
