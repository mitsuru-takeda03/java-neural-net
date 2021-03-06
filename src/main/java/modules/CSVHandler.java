package modules;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * csv形式のファイル読み込み、書き込みを行う。
 * ついでにDouble<->Stringのキャストも行う。
 */
public class CSVHandler {
    /**
     * csv読み込み
     * @param path 読み込みファイルのパス
     * @return 2次元ArrayList
     */
    public static ArrayList<ArrayList<String>> loadCSV(String path) {
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        try {
            List<String> lines = Files.readAllLines(Path.of(path));
            for (String line : lines) {
                ArrayList<String> dataLine = new ArrayList<String>();
                for (String string : line.split(",", 0)){
                    dataLine.add(string);
                }
                data.add(dataLine);
            }
        }
        catch (IOException e) {
            System.out.println("pathが存在しません。");
            e.printStackTrace();
        }
        return data;
    }

    /**
     * csv書き込み
     * @param path 書き込みファイルのパス
     * @param data 書き込む2次元ArrayList
     */
    public static void exportCSV(String path, ArrayList<ArrayList<String>> data){
        try {
            FileWriter fw = new FileWriter(path, false);
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
            for (ArrayList<String> dataLine : data){
                for (String d : dataLine){
                    pw.print(d);
                    pw.print(",");
                }
                pw.println();
            }
            pw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * String -> Doubleのキャスト
     * @param tableString ArrayList<ArrayList<String>>
     * @return ArrayList<ArrayList<Double>>
     */
    public static ArrayList<ArrayList<Double>> toDouble(ArrayList<ArrayList<String>> tableString){
        ArrayList<ArrayList<Double>> tableDouble = new ArrayList<ArrayList<Double>>();
        for(int i = 0; i < tableString.size(); i++){
            ArrayList<Double> lineDouble = new ArrayList<>();
            for(int j = 0; j < tableString.get(0).size(); j++){
                lineDouble.add(Double.parseDouble(tableString.get(i).get(j)));
            }
            tableDouble.add(lineDouble);
        }
        return tableDouble;
    }

    /**
     * Double -> String のキャスト
     * @param tableDouble ArrayList<ArrayList<Double>>
     * @return ArrayList<ArrayList<String>>
     */
    public static ArrayList<ArrayList<String>> toString(ArrayList<ArrayList<Double>> tableDouble){
        ArrayList<ArrayList<String>> tableString = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < tableDouble.size(); i++){
            ArrayList<String> lineString = new ArrayList<>();
            for(int j = 0; j < tableDouble.get(0).size(); j++){
                lineString.add(String.valueOf(tableDouble.get(i).get(j)));
            }
            tableString.add(lineString);
        }
        return tableString;
    }
}
