import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;

// Initializing the Views
public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView images;
    TextView text;

    public ViewHolder(View view) {
        super(view);
        images = (ImageView) view.findViewById(R.id.Albumimg);
        text = (TextView) view.findViewById(R.id.SongName);
    }
}
