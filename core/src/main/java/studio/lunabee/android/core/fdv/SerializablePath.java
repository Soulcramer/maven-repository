package studio.lunabee.android.core.fdv;

import android.graphics.Path;
import java.io.Serializable;

class SerializablePath extends Path implements Serializable {

    SerializablePath() {
        super();
    }

    SerializablePath(SerializablePath path) {
        super(path);
    }
}
