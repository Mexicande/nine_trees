package cn.com.stableloan.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.andreabaccega.widget.FormEditText;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zhuge.analysis.stat.ZhugeSDK;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.model.InformationEvent;
import cn.com.stableloan.model.WorkBean;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.BetterSpinner;
import cn.com.stableloan.view.EmailAutoCompleteTextView;
import cn.com.stableloan.view.SmoothCheckBox;
import cn.com.stableloan.view.SmoothCompoundButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfessionalInformationFragment extends Fragment {

    @Bind(R.id.et_years)
    BetterSpinner etYears;
    @Bind(R.id.checkbox3)
    SmoothCheckBox checkbox3;
    @Bind(R.id.checkbox4)
    SmoothCheckBox checkbox4;
    @Bind(R.id.et_School)
    FormEditText etSchool;
    @Bind(R.id.et_SchoolAddress)
    FormEditText etSchoolAddress;
    @Bind(R.id.et_Teacher)
    FormEditText etTeacher;
    @Bind(R.id.company)
    FormEditText et_company;
    @Bind(R.id.location)
    FormEditText et_location;
    @Bind(R.id.et_email)
    EmailAutoCompleteTextView et_etEmail;
    @Bind(R.id.et_fixedline)
    FormEditText etFixedline;

    @Bind(R.id.checkbox1)
    SmoothCheckBox checkbox1;
    @Bind(R.id.checkbox2)
    SmoothCheckBox checkbox2;
    @Bind(R.id.et_Cincome)
    FormEditText etCincome;
    @Bind(R.id.et_operations)
    BetterSpinner etOperations;
    @Bind(R.id.bincome)
    BetterSpinner bincome;

    private String[] lists;
    private WorkBean work;
    private String[] list2;


    public ProfessionalInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View view = inflater.inflate(R.layout.fragment_professional_information, container, false);
        ButterKnife.bind(this, view);
        getDate();
        initBetter();
        return view;
    }
    private void getDate() {

        JSONObject eventObject = new JSONObject();
        try {
            eventObject.put("persmaterials3", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//记录事件
        ZhugeSDK.getInstance().track(getActivity(), "身份信息",  eventObject);

        String token = (String) SPUtils.get(getActivity(), "token", "1");
        String signature = (String) SPUtils.get(getActivity(), "signature", "1");
        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        parms.put("signature", signature);
        JSONObject object = new JSONObject(parms);
        OkGo.<String>post(Urls.NEW_URL + Urls.Identity.GetOccupation)
                .tag(this)
                .upJson(object)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String isSuccess = jsonObject.getString("isSuccess");
                            if ("1".equals(isSuccess)) {
                                String status = jsonObject.getString("status");
                                if ("1".equals(status)) {
                                    Gson gson = new Gson();
                                    work= gson.fromJson(s, WorkBean.class);
                                    WorkBean.OccupationBean occupation = work.getOccupation();
                                    WorkBean.OccupationBean.StudentBean student = occupation.getStudent();
                                    etSchool.setText(student.getSchool());
                                    etSchoolAddress.setText(student.getAddress());
                                    etTeacher.setText(student.getTeacherphone());
                                    WorkBean.OccupationBean.CompanyBean company = occupation.getCompany();
                                    et_company.setText(company.getCompany());
                                    et_location.setText(company.getLocation());
                                    et_etEmail.setText(company.getEmail());
                                    String years = company.getYears();

                                    if(!years.isEmpty()){
                                        int year = Integer.parseInt(company.getYears());
                                        etYears.setText(list2[year]);
                                    }
                                    String cincome = company.getCincome();
                                    etCincome.setText(cincome);
                                    etFixedline.setText(company.getFixedline());
                                    WorkBean.OccupationBean.BusinessBean business = occupation.getBusiness();

                                    String operations = business.getOperations();
                                    if(!operations.isEmpty()){
                                        int tions = Integer.parseInt(business.getOperations());
                                        etOperations.setText(list2[tions]);
                                    }

                                    String license = business.getLicense();
                                    if ("0".equals(license)) {
                                        checkbox2.setChecked(false);
                                        checkbox1.setChecked(true);
                                    } else {
                                        checkbox1.setChecked(false);
                                        checkbox2.setChecked(true);
                                    }
                                    String bome = business.getBincome();
                                    if(!bome.isEmpty()){
                                        int s1 = Integer.parseInt(bome);
                                        bincome.setText(lists[s1]);
                                    }

                                    WorkBean.OccupationBean.FreelancerBean freelancer = occupation.getFreelancer();
                                    String source = freelancer.getSource();
                                    if ("0".equals(source)) {
                                        checkbox4.setChecked(false);
                                        checkbox3.setChecked(true);
                                    } else {
                                        checkbox3.setChecked(false);
                                        checkbox4.setChecked(true);
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    private void initBetter() {


        lists = getResources().getStringArray(R.array.income);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, lists);
        bincome.setAdapter(adapter);

        list2 = getResources().getStringArray(R.array.years);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, list2);
        etOperations.setAdapter(adapter1);
        etYears.setAdapter(adapter1);

        checkbox1.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                if (checkbox1.isChecked()) {
                    checkbox2.setChecked(false);
                } else {
                    checkbox2.setChecked(true);
                }
            }
        });
        checkbox2.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                if (checkbox2.isChecked()) {
                    checkbox1.setChecked(false);
                } else {
                    checkbox1.setChecked(true);
                }

            }
        });
        checkbox3.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                if (checkbox3.isChecked()) {
                    checkbox4.setChecked(false);
                } else {
                    checkbox4.setChecked(true);
                }


            }
        });
        checkbox4.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                if (checkbox4.isChecked()) {
                    checkbox3.setChecked(false);
                } else {
                    checkbox3.setChecked(true);
                }


            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick(R.id.save)
    public void onViewClicked() {


        String token = (String) SPUtils.get(getActivity(), "token", "1");

        final WorkBean workBean = new WorkBean();
        workBean.setToken(token);
        WorkBean.OccupationBean occupationBean = new WorkBean.OccupationBean();

        WorkBean.OccupationBean.StudentBean studentBean = new WorkBean.OccupationBean.StudentBean();
        studentBean.setSchool(etSchool.getText().toString());
        studentBean.setTeacherphone(etTeacher.getText().toString());
        studentBean.setAddress(etSchoolAddress.getText().toString());

        WorkBean.OccupationBean.CompanyBean companyBean = new WorkBean.OccupationBean.CompanyBean();

        companyBean.setCompany(et_company.getText().toString());
        companyBean.setLocation(et_location.getText().toString());
        companyBean.setEmail(et_etEmail.getText().toString());
        String string = etYears.getText().toString();
        companyBean.setYears("");
        for (int i = 0; i < list2.length; i++) {
            if (list2[i].equals(string)) {
                String s = String.valueOf(i);
                companyBean.setYears(s);
            }
        }
        companyBean.setCincome(etCincome.getText().toString());
        companyBean.setFixedline(etFixedline.getText().toString());




        WorkBean.OccupationBean.BusinessBean businessBean = new WorkBean.OccupationBean.BusinessBean();

        String toString = etOperations.getText().toString();

        businessBean.setOperations("");
        for (int i = 0; i < list2.length; i++) {
            if (list2[i].equals(toString)) {
                String s = String.valueOf(i);
                businessBean.setOperations(s);
            }
        }
        businessBean.setLicense("");

        if (checkbox1.isChecked()) {
            businessBean.setLicense("0");
        }
        if (checkbox2.isChecked()) {
            businessBean.setLicense("1");
        }
        String string1 = bincome.getText().toString();
        businessBean.setBincome("");
        for (int i = 0; i < lists.length; i++) {
            if (lists[i].equals(string1)) {
                String s = String.valueOf(i);
                businessBean.setBincome(s);
            }
        }

        WorkBean.OccupationBean.FreelancerBean freelancerBean = new WorkBean.OccupationBean.FreelancerBean();
        freelancerBean.setSource("");
        if (checkbox3.isChecked()) {
            freelancerBean.setSource("0");
        }
        if (checkbox4.isChecked()) {
            freelancerBean.setSource("1");
        }

        if(work.getOccupation().getStudent().equals(studentBean)&&work.getOccupation().getBusiness().equals(businessBean)
                &&work.getOccupation().getCompany().equals(companyBean)&&work.getOccupation().getFreelancer().equals(freelancerBean)){
            ToastUtils.showToast(getActivity(),"无修改内容");
        }else {

            FormEditText[] allFields = {etTeacher,  etFixedline};
            boolean allValid = true;
            for (FormEditText field : allFields) {
                allValid = field.testValidity() && allValid;
            }
            if (allValid) {

                occupationBean.setStudent(studentBean);
                occupationBean.setBusiness(businessBean);
                occupationBean.setCompany(companyBean);
                occupationBean.setFreelancer(freelancerBean);


                String school = etSchool.getText().toString();
                String operations = etOperations.getText().toString();
                String Cincome = etCincome.getText().toString();
                String fixedline = etFixedline.getText().toString();
                String company = et_company.getText().toString();
                String email = et_etEmail.getText().toString();
                String loction = et_location.getText().toString();
                String teacher = etTeacher.getText().toString();
                String SchoolAddress = etSchoolAddress.getText().toString();
                String yers = etYears.getText().toString();
                String ome = bincome.getText().toString();


                if(school.isEmpty()||operations.isEmpty()||Cincome.isEmpty()||fixedline.isEmpty()||company.isEmpty()||email.isEmpty()
                        ||loction.isEmpty()||teacher.isEmpty()||SchoolAddress.isEmpty()||yers.isEmpty()||ome.isEmpty()){
                    if(!checkbox1.isChecked()&&!checkbox2.isChecked()){
                        if(!checkbox3.isChecked()&&!checkbox4.isChecked()){
                            occupationBean.setStatus("0");
                        }
                    }
                }else {
                    occupationBean.setStatus("1");
                }
                workBean.setOccupation(occupationBean);
                Gson gson = new Gson();
                String json = gson.toJson(workBean);
                OkGo.<String>post(Urls.NEW_URL + Urls.Identity.AddOccupation)
                        .tag(getActivity())
                        .upJson(json)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject object = new JSONObject(s);

                                    String isSuccess = object.getString("isSuccess");
                                    String msg = object.getString("msg");
                                    work=workBean;

                                    if ("1".equals(isSuccess)) {
                                        EventBus.getDefault().post(new InformationEvent("informationStatus"));

                                        ToastUtils.showToast(getActivity(), msg);
                                    } else {
                                        ToastUtils.showToast(getActivity(), msg);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }


                            }
                        });

            }

        }


        }


}
