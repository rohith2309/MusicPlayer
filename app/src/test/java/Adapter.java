import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;

import java.util.ArrayList;

// Extends the Adapter class to RecyclerView.Adapter
// and implement the unimplemented methods
public class Adapter extends RecyclerView.Adapter<ViewHolder> {
    ArrayList AlbumImg, songName;
    Context context;

    // Constructor for initialization
    public Adapter(Context context, ArrayList AlbumImg, ArrayList songName) {
        this.context = context;
        this.songName = songName;
        this.AlbumImg = AlbumImg;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout(Instantiates list_item.xml
        // layout file into View object)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);

        // Passing view to ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Binding data to the into specified position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TypeCast Object to int type
        int res = (int) AlbumImg.get(position);
        holder.images.setImageResource(res);
        holder.text.setText((String) songName.get(position));
    }

    @Override
    public int getItemCount() {
        // Returns number of items
        // currently available in Adapter
        return AlbumImg.size();
    }

}
