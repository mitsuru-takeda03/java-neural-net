package nn.layer;

import matrix.Matrix;

public interface Layer {
    Matrix forward(Matrix input);
    Matrix backward(Matrix error, double learningRate);
    @Override
    String toString();
}
