package org.ace.assignment.json;

import lombok.extern.java.Log;
import org.ace.assignment.JsonProcessor;
import org.ace.assignment.json.model.Root;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log
public class JsonProcessorTest {

    private static final String EOL = System.getProperty("line.separator");
    private static PrintStream console;
    private static ByteArrayOutputStream bytes;

    @BeforeEach
    public void setUp() {
        bytes   = new ByteArrayOutputStream();
        console = System.out;
        System.setOut(new PrintStream(bytes));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(console);
    }

    @Test
    void testNoArguments() {
        try {
            JsonProcessor.main();
        } finally {
            System.setOut(console);
        }
        assertEquals(String.format("too few arguments..\n" +
                "Usage:: java -jar JsonProcessor.jar <JsonFilePath> <TargetFilePath>\n", EOL, EOL), bytes.toString());
    }

    @Test
    void testOneArguments() {
        try {
            JsonProcessor.main();
        } finally {
            System.setOut(console);
        }
        assertEquals(String.format("too few arguments..\n" +
                "Usage:: java -jar JsonProcessor.jar <JsonFilePath> <TargetFilePath>\n", EOL, EOL), bytes.toString());
    }

    @Test
    void testToManyArguments() {
        try {
            JsonProcessor.main("arg1", "arg2", "arg3");
        } finally {
            System.setOut(console);
        }
        assertEquals(String.format("too many arguments..\n" +
                "Usage:: java -jar JsonProcessor.jar <JsonFilePath> <TargetFilePath>\n", EOL, EOL), bytes.toString());
    }

    @Test
    void testJsonInputFileNotFound() {
        try {
            JsonProcessor.main("src/test/resources/invalid/Sample.json","dummy");
        } finally {
            System.setOut(console);
        }
        assertEquals(String.format("File not found :: src/test/resources/invalid/Sample.json (No such file or directory)\n", EOL), bytes.toString());
    }

    @Test
    void testJsonOutFilePathNotFound() {
        try {
            JsonProcessor.main("src/test/resources/Sample.json","src/test/resources/invalid/table.txt");
        } finally {
            System.setOut(console);
        }
        assertEquals(String.format("File not found :: src/test/resources/invalid/table.txt (No such file or directory)\n", EOL), bytes.toString());
    }

    @Test
    void testSameInputOutputFilePath() {
        try {
            JsonProcessor.main("src/test/resources/Sample.json", "src/test/resources/Sample.json");
        } finally {
            System.setOut(console);
        }
        assertEquals(String.format("Input file path and output file path is same. Not processing!!\n", EOL), bytes.toString());
    }

    @Test
    void testValidFilePath() {
        try {
            JsonProcessor.main("src/test/resources/Sample.json", "src/test/resources/test.txt");
        } finally {
            System.setOut(console);
        }
        assertEquals(String.format("Json file processed successfully..\n", EOL), bytes.toString());
    }

    @Test
    void testReadJsonFile() {
        //String path = "src/test/resources/simple.json";
        String path = "src/test/resources/Sample.json";
        Root obj = null;
        try {
            obj = JsonProcessor.readJsonFile(path);
        } catch (Exception e){
            System.out.println(e.getStackTrace());
        }
        assertEquals(Root.class, obj.getClass());
    }
}
