package nn.layer;

public class SoftmaxWithLoss implements Layer{
    private Matrix matOut;
    private double loss;
    private double accuracy;

    public Matrix forward(Matrix matX){
        matOut = Matrix.softMax(matX, 1);
        return matOut;
    }

    public Matrix backward(Matrix matTruth, double learningRate){
        loss = Matrix.crossEntropyError(matTruth, matOut);
        Matrix matTruthArg = Matrix.getMaxArg(matTruth, 1);
        Matrix matOutArg = Matrix.getMaxArg(matOut, 1);
        accuracy = Matrix.checkAccuracy(matTruthArg, matOutArg);
        Matrix error = Matrix.divide(Matrix.subtract(matOut, matTruth), matOut.row);
        return error;

    }

    public double getLoss(){ return loss; }

    public double getAccuracy(){ return accuracy; }

    public String toString() {
        return "type: SoftMaxWithLoss";
    }
}
