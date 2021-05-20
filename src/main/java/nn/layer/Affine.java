package nn.layer;

public class Affine implements Layer{
    private Matrix matW;
    private Matrix matB;
    private Matrix matX;
    private Matrix dW;
    private Matrix dB;

    public Affine(int xDim, int outDim){
        matW = new Matrix(xDim, outDim);
        matB = new Matrix(1, outDim);
    }

    public Affine(Matrix matW, Matrix matB){
        this.matW = matW;
        this.matB = matB;
    }

    public Matrix forward(Matrix matX) {
        this.matX = matX;
        return Matrix.add(Matrix.dot(matX, matW), Matrix.extend(matB, matX.row));
    }

    public Matrix backward(Matrix error, double learningRate) {
        Matrix dX = Matrix.dot(error, Matrix.transpose(matW));
        dW = Matrix.dot(Matrix.transpose(matX), error);
        dB = Matrix.sum(error);
        matW = Matrix.add(matW, Matrix.dotH(dW, learningRate));
        matB = Matrix.add(matB, Matrix.dotH(dB, learningRate));
        return dX;
    }

    public String toString() {
        return "type: Affine, input: "+matW.row+", output: "+matW.col;
    }

    public Matrix getMatW() { return matW; }

    public Matrix getMatB() { return matB; }
}
