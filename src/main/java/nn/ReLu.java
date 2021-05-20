package nn;

public class ReLu implements Layer{
    private Matrix matMask;

    public Matrix forward(Matrix matX){
        matMask = Matrix.isOver(matX, 0);
        return Matrix.dotH(matMask, matX);
    }
    public Matrix backward(Matrix error){
        return Matrix.dotH(matMask, error);
    }
}
