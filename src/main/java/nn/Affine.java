package nn;

public class Affine implements Layer{
    private Matrix W;
    private Matrix b;
    private Tensor X;
    private Tensor dW;
    private Tensor db;

    Affine(int xDim, int outDim){
        W = new Matrix(xDim, outDim);
        b = new Matrix(1, outDim);
    }

    Affine(Matrix w, Matrix b){
        this.W = w;
        this.b = b;
    }

    public Tensor forward(Tensor X) {
        this.X = X;
        return Tensor.add(Tensor.dot(X, W), b);
    }

    public Tensor backward(Tensor error) {
        Tensor dX = Tensor.dot(error, Matrix.transpose(W));
        dW = Tensor.dot(Tensor.transpose(X), error);
        db = Tensor.sum(error);
        return dX;
    }
}
