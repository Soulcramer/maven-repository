package studio.lunabee.android.core.fdv;

import android.graphics.Paint;
import java.io.Serializable;

class SerializablePaint extends Paint implements Serializable {

    SerializablePaint(int flags) {
        super(flags);
    }

    SerializablePaint(SerializablePaint paint) {
        super(paint);
    }
}
