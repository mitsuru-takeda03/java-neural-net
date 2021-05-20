package nn.layer;

import matrix.Function;
import matrix.Matrix;

public class SoftmaxWithLoss implements Layer{
    private Matrix matOut;
    private double loss;
    private double accuracy;

    public Matrix forward(Matrix matX){
        matOut = Function.softMax(matX, 1);
        return matOut;
    }

    public Matrix backward(Matrix matTruth, double learningRate){
        loss = Function.crossEntropyError(matTruth, matOut);
        Matrix matTruthArg = Function.maxArg(matTruth, 1);
        Matrix matOutArg = Function.maxArg(matOut, 1);
        accuracy = Function.checkAccuracy(matTruthArg, matOutArg);
        Matrix error = Matrix.divide(Matrix.subtract(matOut, matTruth), matOut.row);
        return error;

    }

    public double getLoss(){ return loss; }

    public double getAccuracy(){ return accuracy; }

    public String toString() {
        return "type: SoftMaxWithLoss";
    }
}
