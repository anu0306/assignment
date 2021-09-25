package org.ace.assignment;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.logging.Level;

import org.ace.assignment.model.Root;

import static java.util.logging.Level.INFO;

@Log
public class JsonProcessor {

    public static void main(String... args){
        log.log(INFO, "Executing JsonProcessor");
        if(Arrays.stream(args).count() < 1){
            System.out.println("too few arguments..");
            System.out.println("Usage:: JsonProcessor <JsonFilePath>");
            return;
        }
        try{
            Root root = readJsonFile(Arrays.stream(args).findFirst().get());
        }catch (FileNotFoundException e){
            System.out.println("File not found..");
            return;
        }catch (JsonSyntaxException e){
            System.out.println("Invalid Json format..");
            return;
        }
        System.out.println("Json file processed successfully..");
    }

    public static Root readJsonFile(String filePath) throws FileNotFoundException, JsonSyntaxException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        Gson gson = new Gson();

        Root jsonObject= gson.fromJson(br, Root.class);
        return jsonObject;
    }
}

