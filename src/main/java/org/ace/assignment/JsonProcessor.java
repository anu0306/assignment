package org.ace.assignment;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.extern.java.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.ace.assignment.json.model.Batter;
import org.ace.assignment.json.model.Root;
import org.ace.assignment.json.model.Topping;
import org.ace.assignment.table.model.Tabular;

import static java.util.logging.Level.INFO;

@Log
public class JsonProcessor {

    public static void main(String... args){
        log.log(INFO, "Executing JsonProcessor");
        List<String> argsList = Arrays.stream(args).collect(Collectors.toList());
        if(argsList.size() < 2){
            System.out.println("too few arguments..");
            System.out.println("Usage:: java -jar JsonProcessor <JsonFilePath> <TargetFilePath>");
            return;
        } else if(argsList.size() > 2){
            System.out.println("too many arguments..");
            System.out.println("Usage:: JsonProcessor <JsonFilePath> <TargetFilePath>");
            return;
        }
        String jsonInputFilePath = argsList.get(0);
        String outPutFilePath = argsList.get(1);

        if (jsonInputFilePath.equals(outPutFilePath)){
            System.out.println("Input file path and output file path is same. Not processing!!");
            return;
        }

        try{
            Root root = readJsonFile(jsonInputFilePath);

            if (null != root){
                ArrayList<Tabular> tabularData = processJsonToFlatTable(root);
                writeToFile(outPutFilePath, tabularData);
            }
        }catch (FileNotFoundException e){
            System.out.printf("File not found :: %s\n",e.getMessage());
            return;
        }catch (IOException e){
            System.out.printf("Fail to write :: %s\n", e.getMessage());
            return;
        }catch (JsonSyntaxException e){
            System.out.printf("Invalid Json format :: %s\n", e.getMessage());
            return;
        }catch (Exception e){
            System.out.println("Unknown processing error..");
            return;
        }
        System.out.println("Json file processed successfully..");
    }

    private static void writeToFile(String outPutFilePath, ArrayList<Tabular> tabularData) throws IOException{

        log.info("Writing processed tabular data at following location :: ( " + outPutFilePath + " )");

        FileWriter fileWriter = new FileWriter(outPutFilePath);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.printf("===================================================================================\n");
        printWriter.printf("|%5s |%12s  |%9s   |%15s   |%25s  |\n","Id","Type","Name","Batter","Topping");
        printWriter.printf("===================================================================================\n");
        tabularData.forEach(row->{
            printWriter.printf("|%5s |%12s  |%9s   |%15s   |%25s  |\n",row.getId(), row.getType(), row.getName(), row.getBatterType(), row.getToppingType());
        });
        printWriter.printf("===================================================================================\n");
        printWriter.close();
    }

    public static Root readJsonFile(String filePath) throws FileNotFoundException, JsonSyntaxException {
        log.info("Reading Json input file :: ( " + filePath + " )");

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

