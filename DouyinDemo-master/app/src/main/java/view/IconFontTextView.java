package view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 *显示分享界面的图标
 */
public class IconFontTextView extends AppCompatTextView {

    private static Typeface typeface;

    public IconFontTextView(Context context) {
        super(context);
    }

    public IconFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        typeface = Typeface.createFromAsset(getContext().getAssets(), "iconfont.ttf");
        setTypeface(typeface);
    }

}
