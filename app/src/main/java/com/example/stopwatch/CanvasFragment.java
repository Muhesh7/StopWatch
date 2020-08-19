package com.example.stopwatch;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CanvasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CanvasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private static int mParam1;
    private static int mParam2;

    public CanvasFragment() {
        // Required empty public constructor
    }

    public static CanvasFragment newInstance(int param1, int param2) {
        CanvasFragment fragment = new CanvasFragment();
        mParam1 = param1;
        mParam2 = param2;
        return fragment;
    }


    SharedViewModel mViewModel;
    canvas mCanvas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_canvas, container, false);
        RelativeLayout relativeLayout = view.findViewById(R.id.plane);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mParam1, mParam2);
        relativeLayout.setLayoutParams(params);
        mCanvas = new canvas(getContext());
        relativeLayout.addView(mCanvas);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel= ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        observe();
    }
    public void observe()
    {
        mViewModel.getData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s) {
                    case "start":
                        Log.d("Ss1", "ss1");
                        mCanvas.spin(0);

                        break;
                    case "stop":
                        mCanvas.stop();
                        break;
                    case "reset":
                        mCanvas.reset();
                        break;
                }
            }
        });
    }
}
