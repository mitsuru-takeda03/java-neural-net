package nn.layer;

import matrix.Function;
import matrix.Matrix;

/**
 * 活性化関数：ReLu
 * 特に更新はしない
 */
public class ReLu implements Layer{
    private Matrix matMask;

    /**
     * 順伝播
     * @param matX batchSize x dimension 行列
     * @return batchSize x dimension 行列
     */
    public Matrix forward(Matrix matX){
        matMask = Function.isOver(matX, 0);
        return Matrix.dotH(matMask, matX);
    }

    /**
     * 逆伝播
     * @param error batchSize x dimension 行列
     * @param learningRate ダミー
     * @return batchSize x dimension 行列
     */
    public Matrix backward(Matrix error, double learningRate){
        return Matrix.dotH(matMask, error);
    }

    public String toString() {
        return "type: ReLu";
    }
}
