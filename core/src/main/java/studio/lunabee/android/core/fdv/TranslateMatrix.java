package studio.lunabee.android.core.fdv;

import android.graphics.Matrix;

class TranslateMatrix extends Matrix {

    TranslateMatrix(float dx, float dy) {
        super();
        setScale(dx, dy);
    }

    TranslateMatrix(Matrix matrix) {
        super(matrix);
    }
}
