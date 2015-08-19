/**
 * Author: Luis M Pena ( lu@coderazzi.net ) License: MIT License
 *
 * Copyright (c) 2007 Luis M. Pena - lu@coderazzi.net
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.brassoftware.framework.ui.filters.parser;

/**
 * Class to handle HTML content, required to remove HTML tags and to convert
 * HTML special characters (like &amp;) to Java characters
 */
class HtmlHandler {

    private final StringBuffer buffer = new StringBuffer();

    /**
     * Converts an String to the corresponding string without HTML information.
     */
    public String stripHtml(String s) {
        String inner = getSubstringUnderHtmlTag(s);
        return (inner == null ? s : removeHtmlInfo(inner)).trim();
    }

    /**
     * Removes any tag and converts special HTML characters to Java chars.
     */
    private String removeHtmlInfo(String inner) {
        boolean inTag = false, inQuoteInTag = false;
        char quoteChar = '"';
        int entityPos = -1;

        buffer.delete(0, buffer.length());
        for (char c : inner.toCharArray()) {
            if (c == '<') {
                inTag = true;
                entityPos = -1;
            } else if (c == '>') {
                // not anymore tag, unless is inside a quote
                inTag = inTag && inQuoteInTag;
            } else if (inTag) {
                // special attention to "' characters
                if (c == '"' || c == '\'') {
                    if (inQuoteInTag) {
                        inQuoteInTag = (quoteChar != c);
                    } else {
                        inQuoteInTag = true;
                        quoteChar = c;
                    }
                }
            } else {
                if (c == '&') {
                    entityPos = buffer.length();
                } else if (c == ';' && entityPos != -1) {
                    int len = buffer.length();
                    if (len > entityPos + 2) {
                        int entityValue = getEntityValue(entityPos + 1);
                        if (entityValue > 0 && entityValue < 65536) {
                            buffer.delete(entityPos, len);
                            c = (char) entityValue;
                        }
                    }
                }
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    /**
     * Returns the integer associated to the entity stored in the StringBuffer,
     * starting at the passed position (until the end of the buffer).
     *
     * @return -1 if it is not a valid html entity
     */
    private int getEntityValue(int start) {
        if (buffer.charAt(start) == '#') {
            char hex = buffer.charAt(start);
            try {
                if (hex == 'x' || hex == 'X') {
                    return Integer.valueOf(buffer.substring(start + 2), 16);
                }
                return Integer.valueOf(buffer.substring(start + 1));
            } catch (NumberFormatException nfe) {
                return -1;
            }
        }
        return HtmlEntities.getEntityValue(buffer.substring(start));
    }

    /**
     * Removes the external <html></html> tags, if existing. It returns the
     * string contained inside the tags, or null if the tags are not present
     */
    private String getSubstringUnderHtmlTag(String s) {
        int l = s.length();
        if (l >= 6 && (s.charAt(0) == '<') && (s.charAt(5) == '>')
                && (s.charAt(1) == 'h' || s.charAt(1) == 'H')
                && (s.charAt(2) == 't' || s.charAt(2) == 'T')
                && (s.charAt(3) == 'm' || s.charAt(3) == 'M')
                && (s.charAt(4) == 'l' || s.charAt(4) == 'L')
                && (s.charAt(0) == '<')) {
			// it is enough if the string starts with <html>, ending not
            // important
            if (l >= 13 && (s.charAt(l - 1) == '>') && (s.charAt(l - 7) == '<')
                    && (s.charAt(l - 6) == '/')
                    && (s.charAt(l - 5) == 'h' || s.charAt(l - 5) == 'H')
                    && (s.charAt(l - 4) == 't' || s.charAt(l - 4) == 'T')
                    && (s.charAt(l - 3) == 'm' || s.charAt(l - 3) == 'M')
                    && (s.charAt(l - 2) == 'l' || s.charAt(l - 2) == 'L')) {
                l -= 7;
            }
            return s.substring(6, l);
        }
        return null;
    }

}
