package cn.com.stableloan.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.stableloan.R;
import cn.com.stableloan.view.BetterSpinner;
import cn.com.stableloan.view.SmoothCheckBox;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfessionalInformationFragment extends Fragment {


    @Bind(R.id.et_Cincome)
    BetterSpinner etCincome;
    @Bind(R.id.bincome)
    BetterSpinner bincome;
    @Bind(R.id.et_years)
    BetterSpinner etYears;
    @Bind(R.id.checkbox1)
    SmoothCheckBox checkbox1;
    @Bind(R.id.checkbox2)
    SmoothCheckBox checkbox2;
    @Bind(R.id.checkbox3)
    SmoothCheckBox checkbox3;
    @Bind(R.id.checkbox4)
    SmoothCheckBox checkbox4;

    public ProfessionalInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_professional_information, container, false);
        ButterKnife.bind(this, view);
        initBetter();
        return view;
    }

    private void initBetter() {

        String[] list = getResources().getStringArray(R.array.income);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, list);
        etCincome.setAdapter(adapter);

        bincome.setAdapter(adapter);

        String[] list2 = getResources().getStringArray(R.array.years);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, list2);
        etYears.setAdapter(adapter1);

        if(checkbox1.isChecked()){
            checkbox2.setChecked(false);
        }
        if (checkbox2.isChecked()){
            checkbox1.setChecked(false);
        }
        if(checkbox3.isChecked()){
            checkbox4.setChecked(false);
        }
        if (checkbox4.isChecked()){
            checkbox3.setChecked(false);
        }




    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
