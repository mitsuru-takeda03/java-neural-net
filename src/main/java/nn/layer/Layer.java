package nn.layer;

public interface Layer {
    Matrix forward(Matrix input);
    Matrix backward(Matrix error, double learningRate);
    @Override
    String toString();
}
