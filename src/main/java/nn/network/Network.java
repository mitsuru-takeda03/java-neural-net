package nn.network;

import matrix.Matrix;
import nn.layer.Layer;
import nn.layer.SoftmaxWithLoss;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ニューラルネットワークのインターフェース
 * レイヤーの追加、順伝播、逆伝播が実装されていれば良い。
 */
interface Network{

    void addLayer(Layer newLayer);

    Map<String, Double> forward(Matrix matX, Matrix matTruth);

    void backward(Matrix matTruth, double learningRate);

    List<Layer> getLayers();
}
