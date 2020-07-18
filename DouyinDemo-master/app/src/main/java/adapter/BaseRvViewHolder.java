package adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

/**
 *Recycler ViewHolder基类
 */
public class BaseRvViewHolder extends RecyclerView.ViewHolder {

    public BaseRvViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
