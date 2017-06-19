package dobrink.fight_vest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private FightLogicHelper fightLogic;

    private String TAG ;
    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();
            fightLogic = FightLogicHelper.getInstance();
/*        if (fightLogic.getFights().isEmpty()){
            fightLogic.makeFakeFights(10);
        }*/

            bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);
            bottomNavigation.setOnNavigationItemSelectedListener(mItemSelectedListener);

            //Default fragment on start
            Fragment tempFrag = new FightListFragment();
            beginTrans(tempFrag, "fragFightList");
            //Sets highlighted button on naviagtion menu
            Menu menu = ((BottomNavigationView) findViewById(R.id.navigation)).getMenu();
            menu.findItem(R.id.action_fight_list).setChecked(true);
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    switch (id) {
                        case R.id.action_bluetooth:
                            TAG = "fragBTDevices";
                            fragment = fragmentManager.findFragmentByTag(TAG);
                            if (fragment == null) {
                                fragment = new BTDevicesFragment();
                            }
                            beginTrans(fragment,TAG);
                            break;
                        case R.id.action_fight_list:
                            TAG = "fragFightList";
                            fragment = fragmentManager.findFragmentByTag(TAG);
                            if (fragment == null) {
                                fragment = new FightListFragment();
                            }
                            beginTrans(fragment,TAG);
                            break;
                        case R.id.action_fight_info:
                            TAG = "fragFightInfo";
                            fragment = fragmentManager.findFragmentByTag(TAG);
                            if (fragment == null) {
                                Log.d("MainActivity", "CREATING NEW FightInfoFragment" );
                                fragment = new FightInfoFragment();
                            }
                            beginTrans(fragment,TAG);
                            break;
                    }
                    return true;
                }
            };

    private void beginTrans(Fragment fragment, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(tag);
        transaction.replace(R.id.fragmentContainer, fragment,tag).commit();
    }
}
