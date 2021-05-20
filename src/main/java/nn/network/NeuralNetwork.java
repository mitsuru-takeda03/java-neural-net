package nn.network;

import nn.layer.Layer;
import nn.layer.Matrix;
import nn.layer.SoftmaxWithLoss;

import java.util.*;

public class NeuralNetwork {
    protected List<Layer> layers;

    public NeuralNetwork() { layers = new ArrayList<>(); }

    public void addLayer(Layer newLayer) { layers.add(newLayer); }

    public Map<String, Double> forward(Matrix matX, Matrix matTruth){
        Matrix input = Matrix.add(matX, 0.);
        for(Layer layer : layers){
            input = layer.forward(input);
        }
        Map<String, Double> log = new HashMap<String, Double>();
        if(layers.get(layers.size()-1) instanceof SoftmaxWithLoss) {
            SoftmaxWithLoss lastLayer = (SoftmaxWithLoss) layers.get(layers.size() - 1);
            log.put("Loss", lastLayer.getLoss());
            log.put("Accuracy", lastLayer.getAccuracy());
        }
        return log;
    }

    public void backward(Matrix matTruth, double learningRate){
        Matrix input = Matrix.add(matTruth, 0.);
        Collections.reverse(layers);
        for(Layer layer : layers){
            input = layer.backward(input, learningRate);
        }
        Collections.reverse(layers);
    }
}
