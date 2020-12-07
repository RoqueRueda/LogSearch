package com.roque.rueda.logsearch;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class RegexInputTest {

    @Test
    public void createPatternFromCopyString() {
        // Arrange
        // We have some warnings but since this is actual example data we will let it be :-)
        String testInput = "(?<XML><[ns3:]{0,}:AddContainerRequest [\\s\\S]*?<\\/[ns3:]{0,}AddContainerRequest>)";
        Pattern expected = Pattern.compile(testInput);

        // Act
        RegexInput regexInput = new RegexInput(testInput);
        Pattern actual = regexInput.getPattern();

        // Assert
        assertEquals("The patterns should be equals", expected.pattern(), actual.pattern());
    }
}
