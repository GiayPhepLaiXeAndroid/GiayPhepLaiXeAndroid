package com.example.thibanglaixe.untilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.List;

public class CustomPieChart extends View {
    private Paint paint;
    private List<Float> values;
    private List<Integer> colors;


    public CustomPieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void setData(List<Float> values, List<Integer> colors) {
        this.values = values;
        this.colors = colors;
        invalidate(); // Vẽ lại khi có dữ liệu mới
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (values == null || colors == null || values.size() != colors.size()) return;

        float total = 0f;
        for (float val : values) total += val;

        float startAngle = 0f;
        // Giả sử chiều rộng của View là width (ví dụ: 500)
        int width = getWidth();

        // Kích thước của hình vuông
        int size = 600;

        // Tính toán vị trí
        float left = (width - size) / 2;
        float right = left + size;
        float top = 150;  // Có thể đặt cố định hoặc tính toán nếu cần
        float bottom = top + size;

        // Tạo RectF để vẽ hình vuông
        RectF rectF = new RectF(left, top, right, bottom);


        for (int i = 0; i < values.size(); i++) {
            float sweepAngle = (values.get(i) / total) * 360;
            paint.setColor(colors.get(i));
            canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);
            startAngle += sweepAngle;
        }
    }
}
