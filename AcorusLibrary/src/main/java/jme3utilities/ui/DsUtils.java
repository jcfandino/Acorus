/*
 Copyright (c) 2018-2022, Stephen Gold
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice, this
    list of conditions and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

 3. Neither the name of the copyright holder nor the names of its
    contributors may be used to endorse or promote products derived from
    this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package jme3utilities.ui;

import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jme3utilities.Validate;
import jme3utilities.math.MyArray;

/**
 * Utility methods connected with display settings.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class DsUtils {
    // *************************************************************************
    // constants and loggers

    /**
     * message logger for this class
     */
    final public static Logger logger
            = Logger.getLogger(DsUtils.class.getName());
    /**
     * pattern for matching a display dimensions
     */
    final private static Pattern dimensionsPattern
            = Pattern.compile("^\\s*(\\d+)\\s*[x,]\\s*(\\d+)\\s*");
    /**
     * MSAA sampling factors, in ascending order
     */
    final private static int[] msaaFactors = {1, 2, 4, 6, 8};
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private DsUtils() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Describe the specified display dimensions.
     *
     * @param width width in pixels (&gt;0)
     * @param height height in pixels (&gt;0)
     * @return a textual description (not null, not empty)
     */
    public static String describeDimensions(int width, int height) {
        Validate.positive(width, "width");
        Validate.positive(height, "height");

        String description = String.format("%d x %d", width, height);
        return description;
    }

    /**
     * Describe an MSAA sampling factor.
     *
     * @param factor samples per pixel (&ge;0, &le;16)
     * @return a textual description (not null, not empty)
     */
    public static String describeMsaaFactor(int factor) {
        String description;
        if (factor <= 1) {
            description = "disabled";
        } else {
            description = String.format("%dx", factor);
        }

        return description;
    }

    /**
     * Access the array of MSAA sampling factors.
     *
     * @return the pre-existing array (in ascending order)
     */
    public static int[] getMsaaFactors() {
        assert MyArray.isSorted(msaaFactors);
        return msaaFactors;
    }

    /**
     * Parse the specified text to obtain display dimensions.
     *
     * @param description the text to parse (not null, not empty)
     * @return a new array containing the width and height, or null for a syntax
     * error
     */
    public static int[] parseDisplaySize(String description) {
        Validate.nonEmpty(description, "text");

        String lcText = description.toLowerCase(Locale.ROOT);
        Matcher matcher = dimensionsPattern.matcher(lcText);
        int[] result = null;
        if (matcher.find()) {
            result = new int[2];
            String widthText = matcher.group(1);
            result[0] = Integer.parseInt(widthText);
            String heightText = matcher.group(2);
            result[1] = Integer.parseInt(heightText);
        }

        return result;
    }
}
