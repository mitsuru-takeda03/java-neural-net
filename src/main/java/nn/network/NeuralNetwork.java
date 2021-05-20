package nn.network;

import nn.layer.Layer;
import matrix.Matrix;
import nn.layer.SoftmaxWithLoss;

import java.util.*;

/**
 * ニューラルネットワークの実装
 */
public class NeuralNetwork implements Network{
    private List<Layer> layers;

    public NeuralNetwork() { layers = new ArrayList<>(); }

    public void addLayer(Layer newLayer) { layers.add(newLayer); }

    /**
     * 順伝播
     * @param matX 入力行列。batchSize x dimension
     * @param matTruth 正解ラベルのone hot vector行列。 batchSize x number of label
     * @return log ロスと正答率
     */
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

    /**
     * 逆伝播
     * @param matTruth 正解ラベルのone hot vector行列。 batchSize x number of label
     * @param learningRate 学習率
     */
    public void backward(Matrix matTruth, double learningRate){
        Matrix input = Matrix.add(matTruth, 0.);
        Collections.reverse(layers);
        for(Layer layer : layers){
            input = layer.backward(input, learningRate);
        }
        Collections.reverse(layers);
    }

    public List<Layer> getLayers() { return layers; }
}
