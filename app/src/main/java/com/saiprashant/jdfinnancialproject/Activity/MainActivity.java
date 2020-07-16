package com.saiprashant.jdfinnancialproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.saiprashant.jdfinnancialproject.HomeScreen.HomeFragment;
import com.saiprashant.jdfinnancialproject.ListScreen.List_Fragment;
import com.saiprashant.jdfinnancialproject.R;
import com.saiprashant.jdfinnancialproject.UpdateData.Update_Fragment;
import static com.saiprashant.jdfinnancialproject.Urls.Urls.ADD;
import static com.saiprashant.jdfinnancialproject.Urls.Urls.REPLACE;
import static com.saiprashant.jdfinnancialproject.Urls.Urls.Update_Screen;
import static com.saiprashant.jdfinnancialproject.Urls.Urls.home_screen;
import static com.saiprashant.jdfinnancialproject.Urls.Urls.list_screen;

public class MainActivity extends AppCompatActivity {
    Context context;
    FrameLayout fragment_container;
    FragmentTransaction ft;
    FragmentManager FM;
    Fragment ReplacingFragment;
    HomeFragment homeFragment;
    public List_Fragment list_fragment;
    Update_Fragment update_fragment;
    String current_tag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        fragment_container = findViewById(R.id.fragment_container);
        FragmentManagement(home_screen, REPLACE, false, null);

    }

    public void FragmentManagement(String Tag, String addReplace,
                                   boolean addToBackstack, Bundle bundle) {

        FM = getSupportFragmentManager();
        ft = FM.beginTransaction();
        if (FM.findFragmentByTag(Tag) == null) {
            switch (Tag) {
                case home_screen:
                    FM.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    homeFragment = new HomeFragment();
                    ReplacingFragment = homeFragment;
                    break;

                case list_screen:
                    FM.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    list_fragment = new List_Fragment();
                    ReplacingFragment = list_fragment;
                    break;
                case Update_Screen:
                    FM.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    update_fragment = new Update_Fragment();
                    ReplacingFragment = update_fragment;
                    break;

            }
            FragmentTransaction ft = FM.beginTransaction();
            if (bundle != null) {
                ReplacingFragment.setArguments(bundle);
            }
            current_tag = Tag;
            if (addReplace.equals(ADD)) {
                ft.add(R.id.fragment_container, ReplacingFragment, Tag);
            } else {
                ft.replace(R.id.fragment_container, ReplacingFragment, Tag);
            }
            if (addToBackstack) {
                ft.addToBackStack(Tag);
            }
            ft.commitAllowingStateLoss();
        } else {
            current_tag = Tag;
            FM.popBackStack(Tag, 0);
        }
    }


}
