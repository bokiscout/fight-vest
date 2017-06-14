package dobrink.fight_vest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dobrin on 14-Jun-17.
 */

public class listFightsAdapter extends ArrayAdapter<Fight>{

    public listFightsAdapter(Context context, ArrayList<Fight> fights) {
        super(context, 0, fights);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the data item for this position
        Fight fight = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_fights, parent, false);
        }

        TextView tvFightID = (TextView) convertView.findViewById(R.id.tvFightID);
        TextView tvMatchInfo = (TextView) convertView.findViewById(R.id.tvMatchInfo);
        TextView tvFighter2Avatar = (TextView) convertView.findViewById(R.id.tvFighter2Avatar);
        TextView tvFighter1Avatar = (TextView) convertView.findViewById(R.id.tvFighter1Avatar);
        TextView tvFighter1Info = (TextView) convertView.findViewById(R.id.tvFighter1Info);
        TextView tvFighter2Info = (TextView) convertView.findViewById(R.id.tvFighter2Info);

        //get Figters info displayed
        for (int i = 0; i < fight.getFightFighters().size(); i++) {
            StringBuilder sb = new StringBuilder();
            Fighter fighter = null;
            try {
                fighter = (Fighter) fight.getFightFighters().get(i).getFighter().clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            sb.append(fighter.getAvatar()).append(System.getProperty("line.separator"));// name + newline
            sb.append(fighter.getFighterCategory().getName()).append(System.getProperty("line.separator"));
            sb.append(fighter.getCounty()).append(", ").append(fighter.getCity()).append(System.getProperty("line.separator"));
            sb.append(dateFormat(fighter.getBirthDate())); // convert Date to dd/MM/yyyy
            //Figter 1
            if (i == 0){
                tvFighter1Avatar.setText(fighter.getFullName());
                tvFighter1Info.setText(sb.toString());
            }
            //Figter 2
            else {
                tvFighter2Avatar.setText(fighter.getFullName());
                tvFighter2Info.setText(sb.toString());
            }
        }
        //Match info displayed
        tvFightID.setText(String.valueOf(fight.getID()));
        StringBuilder sb = new StringBuilder();
        sb.append("Type: ").append(fight.getFightType().getName()).append(System.getProperty("line.separator"));
        sb.append(fight.getCountry()).append(" ").append(fight.getCity()).append(" ").append(fight.getAddress()).append(System.getProperty("line.separator"));
        sb.append(fight.getDescription());
        tvMatchInfo.setText(sb.toString());

        return convertView;
    }
    // Date to string
    private String dateFormat(Date birthDate) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String result = formatter.format(birthDate);
        return result;
    }

}
