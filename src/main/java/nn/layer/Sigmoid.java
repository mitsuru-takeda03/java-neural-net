package nn.layer;

public class Sigmoid implements Layer {
    private Matrix matOut;

    public Matrix forward(Matrix matX) {
        // out = 1 / (1 + exp(-x))
        matOut = Matrix.divide(1., Matrix.add(1., Matrix.exp(Matrix.dotH(matX, -1.))));
        return matOut.copy();
    }

    public Matrix backward(Matrix error, double learningRate) {
        Matrix dX = Matrix.dotH(Matrix.dotH(error, Matrix.subtract(1., matOut)), matOut);
        return dX;
    }

    public String toString() {
        return "type: Sigmoid";
    }
}