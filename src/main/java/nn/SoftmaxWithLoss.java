package nn;

public class SoftmaxWithLoss implements Layer{
    private Matrix matOut;
    private Matrix matLoss;
    private double loss;


    public Matrix forward(Matrix matX){
        matOut = Matrix.softMax(matX, 1);
        return matOut;
    }

    public Matrix backward(Matrix matTruth){
        return Matrix.devide(Matrix.subtract(matOut, matTruth), matOut.row);
    }

    public double loss(Matrix matTruth){
        return Matrix.crossEntropyError(matTruth, matOut);
    }
}
