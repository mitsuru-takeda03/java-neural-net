package matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * 行列クラス
 * クラスメソッドで四足演算が可能
 * 他にも行列積、転置も可能
 * TODO inverseの実装。必要ないのでやらないが。。。
 */
public class Matrix {
    protected final double[][] value;
    public final int row;
    public final int col;
    private final String enter = System.lineSeparator();

    private Matrix() {
        value = new double[1][1];
        row = 1;
        col = 1;
    };

    public Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        value = new double[row][col];
    };

    public Matrix(int row, int col, double initValue){
        this.row = row;
        this.col = col;
        value = new double[row][col];
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                value[i][j] = initValue;
            }
        }
    }

    public Matrix(ArrayList<ArrayList<Double>> value) {
        this.row = value.size();
        this.col = value.get(0).size();
        this.value = new double[this.row][this.col];
        for(int row = 0; row < this.row; row++){
            for(int col = 0; col < this.col; col++){
                this.value[row][col] = value.get(row).get(col);
            }
        }
    };

    public Matrix(double[][] value){
        row = value.length;
        col = value[0].length;
        this.value = value;
    };

    /**
     * for shallow copy
     * @return copied Matrix
     */
    public Matrix copy(){
        Matrix matrixCopy = new Matrix(this.row, this.col);
        for(int row = 0; row < this.row; row++){
            for(int col = 0; col < this.col; col++){
                matrixCopy.value[row][col] = this.value[row][col];
            }
        }
        return matrixCopy;
    }

    /**
     * 行に関してスライスを行う。pythonのスライスと異なり参照渡しではない。
     * @param index スライスしたい行の行番号のリスト
     * @return スライス後の行列
     */
    public Matrix sliceRow(List<Integer> index){
        Matrix sliceMat = new Matrix(index.size(), value[0].length);
        int row = 0;
        for(int id : index){
            for(int col = 0; col < value[0].length; col++)
                sliceMat.value[row][col] = value[id][col];
            row++;
        }
        return sliceMat;
    }

    /**
     * 行列を2次元ArrayListに変換。
     * もっぱら行列をCSVHandlerで保存するときに使う。
     * @return 2次元ArrayList
     */
    public ArrayList<ArrayList<Double>> toList(){
        ArrayList<ArrayList<Double>> listMat = new ArrayList<ArrayList<Double>>();
        for(int row = 0; row < this.row; row++) {
            ArrayList<Double> listLine = new ArrayList<>();
            for(int col = 0; col < this.col; col++){
                listLine.add(value[row][col]);
            }
            listMat.add(listLine);
        }
        return listMat;
    }

    /**
     * 足し算
     * @param matA
     * @param matB
     * @return 和の行列
     */
    public static Matrix add(Matrix matA, Matrix matB){
        if (matA.row != matB.row || matA.col != matB.col){
            // なんかexception投げたい
            System.out.println("invalid input in add method");
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
     * @return 引いた行列
     */
    public static Matrix subtract(Matrix matA, Matrix matB){
        return add(matA, dotH(matB, -1.));
    }

    /**
     * 行列積
     * @param matA
     * @param matB
     * @return 行列積
     */
    public static Matrix dot(Matrix matA, Matrix matB){
        if (matA.col != matB.row){
            // なんかexception投げたい
            System.out.println("invalid input in dot method");
            return new Matrix();
        }
        Matrix result = new Matrix(matA.row, matB.col);
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
            System.out.println("invalid input in dotH method");
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
    public static Matrix divide(Matrix matA, Matrix matB){
        if (matA.row != matB.row || matA.col != matB.col){
            // なんかexception投げたい
            System.out.println("invalid input in divide method");
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
    public static Matrix dotH(double a, Matrix matB) { return dotH(matB, a); }
    public static Matrix divide(Matrix matA, double b){
        return divide(matA, new Matrix(matA.row, matA.col, b));
    }
    public static Matrix divide(double a, Matrix matB){
        return divide(new Matrix(matB.row, matB.col, a), matB);
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

    @Override
    public String toString(){
        String string = "---------------------------------------------------";
        string += enter;
        for(int row = 0; row < this.row; row++){
            for(int col = 0; col < this.col; col++){
                string += value[row][col];
                string += ", ";
            }
            string += enter;
        }
        string += "---------------------------------------------------";
        return string;
    }
}
