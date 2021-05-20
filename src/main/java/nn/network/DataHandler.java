package nn.network;

import matrix.Matrix;

import java.util.*;

/**
 * データハンドラーのインターフェース
 * つまりデータへのゲッターがあるだけで良い。
 * これだけ守れば他のデータセットにも適用可能。
 */
interface DataHandler {

    public Map<String, Matrix> get(List<Integer> index);

    public Map<String, Matrix> getBatch(int batchSize);

    public int size();
}
