package pranjal.learning.organdonation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class myAdapterQ extends FirebaseRecyclerAdapter<modelQ, myAdapterQ.viewHolder> {

    // fetch all the data form model class
    public myAdapterQ(@NonNull FirebaseRecyclerOptions<modelQ> options) {
        super(options);
    }

    // put data to cardView
    @Override
    protected void onBindViewHolder(@NonNull final viewHolder holder, final int position, @NonNull final modelQ modelQ) {

        holder.question.setText(modelQ.getQuestion());
        holder.answer.setText(modelQ.getAnswer());

    }

    // creating xml template "i.e. singlerow.xml" file template
    // i.e. creating runtime view
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowq, parent, false);
        return new viewHolder(view);
    }

    // creating data holders
    // accessing UI elements of "singlerow.xml" file
    class viewHolder extends RecyclerView.ViewHolder {

        TextView question, answer;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.question);
            answer = itemView.findViewById(R.id.answer);
        }
    }
}
