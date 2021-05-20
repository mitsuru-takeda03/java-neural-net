package nn.layer;

import matrix.Function;
import matrix.Matrix;

/**
 * Affine変換のレイヤー
 * 主にこの重み(matW)と定数項(matB)を学習する。
 */
public class Affine implements Layer{
    private Matrix matW;
    private Matrix matB;
    private Matrix matX;
    private Matrix dW;
    private Matrix dB;

    /**
     * あまり良くはないが、0で初期化。
     * @param xDim 入力層の次元
     * @param outDim 出力層の次元
     */
    public Affine(int xDim, int outDim){
        matW = new Matrix(xDim, outDim);
        matB = new Matrix(1, outDim);
    }

    /**
     * こちらは保存した重みから初期化するときに使う。
     * @param matW
     * @param matB
     */
    public Affine(Matrix matW, Matrix matB){
        this.matW = matW;
        this.matB = matB;
    }

    /**
     * 順伝播
     * @param matX
     * @return
     */
    public Matrix forward(Matrix matX) {
        this.matX = matX.copy();
        Matrix matOut = Matrix.add(Matrix.dot(matX, matW), Function.extend(matB, matX.row));
        return matOut;
    }

    /**
     * 逆伝播
     * @param error 前の層の誤差
     * @param learningRate 学習率
     * @return 次の層に渡す誤差
     */
    public Matrix backward(Matrix error, double learningRate) {
        Matrix dX = Matrix.dot(error, Matrix.transpose(matW));
        dW = Matrix.dot(Matrix.transpose(matX), error);
        dB = Function.sum(error, 0);
        matW = Matrix.subtract(matW.copy(), Matrix.dotH(dW, learningRate));
        matB = Matrix.subtract(matB.copy(), Matrix.dotH(dB, learningRate));
        return dX;
    }

    public String toString() {
        return "type: Affine, input: "+matW.row+", output: "+matW.col;
    }

    public Matrix getMatW() { return matW; }

    public Matrix getMatB() { return matB; }
}
