package dobrink.fight_vest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import dobrink.fight_vest.models.Fight;
import dobrink.fight_vest.models.Fighter;

/**
 * Created by Dobrin on 14-Jun-17.
 */

@SuppressWarnings("DefaultFileTemplate")
class listFightsAdapter extends ArrayAdapter<Fight>{
    private final static String URL = "http://www.fv.pdtransverzalec.org.mk";

    public listFightsAdapter(Context context, ArrayList<Fight> fights) {
        super(context, 0, fights);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        // Get the data item for this position
        Fight fight = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_fights, parent, false);
        }

        TextView tvFightID = convertView.findViewById(R.id.tvFightID);
        TextView tvFightStartTime = convertView.findViewById(R.id.tvFightStartTime);
        tvFightStartTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_access_time_black_24dp, 0, 0, 0);
        TextView tvFightLocation = convertView.findViewById(R.id.tvFightLocation);
        tvFightLocation.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_location_on_black_24dp, 0, 0, 0);
        TextView tvFightDescription = convertView.findViewById(R.id.tvFightDescription);
        ImageView imageViewFighter1 = convertView.findViewById(R.id.imageViewFighter1);
        ImageView imageViewFighter2 = convertView.findViewById(R.id.imageViewFighter2);
        TextView tvFighter1FullName = convertView.findViewById(R.id.tvFighter1FullName);
        TextView tvFighter2FullName = convertView.findViewById(R.id.tvFighter2FullName);

        //get Fighters info displayed
        for (int i = 0; i < fight.getFightFighters().size(); i++) {
            Fighter fighter = fight.getFightFighters().get(i).getFighter();
            //Fighter 1
            if (i == 0) {
                Picasso.with(getContext()).load(URL+fighter.getAvatarUrl()).placeholder(R.mipmap.ic_launcher).into(imageViewFighter1);
                tvFighter1FullName.setText(fighter.getFullName());
            }
            //Fighter 2
            else {
                Picasso.with(getContext()).load(URL+fighter.getAvatarUrl()).placeholder(R.mipmap.ic_launcher).into(imageViewFighter2);
                tvFighter2FullName.setText(fighter.getFullName());
            }
        }
        //Match info displayed
        tvFightID.setText("Fight ID: "+String.valueOf(fight.getID()));
        tvFightStartTime.setText("Fight Start: "+fight.getStartTime());
        tvFightLocation.setText(fight.getAddress()+", "+fight.getCity()+", "+fight.getCountry());
        tvFightDescription.setText(fight.getDescription());
        return convertView;
    }
    // Date to string
    private String dateFormat(Date date) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        return formatter.format(date);
    }

}
