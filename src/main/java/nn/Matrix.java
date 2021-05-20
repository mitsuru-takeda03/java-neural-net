package nn;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    private final double[][] value;
    public final int row;
    public final int col;

    private Matrix() {
        value = new double[1][1];
        row = 1;
        col = 1;
    };

    Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        value = new double[row][col];
    };

    Matrix(List<List<Double>> value) {
        this.row = value.size();
        this.col = value.get(0).size();
        this.value = new double[this.row][this.col];
        for(int row = 0; row < this.row; row++){
            for(int col = 0; col < this.col; col++){
                this.value[row][col] = value.get(row).get(col);
            }
        }
    };

    Matrix(double[][] value){
        row = value.length;
        col = value[0].length;
        this.value = value;
    };

    public static Matrix add(Matrix matA, Matrix matB){
        if (matA.row != matB.row || matA.col != matB.col){
            // なんかexception投げたい
            return new Matrix();
        }
        double[][] result = new double[matA.row][matA.col];
        for(int row = 0; row < matA.row; row++){
            for(int col = 0; col < matA.col; col++){
                result[row][col] = matA.value[row][col] + matB.value[row][col];
            }
        }
        return new Matrix(result);
    }

    public static Matrix subtract(Matrix matA, Matrix matB){
        return add(matA, dot(matB, -1.));
    }

    public static Matrix dot(Matrix matA, double b){
        double[][] result = new double[matA.row][matA.col];
        for(int row = 0; row < matA.row; row++){
            for(int col = 0; col < matA.col; col++){
                result[row][col] = matA.value[row][col] + b;
            }
        }
        return new Matrix(result);
    }

    public static Matrix dot(double a, Matrix matB){
        return dot(matB, a);
    }

    public static Matrix dot(Matrix matA, Matrix matB){
        if (matA.row != matB.col || matA.col != matB.row){
            // なんかexception投げたい
            return new Matrix();
        }
        Matrix result = new Matrix(new double[matA.row][matB.col]);
        for(int row = 0; row < result.row; row++){
            for(int col = 0; col < result.col; col++){
                double product = 0;
                for(int matACol = 0; matACol < matA.col; matACol++)
                    product += matA.value[row][matACol] * matB.value[matACol][col];
                result.value[row][col] = product;
            }
        }
        return result;
    }

    public static Matrix transpose(Matrix mat){
        double[][] result = new double[mat.col][mat.row];
        for(int row = 0; row < mat.row; row++){
            for(int col = 0; col < mat.col; col++){
                result[col][row] = mat.value[row][col];
            }
        }
        return new Matrix(result);
    }

    /**
     *
     */
    public static Matrix sum(Matrix mat){
        double[][] result = new double[1][mat.col];
        for(int row = 0; row < mat.row; row++){
            for(int col = 0; col < mat.col; col++){
                result[0][col] += mat.value[row][col];
            }
        }
        return new Matrix(result);
    }

    public static Matrix sum(Matrix mat, int axis){
        if(axis == 0)
            return sum(mat);
        else{
            double[][] result = new double[mat.row][1];
            for(int row = 0; row < mat.row; row++){
                for(int col = 0; col < mat.col; col++){
                    result[row][0] += mat.value[row][col];
                }
            }
            return new Matrix(result);
        }
    }
}
