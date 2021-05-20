package nn;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class Sigmoid implements Layer{
    private Matrix matOut;

    public Matrix forward(Matrix matX){
        // out = 1 / (1 + exp(-x))
        matOut = Matrix.devide(1., Matrix.add(1., Matrix.exp(Matrix.dot(matX, -1.))));
        return matOut;
    }

    public Matrix backward(Matrix error){
        Matrix dX = Matrix.dot(Matrix.dot(error, Matrix.subtract(1., matOut)), matOut);
        return dX;
    }
}
