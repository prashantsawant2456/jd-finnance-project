package com.saiprashant.jdfinnancialproject.ListScreen;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.saiprashant.jdfinnancialproject.APIHit.HitAPI;
import com.saiprashant.jdfinnancialproject.Activity.MainActivity;
import com.saiprashant.jdfinnancialproject.Adapter.UserListAdapter;
import com.saiprashant.jdfinnancialproject.Model.UserDetails;
import com.saiprashant.jdfinnancialproject.R;
import com.saiprashant.jdfinnancialproject.Utility.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.saiprashant.jdfinnancialproject.Urls.Urls.getApi;

public class List_Fragment extends Fragment implements ListInterface {
    MainActivity mainActivity;
    Context context;
    RecyclerView rv_userlist;
    TextView tv_nodata;
    ShimmerFrameLayout ly_shimmer;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<UserDetails> detailsList;
    UserListAdapter userlistAdapter;
    UserDetails userDetails;
    public ListPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        context = getContext();
        presenter = new ListPresenter(this, mainActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, null);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onBindView(view);
    }

    @Override
    public void onBindView(View view) {
        rv_userlist = view.findViewById(R.id.rv_userlist);
        tv_nodata = view.findViewById(R.id.tv_nodata);
        ly_shimmer = view.findViewById(R.id.ly_shimmer);
        ly_shimmer.startShimmer();
        layoutManager = new LinearLayoutManager(context);
        rv_userlist.setLayoutManager(layoutManager);
        presenter.getData();
    }

    @Override
    public void getList(ArrayList<UserDetails> detailsList) {
        if (detailsList.size() == 0) {
            tv_nodata.setVisibility(View.VISIBLE);
            rv_userlist.setVisibility(View.INVISIBLE);
        } else {
            userlistAdapter = new UserListAdapter(mainActivity, detailsList);
            rv_userlist.setAdapter(userlistAdapter);
            ly_shimmer.hideShimmer();
            rv_userlist.setVisibility(View.VISIBLE);
            tv_nodata.setVisibility(View.GONE);
        }
    }
}

