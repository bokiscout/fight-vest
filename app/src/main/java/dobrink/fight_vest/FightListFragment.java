package dobrink.fight_vest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;


import dobrink.fight_vest.models.Fight;

/**
 * Created by Dobrin on 15-Jun-17.
 */

public class FightListFragment extends SwipeRefreshListFragment{
    private static final String LOG_TAG = FightListFragment.class.getSimpleName();
    ProgressBar progressBar;
    private FightLogicHelper fightLogic;
    private listFightsAdapter adapterFights ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate");
        fightLogic = FightLogicHelper.getInstance();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("fightListUpdated"));

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(LOG_TAG, "onViewCreated");
        initiateRefresh();
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                initiateRefresh();
            }
        });
    }

    private void initiateRefresh() {
        Log.i(LOG_TAG, "initiateRefresh");
        setRefreshing(true);
        VolleySingleton.getInstance(getContext()).fetchFightsOnline();
    }
    private void onRefreshComplete() {
        Log.i(LOG_TAG, "onRefreshComplete");

        // Remove all items from the ListAdapter, and then replace them with the new items
        adapterFights = new listFightsAdapter(getActivity(), fightLogic.getFights());
        setListAdapter(adapterFights);
        setRefreshing(false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("FIGHT LIST FRAGMENT", "onListItemClick()" );

        super.onListItemClick(l, v, position, id);
        Fight fight = fightLogic.getFights().get(position);
        fightLogic.setSelectedFight(fight);
        fightLogic.setMatchID(fightLogic.getMatchID());

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

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onRefreshComplete();
        }
    };

}
