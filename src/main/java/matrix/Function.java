package matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用した行列計算のごった煮
 */
public class Function {

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

    /**
     * 要素毎に対数をとる
     * @param mat
     * @return
     */
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
     * softmax関数
     * @param mat
     * @param axis
     * @return
     */
    public static Matrix softMax(Matrix mat, int axis){
        if(axis == 0) {
            Matrix expMat = exp(mat);
            Matrix sumExpMat = sum(expMat, 0);
            return Matrix.divide(expMat, extend(sumExpMat, expMat.row));
        }
        else{
            Matrix expMat = exp(mat);
            Matrix sumExpMat = sum(expMat, 1);
            return Matrix.divide(expMat, extend(sumExpMat, expMat.col));
        }
    }

    /**
     * クロスエントロピー
     * @param matA
     * @param matB
     * @return
     */
    public static double crossEntropyError(Matrix matA, Matrix matB){
        Matrix crossEntropy = Matrix.dotH(-1., Matrix.dotH(matA, log(matB)));
        return sum(sum(crossEntropy, 0), 1).value[0][0];
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

    /**
     * 正解ラベル等をワンホットベクターにして返す。
     * @param mat
     * @param dimension
     * @return
     */
    public static Matrix makeOneHot(Matrix mat, int dimension){
        Matrix oneHotMat = new Matrix(mat.row, dimension);
        for(int i = 0; i < mat.row; i++){
            oneHotMat.value[i][(int)mat.value[i][0]] = 1;
        }
        return oneHotMat;
    }

    /**
     * 最大値のindexを返す。
     * @param mat
     * @param axis
     * @return
     */
    public static Matrix maxArg(Matrix mat, int axis){
        if(axis == 0){
            Matrix argMat = new Matrix(1, mat.col);
            for(int col = 0; col < mat.col; col++){
                double tempMax = 0;
                int tempMaxArg = 0;
                for(int row = 0; row < mat.row; row++){
                    if(mat.value[row][col] > tempMax){
                        tempMax = mat.value[row][col];
                        tempMaxArg = row;
                    }
                }
                argMat.value[0][col] = tempMaxArg;
            }
            return argMat;
        }
        else{
            Matrix argMat = new Matrix(mat.row, 1);
            for(int row = 0; row < mat.row; row++){
                double tempMax = 0;
                int tempMaxArg = 0;
                for(int col = 0; col < mat.col; col++){
                    if(mat.value[row][col] > tempMax){
                        tempMax = mat.value[row][col];
                        tempMaxArg = col;
                    }
                }
                argMat.value[row][0] = tempMaxArg;
            }
            return argMat;
        }
    }

    public static Matrix minArg(Matrix mat, int axis){
        if(axis == 0){
            Matrix argMat = new Matrix(1, mat.col);
            for(int col = 0; col < mat.col; col++){
                double tempMin = 0;
                int tempMinArg = 0;
                for(int row = 0; row < mat.row; row++){
                    if(mat.value[row][col] < tempMin){
                        tempMin = mat.value[row][col];
                        tempMinArg = row;
                    }
                }
                argMat.value[0][col] = tempMinArg;
            }
            return argMat;
        }
        else{
            Matrix argMat = new Matrix(mat.row, 1);
            for(int row = 0; row < mat.row; row++){
                double tempMin = 0;
                int tempMinArg = 0;
                for(int col = 0; col < mat.col; col++){
                    if(mat.value[row][col] < tempMin){
                        tempMin = mat.value[row][col];
                        tempMinArg = col;
                    }
                }
                argMat.value[row][0] = tempMinArg;
            }
            return argMat;
        }
    }

    public static Matrix max(Matrix mat, int axis){
        if(axis == 0){
            Matrix argMat = new Matrix(1, mat.col);
            for(int col = 0; col < mat.col; col++){
                double tempMax = 0;
                for(int row = 0; row < mat.row; row++){
                    if(mat.value[row][col] > tempMax){
                        tempMax = mat.value[row][col];
                    }
                }
                argMat.value[0][col] = tempMax;
            }
            return argMat;
        }
        else{
            Matrix argMat = new Matrix(mat.row, 1);
            for(int row = 0; row < mat.row; row++){
                double tempMax = 0;
                for(int col = 0; col < mat.col; col++){
                    if(mat.value[row][col] > tempMax){
                        tempMax = mat.value[row][col];
                    }
                }
                argMat.value[row][0] = tempMax;
            }
            return argMat;
        }
    }

    public static Matrix min(Matrix mat, int axis){
        if(axis == 0){
            Matrix argMat = new Matrix(1, mat.col);
            for(int col = 0; col < mat.col; col++){
                double tempMin = 0;
                for(int row = 0; row < mat.row; row++){
                    if(mat.value[row][col] < tempMin){
                        tempMin = mat.value[row][col];
                    }
                }
                argMat.value[0][col] = tempMin;
            }
            return argMat;
        }
        else{
            Matrix argMat = new Matrix(mat.row, 1);
            for(int row = 0; row < mat.row; row++){
                double tempMin = 0;
                for(int col = 0; col < mat.col; col++){
                    if(mat.value[row][col] < tempMin){
                        tempMin = mat.value[row][col];
                    }
                }
                argMat.value[row][0] = tempMin;
            }
            return argMat;
        }
    }

    public static double max(Matrix mat){
        return max(max(mat, 0),1).value[0][0];
    }

    public static double min(Matrix mat){
        return min(min(mat, 0),1).value[0][0];
    }

    /**
     * 正答率計算用。
     * @param matA
     * @param matB
     * @return
     */
    public static double checkAccuracy(Matrix matA, Matrix matB){
        if(matA.row != matB.row || matA.col != matB.col)
            return 0.;
        int count = 0;
        for(int row = 0; row < matA.row; row++){
            for(int col = 0; col < matA.col; col++){
                if(matA.value[row][col] == matB.value[row][col]){
                    count += 1.;
                }
            }
        }
        return (double)count / (matA.row * matA.col);
    }

    /**
     * Bをバッチサイズに合わせて拡張するための処理
     * @param mat
     * @param times
     * @return
     */
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
}
