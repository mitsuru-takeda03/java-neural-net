package nn.network;

import modules.CSVHandler;
import nn.layer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NeuralNetworkHandler {
    private final NeuralNetwork neuralNetwork;
    private final WineDataHandler wineDataHandler;
    private ArrayList<ArrayList<Double>> log;
    private double learningRate;

    public NeuralNetworkHandler(NeuralNetwork neuralNetwork, WineDataHandler wineDataHandler) {
        this.neuralNetwork = neuralNetwork;
        this.wineDataHandler = wineDataHandler;
        log = new ArrayList<ArrayList<Double>>();
        learningRate = 0.1;
    }

    public void loadNN(String path){
        ArrayList<ArrayList<String>> configTable = CSVHandler.loadCSV(path);
        for(ArrayList<String> configLine : configTable){
            if(configLine.get(0) == "nn.layer.Affine"){
                Matrix matW = new Matrix(CSVHandler.toDouble(CSVHandler.loadCSV(configLine.get(1)+"_W.csv")));
                Matrix matB = new Matrix(CSVHandler.toDouble(CSVHandler.loadCSV(configLine.get(1)+"_B.csv")));
                neuralNetwork.addLayer(new Affine(matW, matB));
            }
            else if(configLine.get(0) == "nn.layer.ReLu")
                neuralNetwork.addLayer(new ReLu());
            else if(configLine.get(0) == "nn.layer.Sigmoid")
                neuralNetwork.addLayer(new Sigmoid());
            else if(configLine.get(0) == "nn.layer.SoftmaxWithLoss")
                neuralNetwork.addLayer(new SoftmaxWithLoss());
        }
    }

    public void exportNN(String path){
        int numberOfAffine = 0;
        ArrayList<ArrayList<String>> configTable = new ArrayList<ArrayList<String>>();
        for(Layer layer : neuralNetwork.layers){
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
        for(int times = 0; times < epoch * wineDataHandler.size() / batchSize; times++){
            Map<String, Matrix> batchData = wineDataHandler.getBatch(batchSize);
            Map<String, Double> newLog = neuralNetwork.forward(batchData.get("X"), batchData.get("Y"));
            neuralNetwork.backward(batchData.get("Y"), learningRate);
            log.add(new ArrayList<Double>(List.of(newLog.get("Loss"), newLog.get("Accuracy"))));
        }
        CSVHandler.exportCSV(path + "Log.csv", CSVHandler.toString(log));
    }
}
