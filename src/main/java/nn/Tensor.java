package nn;

import java.util.ArrayList;
import java.util.List;

public class Tensor {
    private final List<Matrix> matrices;
    private final int dimension;
    private final int row;
    private final int col;

    private Tensor() {
        dimension = 1;
        row = 1;
        col = 1;
        matrices = new ArrayList<>(List.of(new Matrix(1,1)));
    };

    Tensor(int dimension, int row, int col){
        this.dimension = dimension;
        this.row = row;
        this.col = col;
        matrices = new ArrayList<>();
        Matrix matrix = new Matrix(row, col);
        for(int dim = 0; dim < dimension; dim++){
            matrices.add(matrix);
        }
    };

    Tensor(List<Matrix> matrices) {
        this.dimension = matrices.size();
        this.row = matrices.get(0).row;
        this.col = matrices.get(0).col;
        this.matrices = matrices;
    };

    Tensor(double[][][] value){
        dimension = value.length;
        row = value[0].length;
        col = value[0][0].length;
        matrices = new ArrayList<>();
        for(int dim = 0; dim < dimension; dim++){
            matrices.add(new Matrix(value[dim]));
        }
    };

    public static Tensor add(Tensor tensorA, Tensor tensorB){
        if (tensorA.dimension != tensorB.dimension){
            // なんかexception投げたい
            return new Tensor();
        }
        List<Matrix> result = new ArrayList<>();
        for(int dim = 0; dim < tensorA.dimension; dim++) {
            result.add(Matrix.add(tensorA.matrices.get(dim), tensorB.matrices.get(dim)));
        }
        return new Tensor(result);
    }

    public static Tensor add(Tensor tensorA, Matrix matrixB){
        List<Matrix> tensorB = new ArrayList<>();
        for(int i = 0; i < tensorA.dimension; i++){
            tensorB.add(matrixB);
        }
        return add(tensorA, new Tensor(tensorB));
    }

    public static Tensor add(Matrix matrixA, Tensor tensorB){
        return add(tensorB, matrixA);
    }

    public static Tensor subtract(Tensor tensorA, Tensor tensorB){
        return add(tensorA, dot(tensorB, -1.));
    }

    public static Tensor dot(Tensor tensorA, double b){
        List<Matrix> result = new ArrayList<>();
        for(int dim = 0; dim < tensorA.dimension; dim++) {
            result.add(Matrix.dot(tensorA.matrices.get(dim), b));
        }
        return new Tensor(result);
    }

    public static Tensor dot(double a, Tensor tensorB){
        return dot(tensorB, a);
    }

    public static Tensor dot(Tensor tensorA, Tensor tensorB){
        if (tensorA.dimension != tensorB.dimension){
            // なんかexception投げたい
            return new Tensor();
        }
        List<Matrix> result = new ArrayList<>();
        for(int dim = 0; dim < tensorA.dimension; dim++) {
            result.add(Matrix.dot(tensorA.matrices.get(dim), tensorB.matrices.get(dim)));
        }
        return new Tensor(result);
    }

    public static Tensor dot(Tensor tensorA, Matrix matrixB){
        List<Matrix> tensorB = new ArrayList<>();
        for(int i = 0; i < tensorA.dimension; i++){
            tensorB.add(matrixB);
        }
        return dot(tensorA, new Tensor(tensorB));
    }

    public static Tensor dot(Matrix matrixA, Tensor tensorB){
        List<Matrix> tensorA = new ArrayList<>();
        for(int i = 0; i < tensorB.dimension; i++){
            tensorA.add(matrixA);
        }
        return dot(new Tensor(tensorA), tensorB);
    }

    public static Tensor transpose(Tensor tensor) {
        List<Matrix> result = new ArrayList<>();
        for (int dim = 0; dim < tensor.dimension; dim++) {
            result.add(Matrix.transpose(tensor.matrices.get(dim)));
        }
        return new Tensor(result);
    }

    public static Tensor sum(Tensor tensor){

    }
}
