package nn;

interface Layer {
    Matrix forward(Matrix input);
    Matrix backward(Matrix error);
}
