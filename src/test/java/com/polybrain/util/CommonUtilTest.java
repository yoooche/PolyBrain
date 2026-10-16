package com.polybrain.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommonUtilTest {

    @Test
    public void trimToEmpty_null_returnsEmpty() {
        assertEquals("", CommonUtil.trimToEmpty(null));
    }

    @Test
    public void trimToEmpty_blank_returnsEmpty() {
        assertEquals("", CommonUtil.trimToEmpty("   "));
    }

    @Test
    public void trimToEmpty_removesLeadingAndTrailingSpaces() {
        assertEquals("王小明", CommonUtil.trimToEmpty("  王小明 "));
    }

    @Test
    public void trimToEmpty_keepsInnerSpaces() {
        assertEquals("John Li", CommonUtil.trimToEmpty(" John Li "));
    }
}
