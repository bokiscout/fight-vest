package dobrink.fight_vest;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Dobrin on 15-Jun-17.
 */

public class FightListFragment extends android.support.v4.app.ListFragment {

    private ArrayListFightSingleton listOfFights;
    public interface OnFightSelected {
        public void onFightSelected(int id);
    }

    private OnFightSelected mCallback;

    private listFightsAdapter adapterFights ;

    private int MatchID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listOfFights = (ArrayListFightSingleton)getActivity().getApplicationContext();
        listOfFights.makeFakeFights(10);
        adapterFights = new listFightsAdapter(getActivity(), listOfFights.getFights());
        setListAdapter(adapterFights);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Fight fight = listOfFights.getFights().get(position);
        MatchID =fight.getID();
        listOfFights.setSelectedFight(fight);
        listOfFights.setMatchID(MatchID);
        showToast("Fight ID: "+MatchID);

        openFightInfoFragment();
    }

    private void openFightInfoFragment() {
        FightInfoFragment nextFrag = new FightInfoFragment();

        //this.getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,nextFrag,null).commit();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragmentContainer, nextFrag,null).commit();
    }

    private void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}
