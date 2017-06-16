package dobrink.fight_vest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ArrayListFightSingleton listOfFights;
    private int MatchID = -1;
    private Fight selectedFight;
    private static String parsedMsg;
    private static int player = -1;
    private static int strength = -1;

    protected int getMatchID() {
        return MatchID;
    }

    protected static String getParsedMsg() {
        return parsedMsg;
    }

    protected static int getPlayer() {
        return player;
    }

    protected static int getStrength() {
        return strength;
    }

    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listOfFights = (ArrayListFightSingleton) getApplicationContext();

        bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        fragmentManager = getSupportFragmentManager();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_bluetooth:
                        fragment = new BTDevicesFragment();
                        break;
                    case R.id.action_fight_list:
                        fragment = new FightListFragment();
                        break;
                    case R.id.action_fight_info:
                        fragment = new FightInfoFragment();
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragmentContainer, fragment).commit();

                return true;
            }
        });
    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}
