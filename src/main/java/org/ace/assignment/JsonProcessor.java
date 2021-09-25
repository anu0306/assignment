package org.ace.assignment;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import org.ace.assignment.model.Country;
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
            Gson gson = readJsonFile(Arrays.stream(args).findFirst().get());
        }catch (FileNotFoundException e){
            System.out.println("File not found..");
            return;
        }
    }

    public static Gson readJsonFile(String filePath) throws FileNotFoundException, JsonSyntaxException, JsonIOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        Gson gson = new Gson();
        Root jsonObject= gson.fromJson(br, Root.class);

        System.out.println("Root ID: "+jsonObject.getItems().getItem().get(0));
        System.out.println("Json file read successful..");
        return gson;
    }
}

