package nn.network;

import matrix.Function;
import modules.CSVHandler;
import matrix.Matrix;

import java.util.*;

/**
 * ワインのデータセット用のハンドラー
 * ちょっと雑に書きすぎたかもしれない
 */
public class WineDataHandler implements DataHandler{
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
        Matrix srcDataX = new Matrix(CSVHandler.toDouble(dataXString));
        // 正規化
        dataX = Matrix.divide(Matrix.subtract(srcDataX, Function.min(srcDataX)), Function.max(srcDataX) - Function.min(srcDataX));
        dataY = Function.makeOneHot(new Matrix(CSVHandler.toDouble(dataYString)), 10);
    }

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
    public int size() { return dataX.row; }
}
