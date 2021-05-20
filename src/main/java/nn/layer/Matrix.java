package nn.layer;

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

    Matrix(int row, int col, double initValue){
        this.row = row;
        this.col = col;
        value = new double[row][col];
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                value[i][j] = initValue;
            }
        }
    }

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

    public Matrix sliceRow(List<Integer> index){
        Matrix sliceMat = new Matrix(index.size(), value[0].length);
        for(int id : index){
            for(int col = 0; col < value[0].length; col++)
                sliceMat.value[id][col] = value[id][col];
        }
        return sliceMat;
    }

    public ArrayList<ArrayList<Double>> toList(){
        ArrayList<ArrayList<Double>> listMat = new ArrayList<ArrayList<Double>>();
        for(int i = 0; i < row; i++) {
            listMat.add(new ArrayList(List.of(value[row])));
        }
        return listMat;
    }

    /**
     * 足し算
     * @param matA
     * @param matB
     * @return
     */
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

    /**
     * 引き算
     * @param matA
     * @param matB
     * @return
     */
    public static Matrix subtract(Matrix matA, Matrix matB){
        return add(matA, dotH(matB, -1.));
    }

    /**
     * 行列積
     * @param matA
     * @param matB
     * @return
     */
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

    /**
     * hadamard積
     * @param matA
     * @param matB
     * @return
     */
    public static Matrix dotH(Matrix matA, Matrix matB){
        if (matA.row != matB.row || matA.col != matB.col){
            // なんかexception投げたい
            return new Matrix();
        }
        double[][] result = new double[matA.row][matA.col];
        for(int row = 0; row < matA.row; row++){
            for(int col = 0; col < matA.col; col++){
                result[row][col] = matA.value[row][col] * matB.value[row][col];
            }
        }
        return new Matrix(result);
    }

    /**
     * 要素毎の除算
     * @return
     */
    public static Matrix devide(Matrix matA, Matrix matB){
        if (matA.row != matB.col || matA.col != matB.row){
            // なんかexception投げたい
            return new Matrix();
        }
        double[][] result = new double[matA.row][matB.col];
        for(int row = 0; row < matA.row; row++){
            for(int col = 0; col < matB.col; col++){
                result[row][col] = matA.value[row][col] / matB.value[row][col];
            }
        }
        return new Matrix(result);
    }


    // double用のキャスト機能
    public static Matrix add(Matrix matA, double b){
        return add(matA, new Matrix(matA.row, matA.col, b));
    }
    public static Matrix add(double a, Matrix matB){
        return add(matB, a);
    }
    public static Matrix subtract(Matrix matA, double b){
        return subtract(matA, new Matrix(matA.row, matA.col, b));
    }
    public static Matrix subtract(double a, Matrix matB){
        return subtract(matB, a);
    }
    public static Matrix dotH(Matrix matA, double b){
        return dotH(matA, new Matrix(matA.row, matA.col, b));
    }
    public static Matrix dotH(double a, Matrix matB) {
        return dotH(matB, a);
    }
    public static Matrix devide(Matrix matA, double b){
        return devide(matA, new Matrix(matA.row, matA.col, b));
    }
    public static Matrix devide(double a, Matrix matB){
        return devide(new Matrix(matB.row, matB.col, a), matB);
    }

    /**
     * 転置
     * @param mat
     * @return
     */
    public static Matrix transpose(Matrix mat){
        double[][] result = new double[mat.col][mat.row];
        for(int row = 0; row < mat.row; row++){
            for(int col = 0; col < mat.col; col++){
                result[col][row] = mat.value[row][col];
            }
        }
        return new Matrix(result);
    }

    public static Matrix sum(Matrix mat){
        return sum(mat, 0);
    }

    public static Matrix sum(Matrix mat, int axis){
        if(axis == 0) {
            double[][] sumOfRow = new double[1][mat.col];
            for (int row = 0; row < mat.row; row++) {
                for (int col = 0; col < mat.col; col++) {
                    sumOfRow[0][col] += mat.value[row][col];
                }
            }
            return new Matrix(sumOfRow);
        }
        else{
            double[][] sumOfCol = new double[mat.row][1];
            for(int row = 0; row < mat.row; row++){
                for(int col = 0; col < mat.col; col++){
                    sumOfCol[row][0] += mat.value[row][col];
                }
            }
            return new Matrix(sumOfCol);
        }
    }

    public static Matrix extend(Matrix mat, int times){
        if(mat.row == 1){
            double[][] extendRow = new double[times][mat.col];
            for(int row = 0; row < times; row++){
                for(int col = 0; col < mat.col; col++){
                    extendRow[row][col] = mat.value[0][col];
                }
            }
            return new Matrix(extendRow);
        }
        else if(mat.col == 1){
            double[][] extendCol = new double[mat.row][times];
            for(int row = 0; row < mat.row; row++){
                for(int col = 0; col < times; col++){
                    extendCol[row][col] = mat.value[row][0];
                }
            }
            return new Matrix(extendCol);
        }
        else{
            // TODO 何かexceptionを投げたい
            System.out.println("Cannot extend Matrix which row != 1 and col != 1");
            return mat;
        }
    }

    /**
     * 要素毎に指数乗する
     * @param mat
     * @return
     */
    public static Matrix exp(Matrix mat){
        double[][] expMat = new double[mat.row][mat.col];
        for(int row = 0; row < mat.row; row++){
            for(int col = 0; col < mat.col; col++){
                expMat[row][col] = Math.exp(mat.value[row][col]);
            }
        }
        return new Matrix(expMat);
    }

    public static Matrix log(Matrix mat){
        double[][] expMat = new double[mat.row][mat.col];
        for(int row = 0; row < mat.row; row++){
            for(int col = 0; col < mat.col; col++){
                expMat[row][col] = Math.log(mat.value[row][col]);
            }
        }
        return new Matrix(expMat);
    }
    /**
     * thresholdを超えたかどうかを判定し0 or 1の行列で返す。
     * @param mat
     * @param threshold
     * @return
     */
    public static Matrix isOver(Matrix mat, double threshold){
        double[][] result = new double[mat.row][mat.col];
        for(int row = 0; row < mat.row; row++){
            for(int col = 0; col < mat.col; col++){
                result[row][col] = mat.value[row][col] > threshold ? 1 : 0;
            }
        }
        return new Matrix(result);
    }

    public static Matrix softMax(Matrix mat, int axis){
        if(axis == 0) {
            Matrix expMat = exp(mat);
            Matrix sumExpMat = sum(expMat, 0);
            return devide(expMat, extend(sumExpMat, expMat.row));
        }
        else{
            Matrix expMat = exp(mat);
            Matrix sumExpMat = sum(expMat, 1);
            return devide(expMat, extend(sumExpMat, expMat.col));
        }
    }

    public static double crossEntropyError(Matrix matA, Matrix matB){
        Matrix crossEntropy = Matrix.dotH(-1., dotH(matA, log(matB)));
        return Matrix.sum(Matrix.sum(crossEntropy, 0), 1).value[0][0];
    }

}
