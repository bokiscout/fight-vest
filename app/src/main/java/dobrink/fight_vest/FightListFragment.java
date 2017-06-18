package dobrink.fight_vest;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Dobrin on 15-Jun-17.
 */

public class FightListFragment extends android.support.v4.app.ListFragment {

    private FightLogicHelper fightLogic;
    public interface OnFightSelected {
        public void onFightSelected(int id);
    }

    private OnFightSelected mCallback;

    private listFightsAdapter adapterFights ;

    private int MatchID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fightLogic = FightLogicHelper.getInstance();

        adapterFights = new listFightsAdapter(getActivity(), fightLogic.getFights());
        setListAdapter(adapterFights);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("FIGHT LIST FRAGMENT", "onListItemClick()" );

        super.onListItemClick(l, v, position, id);
        Fight fight = fightLogic.getFights().get(position);
        MatchID =fight.getID();
        fightLogic.setSelectedFight(fight);
        fightLogic.setMatchID(MatchID);

        Log.d("FIGHT LIST FRAGMENT", "onListItemClick() -> fight:" + fight );

        openFightInfoFragment();
    }

    private void openFightInfoFragment() {
        FragmentManager fm = getFragmentManager();
        Fragment nextFrag;
        nextFrag = fm.findFragmentByTag("fragFightInfo");
        if (nextFrag == null) {
            Log.d("MainActivity", "CREATING NEW FightInfoFragment" );
            nextFrag = new FightInfoFragment();
        }
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack("fragFightInfo");
        transaction.replace(R.id.fragmentContainer, nextFrag,"fragFightInfo").commit();

        //Sets highlighted button on naviagtion menu
        Menu menu = ((BottomNavigationView) getActivity().findViewById(R.id.navigation)).getMenu();
        menu.findItem(R.id.action_fight_info).setChecked(true);
    }

    private void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}
