package nn;

import java.rmi.MarshalException;

public class Affine implements Layer{
    private Matrix matW;
    private Matrix matB;
    private Matrix matX;
    private Matrix dW;
    private Matrix db;

    Affine(int xDim, int outDim){
        matW = new Matrix(xDim, outDim);
        matB = new Matrix(1, outDim);
    }

    Affine(Matrix w, Matrix matB){
        this.matW = w;
        this.matB = matB;
    }

    public Matrix forward(Matrix matX) {
        this.matX = matX;
        return Matrix.add(Matrix.dot(matX, matW), Matrix.extend(matB, matX.row));
    }

    public Matrix backward(Matrix error) {
        Matrix dX = Matrix.dot(error, Matrix.transpose(matW));
        dW = Matrix.dot(Matrix.transpose(matX), error);
        db = Matrix.sum(error);
        return dX;
    }
}
