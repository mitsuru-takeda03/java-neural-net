package nn.network;

import matrix.Matrix;
import modules.CSVHandler;
import nn.layer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NeuralNetworkHandler implements NetworkHandler{
    private final Network network;
    private final DataHandler dataHandler;
    private ArrayList<ArrayList<Double>> log;
    private double learningRate;

    /**
     *
     * @param network
     * @param dataHandler
     */
    public NeuralNetworkHandler(Network network, DataHandler dataHandler) {
        this.network = network;
        this.dataHandler = dataHandler;
        log = new ArrayList<ArrayList<Double>>();
        learningRate = 0.1;
    }

    public void loadNN(String path){
        ArrayList<ArrayList<String>> configTable = CSVHandler.loadCSV(path);
        for(ArrayList<String> configLine : configTable){
            if(configLine.get(0) == "nn.layer.Affine"){
                Matrix matW = new Matrix(CSVHandler.toDouble(CSVHandler.loadCSV(configLine.get(1)+"_W.csv")));
                Matrix matB = new Matrix(CSVHandler.toDouble(CSVHandler.loadCSV(configLine.get(1)+"_B.csv")));
                network.addLayer(new Affine(matW, matB));
            }
            else if(configLine.get(0) == "nn.layer.ReLu")
                network.addLayer(new ReLu());
            else if(configLine.get(0) == "nn.layer.Sigmoid")
                network.addLayer(new Sigmoid());
            else if(configLine.get(0) == "nn.layer.SoftmaxWithLoss")
                network.addLayer(new SoftmaxWithLoss());
        }
    }

    public void exportNN(String path){
        int numberOfAffine = 0;
        ArrayList<ArrayList<String>> configTable = new ArrayList<ArrayList<String>>();
        for(Layer layer : network.getLayers()){
            ArrayList<String> configLine = new ArrayList<>();
            if(layer.getClass().getName() == "nn.layer.Affine"){
                Affine affine = (Affine) layer;
                ArrayList<ArrayList<Double>> affineW = affine.getMatW().toList();
                ArrayList<ArrayList<Double>> affineB = affine.getMatB().toList();
                CSVHandler.exportCSV(path+"Affine_"+numberOfAffine+"_W.csv",
                        CSVHandler.toString(affineW));
                CSVHandler.exportCSV(path+"Affine_"+numberOfAffine+"_B.csv",
                        CSVHandler.toString(affineB));
                configLine.add("nn.layer.Affine");
                configLine.add(path+"Affine_"+numberOfAffine);
                numberOfAffine++;
            }
            else{
                configLine.add(layer.getClass().getName());
                configLine.add("");
            }
            configTable.add(configLine);
        }
        CSVHandler.exportCSV(path+".csv", configTable);
    }

    public void train(int batchSize, int epoch, String path){
        for(int times = 0; times < epoch * dataHandler.size() / batchSize; times++){
            Map<String, Matrix> batchData = dataHandler.getBatch(batchSize);
            Map<String, Double> newLog = network.forward(batchData.get("X"), batchData.get("Y"));
            network.backward(batchData.get("Y"), learningRate);
            log.add(new ArrayList<Double>(List.of(newLog.get("Loss"), newLog.get("Accuracy"))));
        }
        CSVHandler.exportCSV(path + "Log.csv", CSVHandler.toString(log));
    }
}
