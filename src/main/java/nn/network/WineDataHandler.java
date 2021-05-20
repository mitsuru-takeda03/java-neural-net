package nn.network;

import modules.CSVHandler;
import nn.layer.Matrix;

import java.util.*;

public class WineDataHandler {
    private final Matrix dataX;
    private final Matrix dataY;

    public WineDataHandler(String path){
        ArrayList<ArrayList<String>> data = CSVHandler.loadCSV(path);
        data.remove(0);
        ArrayList<ArrayList<String>> dataXString = new ArrayList<>();
        ArrayList<ArrayList<String>> dataYString = new ArrayList<>();
        for(int row = 0; row < data.size(); row++){
            ArrayList<String> lineX = new ArrayList<>();
            ArrayList<String> lineY = new ArrayList<>();
            for(int col = 0; col < data.get(0).size() - 1; col++){
                lineX.add(data.get(row).get(col));
            }
            lineY.add(data.get(row).get(data.get(0).size()-1));
            dataXString.add(lineX);
            dataYString.add(lineY);
        }
        dataX = new Matrix(CSVHandler.toDouble(dataXString));
        dataY = Matrix.makeOneHot(new Matrix(CSVHandler.toDouble(dataYString)), 10);
    }

    public int size() { return dataX.row; }

    public Map<String, Matrix> get(List<Integer> index) {
        Map<String, Matrix> partOfData = new HashMap<>();
        partOfData.put("X", dataX.sliceRow(index));
        partOfData.put("Y", dataY.sliceRow(index));
        return partOfData;
    }

    public Map<String, Matrix> getBatch(int batchSize){
        List<Integer> allIndex = new ArrayList<>();
        List<Integer> selectedIndex = new ArrayList<>();
        for(int i = 0; i < dataX.row; i++)
            allIndex.add(i);
        Collections.shuffle(allIndex);
        for(int i = 0; i < batchSize; i++){
            selectedIndex.add(allIndex.remove(0));
        }
        return get(selectedIndex);
    }
}
