package nn.layer;

import matrix.Function;
import matrix.Matrix;

/**
 * 活性化関数：Sigmoid
 * 特に更新はしない
 */
public class Sigmoid implements Layer {
    private Matrix matOut;

    /**
     * 順伝播
     * @param matX batchSize x dimension 行列
     * @return batchSize x dimension 行列
     */
    public Matrix forward(Matrix matX) {
        // out = 1 / (1 + exp(-x))
        matOut = Matrix.divide(1., Matrix.add(1., Function.exp(Matrix.dotH(matX, -1.))));
        return matOut.copy();
    }

    /**
     * 逆伝播
     * @param error batchSize x dimension 行列
     * @param learningRate ダミー
     * @return batchSize x dimension 行列
     */
    public Matrix backward(Matrix error, double learningRate) {
        Matrix dX = Matrix.dotH(Matrix.dotH(error, Matrix.subtract(1., matOut)), matOut);
        return dX;
    }

    public String toString() {
        return "type: Sigmoid";
    }
}