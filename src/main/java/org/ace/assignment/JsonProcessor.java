package org.ace.assignment;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.ace.assignment.json.model.Batter;
import org.ace.assignment.json.model.Root;
import org.ace.assignment.json.model.Topping;
import org.ace.assignment.table.model.Tabular;

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

            if (null != root){
                ArrayList<Tabular> processed = processJsonToFlatTable(root);
                System.out.printf("|%5s |%12s  |%9s   |%15s   |%25s  |\n","Id","Type","Name","Batter","Topping");
                System.out.println("===================================================================================");

                processed.forEach(row->{
                    System.out.printf("|%5s |%12s  |%9s   |%15s   |%25s  |\n",row.getId(), row.getType(), row.getName(), row.getBatterType(), row.getToppingType());
                });
            }
        }catch (FileNotFoundException e){
            System.out.println("File not found..");
            return;
        }catch (JsonSyntaxException e){
            System.out.println("Invalid Json format..");
            return;
        }catch (Exception e){
            System.out.println("Unknown processing error..");
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

    private static ArrayList<Tabular> processJsonToFlatTable(Root root) {
        ArrayList<Tabular> transformedTable = new ArrayList<>();
        root.getItems().getItem().forEach( item -> {
            String id = item.getId();
            String type = item.getType();
            String name = item.getName();
            List<Batter> batterList = item.getBatters().getBatter();
            List<Topping> toppingList = item.getTopping();
            for (int i = 0; i < batterList.size(); i++) {
                String batterType = batterList.get(i).getType();
                for (int j = 0; j < toppingList.size(); j++) {
                    transformedTable.add(new Tabular(id,type,name,batterType, toppingList.get(j).getType()));
                }
            }
        });
        return transformedTable;
    }
}

