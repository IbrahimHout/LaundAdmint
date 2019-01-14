package dev.ibrahhout.shinystoreadmin;

import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.ibrahhout.shinystoreadmin.Adapters.MainViewPagerAdapter;
import dev.ibrahhout.shinystoreadmin.Fragments.FeedbacksFragment;
import dev.ibrahhout.shinystoreadmin.Fragments.ManageCategories;
import dev.ibrahhout.shinystoreadmin.Fragments.OrdersHistoryFargment;
import dev.ibrahhout.shinystoreadmin.Fragments.UsersFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainToolBar)
    Toolbar mainToolBar;

    @BindView(R.id.viewPager_mainChat)
    ViewPager viewPagerMainChat;

    @BindView(R.id.mainAppBar)
    AppBarLayout mainAppBar;
    @BindView(R.id.tablatout)
    TabLayout tablayoutMainChat;

    MainViewPagerAdapter mainViewPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_content);
        ButterKnife.bind(this);

         {


//            FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_USERS_NODE).keepSynced(true);


            setSupportActionBar(mainToolBar);
            getSupportActionBar().setTitle(R.string.appname);
            mainToolBar.setTitleTextColor(Color.WHITE);

//            mainToolBar.setNavigationIcon(R.drawable.ic_menu);
//            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                    this, drawer, mainToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//            drawer.addDrawerListener(toggle);
//            toggle.syncState();





            mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
             mainViewPagerAdapter.addFragment(new ManageCategories(), "Manage Categories");
             mainViewPagerAdapter.addFragment(new OrdersHistoryFargment(),"Orders" );
             mainViewPagerAdapter.addFragment(new UsersFragment(), "Users");
             mainViewPagerAdapter.addFragment(new FeedbacksFragment(), "FeedBacks");
//            mainViewPagerAdapter.addFragment(new ServicesHistoryFragment(), "History");
//            mainViewPagerAdapter.addFragment(new FeedBackFragment(), "Feedback");


            tablayoutMainChat.setupWithViewPager(viewPagerMainChat);
            viewPagerMainChat.setAdapter(mainViewPagerAdapter);
        }


    }


    @Override
    public void onBackPressed() {
            super.onBackPressed();

    }

    public ViewPager getViewPagerMainChat() {
        return viewPagerMainChat;
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

}
