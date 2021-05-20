package nn.network;

import nn.layer.Matrix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataHandler {
    private final Matrix dataX;
    private final Matrix dataY;

    public DataHandler(String path){

    }

    public int size() { return dataX.row; }

    public Map<String, Matrix> get(List<Integer> index) {
        Map<String, Matrix> partOfData = new HashMap<String, Matrix>();
        partOfData.put("X", dataX.sliceRow(index));
        partOfData.put("Truth", dataY.sliceRow(index));
        return partOfData;
    }

    public Map<String, Matrix> getBatch(int batchSize){

    }
}
