package com.kenzie.app;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.NoSuchElementException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainTest {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    // Set up streams to test console input and output
    @BeforeAll
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @BeforeEach
    public void setTestInput() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void printsOpeningMessage() {
        tryAndCatchRunWithMain("");
        assertThat(outContent.toString(), containsString("Welcome to the Kenzie Stock Exchange"));
    }

    @Test
    public void asksForStockInfo() {
        tryAndCatchRunWithMain("Kenzie\nKENZ\n250.0\n");
        assertThat(outContent.toString(), containsString("What is the Stock's name?"));
        assertThat(outContent.toString(), containsString("What is the Stock's ticker symbol?"));
        assertThat(outContent.toString(), containsString("What is the Stock's current price?"));
    }

    @Test
    public void displaysMenu() {
        tryAndCatchRunWithMain("Kenzie\nKENZ\n250.0\n");
        assertThat(outContent.toString(), containsString("Enter a selection (1-4)"));
        assertThat(outContent.toString(), containsString("1. Check your Balance"));
        assertThat(outContent.toString(), containsString("2. Buy"));
        assertThat(outContent.toString(), containsString("3. Sell"));
        assertThat(outContent.toString(), containsString("4. Exit"));
    }

    @Test
    public void canExitProgram() {
        runMainWithInput("Kenzie\nKENZ\n250.0\n4\n");
        assertThat(outContent.toString(), containsString("Goodbye"));
    }

    @Test
    public void canBuyStock() {
        tryAndCatchRunWithMain("Kenzie\nKENZ\n250.0\n2\n10\n");
        assertThat(outContent.toString(), containsString("You bought 10 shares for $2500.0"));
    }

    @Test
    public void canCheckEmptyBalance() {
        tryAndCatchRunWithMain("Kenzie\nKENZ\n250.0\n1\n");
        assertThat(outContent.toString(), containsString("You own 0 shares of Kenzie (KENZ)"));
        assertThat(outContent.toString(), containsString("Your balance is: $0.0"));
    }

    @Test
    public void canRunWithDifferentStockInfo() {
        tryAndCatchRunWithMain("Apple\nAAPL\n53.21\n1\n");
        assertThat(outContent.toString(), containsString("You own 0 shares of Apple (AAPL)"));
        assertThat(outContent.toString(), containsString("Your balance is: $0.0"));
    }

    @Test
    public void canBuyAndCheckBalance() {
        tryAndCatchRunWithMain("Kenzie\nKENZ\n250.0\n2\n10\n1\n");
        assertThat(outContent.toString(), containsString("You bought 10 shares for $2500.0"));
        assertThat(outContent.toString(), containsString("You own 10 shares of Kenzie (KENZ)"));
        assertThat(outContent.toString(), containsString("Your balance is: $2500.0"));
    }

    @Test
    public void canBuyAndCheckBalanceWithDifferentStockInfo() {
        tryAndCatchRunWithMain("Apple\nAAPL\n53.21\n2\n10\n1\n");
        assertThat(outContent.toString(), containsString("You bought 10 shares for $532.1"));
        assertThat(outContent.toString(), containsString("You own 10 shares of Apple (AAPL)"));
        assertThat(outContent.toString(), containsString("Your balance is: $532.1"));
    }


    @Test
    public void canBuyAndSell() {
        tryAndCatchRunWithMain("Kenzie\nKENZ\n250.0\n2\n10\n3\n4\n");
        assertThat(outContent.toString(), containsString("You bought 10 shares for $2500.0"));
        assertThat(outContent.toString(), containsString("You sold 4 shares for $1000.0"));
    }

    @Test
    public void canBuySellAndCheckBalance() {
        tryAndCatchRunWithMain("Kenzie\nKENZ\n250.0\n2\n10\n3\n4\n1\n");
        assertThat(outContent.toString(), containsString("You bought 10 shares for $2500.0"));
        assertThat(outContent.toString(), containsString("You sold 4 shares for $1000.0"));
        assertThat(outContent.toString(), containsString("You own 6 shares of Kenzie (KENZ)"));
        assertThat(outContent.toString(), containsString("Your balance is: $1500.0"));
    }

    @Test
    public void canBuyMultipleTimes() {
        tryAndCatchRunWithMain("Kenzie\nKENZ\n250.0\n2\n10\n2\n2\n2\n3\n1\n");
        assertThat(outContent.toString(), containsString("You bought 10 shares for $2500.0"));
        assertThat(outContent.toString(), containsString("You bought 2 shares for $500.0"));
        assertThat(outContent.toString(), containsString("You bought 3 shares for $750.0"));
        assertThat(outContent.toString(), containsString("You own 15 shares of Kenzie (KENZ)"));
        assertThat(outContent.toString(), containsString("Your balance is: $3750.0"));
    }

    @Test
    public void canSellMultipleTimes() {
        tryAndCatchRunWithMain("Kenzie\nKENZ\n250.0\n2\n10\n3\n2\n3\n3\n1\n");
        assertThat(outContent.toString(), containsString("You bought 10 shares for $2500.0"));
        assertThat(outContent.toString(), containsString("You sold 2 shares for $500.0"));
        assertThat(outContent.toString(), containsString("You sold 3 shares for $750.0"));
        assertThat(outContent.toString(), containsString("You own 5 shares of Kenzie (KENZ)"));
        assertThat(outContent.toString(), containsString("Your balance is: $1250.0"));
    }

    @Test
    public void allInstanceVariablesArePrivate() {
        Field[] fields = StockHolding.class.getDeclaredFields();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            Modifier.isPrivate(modifiers);
            assertEquals(true, Modifier.isPrivate(modifiers), field.getName() + " is private");
        }
    }


    private static void tryAndCatchRunWithMain(String input) {
        try {
            runMainWithInput(input);
        } catch (NoSuchElementException e) {
            // Ignore this error
        }
    }
    private static void runMainWithInput(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Main.main(new String[]{});
    }
}

