package nn;

import nn.layer.Affine;
import nn.layer.Sigmoid;
import nn.layer.SoftmaxWithLoss;
import nn.network.NeuralNetworkHandler;
import nn.network.WineDataHandler;
import nn.network.NeuralNetwork;

public class Main {
    public static void main(String[] args){
        test();
    }

    public static void test(){
        // ネットワークの設定
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        neuralNetwork.addLayer(new Affine(11, 20));
        neuralNetwork.addLayer(new Sigmoid());
        neuralNetwork.addLayer(new Affine(20, 10));
        neuralNetwork.addLayer(new SoftmaxWithLoss());
        // データハンドラーの設定
        WineDataHandler wineDataHandler = new WineDataHandler("src/main/resources/data/winequality-red.csv");

        NeuralNetworkHandler neuralNetworkHandler = new NeuralNetworkHandler(neuralNetwork, wineDataHandler);
        neuralNetworkHandler.train(100, 100, "src/main/resources/result/test1");
        neuralNetworkHandler.exportNN("src/main/resources/result/test1");
    }
}
