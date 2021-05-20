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

        return Matrix.devide(Matrix.subtract(matOut, matTruth), matOut.row);
    }

    public double getLoss(){ return loss; }

    public double getAccuracy(){ return accuracy; }

    public String toString() {
        return "type: SoftMaxWithLoss";
    }
}
