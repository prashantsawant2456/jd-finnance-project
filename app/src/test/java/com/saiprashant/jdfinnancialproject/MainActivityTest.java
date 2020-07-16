package com.saiprashant.jdfinnancialproject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.saiprashant.jdfinnancialproject.Activity.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.saiprashant.jdfinnancialproject.Urls.Urls.REPLACE;
import static com.saiprashant.jdfinnancialproject.Urls.Urls.home_screen;

public class MainActivityTest {
    @Mock
    MainActivity mainActivity;
    @Mock
    FragmentManager FM;
    @Mock
    Fragment ReplacingFragment;

    @Mock
    FragmentTransaction ft;

    @Before
    public void Testfragment() {
        MockitoAnnotations.initMocks(this);
        mainActivity = new MainActivity();
        FM = mainActivity.getSupportFragmentManager();
        ft = FM.beginTransaction();
    }

    @Test
    public void testFragment() {
        mainActivity.FragmentManagement(home_screen, REPLACE, false, null);
    }

}
