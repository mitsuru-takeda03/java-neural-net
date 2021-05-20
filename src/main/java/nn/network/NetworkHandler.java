package nn.network;

import matrix.Matrix;
import modules.CSVHandler;
import nn.layer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ニューラルネットワークのハンドラーのインターフェース
 * 重みの読み込み、重みの書き出し、訓練が実装されてば良い。
 */
interface NetworkHandler {

    void loadNN(String path);

    void exportNN(String path);

    void train(int batchSize, int epoch, String path);
}
