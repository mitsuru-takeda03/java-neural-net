package nn.layer;

public class ReLu implements Layer{
    private Matrix matMask;

    public Matrix forward(Matrix matX){
        matMask = Matrix.isOver(matX, 0);
        return Matrix.dotH(matMask, matX);
    }
    public Matrix backward(Matrix error, double learningRate){
        return Matrix.dotH(matMask, error);
    }

    public String toString() {
        return "type: ReLu";
    }
}
