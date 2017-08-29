package studio.lunabee.android.core.fdv;

import android.graphics.Paint;
import android.graphics.Path;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.ArrayList;

class HistoryPath implements Parcelable, Serializable {


    // Parcelable CREATOR class
    public static final Creator<HistoryPath> CREATOR = new Creator<HistoryPath>() {
        @Override
        public HistoryPath createFromParcel(Parcel in) {
            return new HistoryPath(in);
        }

        @Override
        public HistoryPath[] newArray(int size) {
            return new HistoryPath[size];
        }
    };
    static final long serialVersionUID = 41L;
    private static final String TAG = "HistoryPath";
    private transient Paint paint = null;
    private transient Path path = null;
    private ArrayList<Point> points = new ArrayList<>();
    private int paintColor;
    private int paintAlpha;
    private float paintWidth;
    private float originX, originY;
    private boolean isPoint;

    HistoryPath(@NonNull ArrayList<Point> points, @NonNull Paint paint) {
        this.points = new ArrayList<>(points);
        this.paintColor = paint.getColor();
        this.paintAlpha = paint.getAlpha();
        this.paintWidth = paint.getStrokeWidth();
        this.originX = points.get(0).x;
        this.originY = points.get(0).y;
        this.isPoint = FreeDrawHelper.isAPoint(points);
    }

    // Parcelable stuff
    private HistoryPath(Parcel in) {
        in.readTypedList(points, Point.CREATOR);

        paintColor = in.readInt();
        paintAlpha = in.readInt();
        paintWidth = in.readFloat();

        originX = in.readFloat();
        originY = in.readFloat();

        isPoint = in.readByte() != 0;

        generatePath();
        generatePaint();
    }

    public void generatePath() {
        path = new Path();

        if (points != null) {
            boolean first = true;

            for (int i = 0; i < points.size(); i++) {

                Point point = points.get(i);

                if (first) {
                    path.moveTo(point.x, point.y);
                    first = false;
                } else {
                    path.lineTo(point.x, point.y);
                }
            }
        }
    }

    private void generatePaint() {
        paint = FreeDrawHelper.createPaintAndInitialize(paintColor, paintAlpha, paintWidth, isPoint);
    }

    public Path getPath() {
        if (path == null) {

            generatePath();
        }

        return path;
    }

    public float getOriginX() {
        return originX;
    }

    public void setOriginX(float originX) {
        this.originX = originX;
    }

    public float getOriginY() {
        return originY;
    }

    public void setOriginY(float originY) {
        this.originY = originY;
    }

    public int getPaintColor() {
        return paintColor;
    }

    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
    }

    public int getPaintAlpha() {
        return paintAlpha;
    }

    public void setPaintAlpha(int paintAlpha) {
        this.paintAlpha = paintAlpha;
    }

    public float getPaintWidth() {
        return paintWidth;
    }

    public void setPaintWidth(float paintWidth) {
        this.paintWidth = paintWidth;
    }

    public Paint getPaint() {

        if (paint == null) {
            generatePaint();
        }

        return paint;
    }

    public boolean isPoint() {
        return isPoint;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(points);

        dest.writeInt(paintColor);
        dest.writeInt(paintAlpha);
        dest.writeFloat(paintWidth);

        dest.writeFloat(originX);
        dest.writeFloat(originY);

        dest.writeByte((byte) (isPoint ? 1 : 0));
    }

    @Override
    public String toString() {
        return "Point: " + isPoint + "\n" + "Points: " + points + "\n" + "Color: " + paintColor + "\n" + "Alpha: "
                + paintAlpha + "\n" + "Width: " + paintWidth;
    }
}
