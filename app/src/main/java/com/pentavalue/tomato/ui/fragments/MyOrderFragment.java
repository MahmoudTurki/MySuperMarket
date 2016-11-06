package com.pentavalue.tomato.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.adapter.MyOrderAdapter;
import com.pentavalue.tomato.data.parsing.BuildingStaticData;
import com.pentavalue.tomato.model.Notification;
import com.pentavalue.tomato.model.Orders;
import com.pentavalue.tomato.ui.custom.DividerItemDecoration;
import com.pentavalue.tomato.utils.ValidatorUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyOrderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author Mahmoud Turki
 */
public class MyOrderFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.noOrderLyt)
    LinearLayout noOrderLyt;
    @Bind(R.id.listLyt)
    LinearLayout listLyt;

    public MyOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyOrderFragment newInstance() {
        MyOrderFragment fragment = new MyOrderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_order, container, false);
        init(rootView);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void init(View view) {
        ButterKnife.bind(this, view);
        setupRecyclerView(recyclerView, BuildingStaticData.getOrdersList());
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Orders> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(R.layout.list_order_spacer));
        setListItems(recyclerView, list);
    }

    private void setListItems(RecyclerView recyclerView, List<Orders> list) {
        if (!ValidatorUtils.isEmpty(list)) {
            MyOrderAdapter adapter = new MyOrderAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private void handleEmptyList(List<Notification> list) {
        if (ValidatorUtils.isEmpty(list)) {
            noOrderLyt.setVisibility(View.VISIBLE);
            listLyt.setVisibility(View.GONE);
        } else {
            noOrderLyt.setVisibility(View.GONE);
            listLyt.setVisibility(View.VISIBLE);
        }
    }
}
