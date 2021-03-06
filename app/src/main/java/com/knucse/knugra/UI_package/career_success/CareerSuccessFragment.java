package com.knucse.knugra.UI_package.career_success;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knucse.knugra.PD_package.Graduation_Info_package.Graduation_Info_List;
import com.knucse.knugra.R;
import com.knucse.knugra.UI_package.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class CareerSuccessFragment extends Fragment {
    private CareerSuccessViewModel careerSuccessViewModel;
    private static ArrayList<RecyclerItem> mData = new ArrayList<RecyclerItem>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        careerSuccessViewModel =
                ViewModelProviders.of(this).get(CareerSuccessViewModel.class);
        View root = inflater.inflate(R.layout.fragment_career_success, container, false);
        final Spinner trackSpinner = (Spinner)root.findViewById(R.id.track_spinner);

        final RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_career_success);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        CareerSuccessAdapter cs_adapter = new CareerSuccessAdapter(mData);
        recyclerView.setAdapter(cs_adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        final ArrayAdapter trackAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.track, android.R.layout.simple_dropdown_item_1line);

        trackSpinner.setAdapter(trackAdapter);
        trackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String[]> data = new ArrayList<String[]>();
                data = Graduation_Info_List.Graduation_Info_compare((String)parent.getItemAtPosition(position));
                mData = getCsList(data);
                CareerSuccessAdapter cs_adapter = new CareerSuccessAdapter(mData);
                recyclerView.setAdapter(cs_adapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                switch (position) {
                    case 0:
                        break;
                    default:    break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        careerSuccessViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    public ArrayList<RecyclerItem> getCsList(ArrayList<String[]> data){
        ArrayList<RecyclerItem> rcItems = new ArrayList<RecyclerItem>();

        for (int i=0; i<data.size(); i++){
            RecyclerItem item = new RecyclerItem();
            String[] str = data.get(i);
            item.setSc_item(str[0]);
            if (str[2].equals(""))
                item.setSc_pct("-");
            else
                item.setSc_pct(str[2] + " / " +str[1]);
            item.setSc_percent(str[3]);
            StringTokenizer st = new StringTokenizer(str[3], "%");
            item.setPrg(Integer.parseInt(st.nextToken()));
            ProgressBar pg = item.getSuccess_prg();
            item.setSuccess_prg(pg);
            rcItems.add(item);
        }
        return rcItems;
    }

    public static CareerSuccessFragment newInstance() {
        CareerSuccessFragment fragment = new CareerSuccessFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}