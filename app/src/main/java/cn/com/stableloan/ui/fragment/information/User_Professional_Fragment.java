package cn.com.stableloan.ui.fragment.information;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zhuge.analysis.stat.ZhugeSDK;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.model.InformationEvent;
import cn.com.stableloan.model.WorkInformation;
import cn.com.stableloan.model.event.ProfessionalSelectEvent;
import cn.com.stableloan.ui.activity.integarl.DateChangeActivity;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.SmoothCheckBox;
import cn.com.stableloan.view.SmoothCompoundButton;
import cn.com.stableloan.view.pickerview.PickerViewUtils;
import cn.com.stableloan.view.supertextview.SuperTextView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class User_Professional_Fragment extends Fragment {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.bt_SelectProfession)
    Button btSelectProfession;
    @Bind(R.id.et_School)
    SuperTextView etSchool;
    @Bind(R.id.et_SchoolAddress)
    SuperTextView etSchoolAddress;
    @Bind(R.id.et_Teacher)
    SuperTextView etTeacher;
    @Bind(R.id.layout_Student)
    LinearLayout layoutStudent;
    @Bind(R.id.et_company)
    SuperTextView etCompany;
    @Bind(R.id.et_location)
    SuperTextView etLocation;
    @Bind(R.id.et_fixedline)
    SuperTextView etFixedline;
    @Bind(R.id.et_years)
    SuperTextView etYears;
    @Bind(R.id.et_email)
    SuperTextView etEmail;
    @Bind(R.id.et_Cincome)
    SuperTextView etCincome;
    @Bind(R.id.layout_Company)
    LinearLayout layoutCompany;
    @Bind(R.id.Business_company)
    SuperTextView BusinessCompany;
    @Bind(R.id.Business_location)
    SuperTextView BusinessLocation;
    @Bind(R.id.etBusiness_fixedline)
    SuperTextView etBusinessFixedline;
    @Bind(R.id.Business_years)
    SuperTextView BusinessYears;
    @Bind(R.id.Business_email)
    SuperTextView BusinessEmail;
    @Bind(R.id.Business_Cincome)
    SuperTextView BusinessCincome;
    @Bind(R.id.et_operations)
    SuperTextView etOperations;
    @Bind(R.id.checkbox1)
    SmoothCheckBox checkbox1;
    @Bind(R.id.checkbox2)
    SmoothCheckBox checkbox2;
    @Bind(R.id.layout4)
    LinearLayout layout4;
    @Bind(R.id.layout_Business)
    LinearLayout layoutBusiness;
    @Bind(R.id.checkbox3)
    SmoothCheckBox checkbox3;
    @Bind(R.id.checkbox4)
    SmoothCheckBox checkbox4;
    @Bind(R.id.layout5)
    LinearLayout layout5;
    @Bind(R.id.layout_Freelancer)
    LinearLayout layoutFreelancer;
    private String[] list2;
    private static final int STUDENT = 2;
    private static final int COMPANY = 1;
    private static final int BUSINESS = 3;
    private static final int FREE = 4;
    private static int id;
    private String token;
    private WorkInformation.DataBean work;
    private static final int REQUEST_CODE = 30000;


    public User_Professional_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user__professional_, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        getDate();
        initBetter();
        token = (String) SPUtils.get(getActivity(), "token", "1");

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
        ZhugeSDK.getInstance().track(getActivity(), "身份信息第三页", eventObject);

        String token = (String) SPUtils.get(getActivity(), "token", "1");
        String signature = (String) SPUtils.get(getActivity(), "signature", "1");
        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        parms.put("signature", signature);
        parms.put("source", "");
        JSONObject object = new JSONObject(parms);
        OkGo.<String>post(Urls.Ip_url + Urls.Identity.GetOccupation)
                .tag(this)
                .upJson(object)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson = new Gson();
                            WorkInformation information = gson.fromJson(s, WorkInformation.class);
                            work = information.getData();
                            if (information.getError_code() == 0) {
                                if (information.getData().getStatus().equals("1")) {
                                    String identity = work.getIdentity();
                                    int anInt = Integer.parseInt(identity);
                                    switch (anInt) {
                                        case STUDENT:
                                            title.setText("其他");
                                            setVisibilityProfession(layoutStudent);
                                            id = STUDENT;
                                            break;
                                        case COMPANY:
                                            title.setText("上班族");
                                            setVisibilityProfession(layoutCompany);
                                            id = COMPANY;
                                            break;
                                        case BUSINESS:
                                            title.setText("企业主");
                                            setVisibilityProfession(layoutBusiness);
                                            id = BUSINESS;
                                            break;
                                        case FREE:
                                            title.setText("逍遥客");
                                            setVisibilityProfession(layoutFreelancer);
                                            id = FREE;
                                            break;
                                    }
                                    WorkInformation.DataBean.OccupationBean occupation = work.getOccupation();
                                    WorkInformation.DataBean.OccupationBean.StudentBean student = occupation.getStudent();
                                    etSchool.setRightString(student.getSchool());
                                    etSchoolAddress.setRightString(student.getAddress());

                                    if(!student.getTeacherphone().isEmpty()){
                                        etTeacher.setRightString(student.getPreTeacherphone() + "-" + student.getTeacherphone());
                                    }

                                    // et_PreTeacherphone.setText(student.getPreTeacherphone());
                                    WorkInformation.DataBean.OccupationBean.CompanyBean company = occupation.getCompany();
                                    etCompany.setRightString(company.getCompany());
                                    etLocation.setRightString(company.getLocation());
                                    etEmail.setRightString(company.getEmail());
                                    String years = company.getYears();
                                    if (years.length()==1) {
                                        int year = Integer.parseInt(company.getYears());
                                        etYears.setRightString(list2[year]);
                                    }
                                    String cincome = company.getCincome();
                                    etCincome.setRightString(cincome);
                                    if(!company.getFixedline().isEmpty()){
                                        etFixedline.setRightString(company.getPreFixedline() + "-" + company.getFixedline());
                                    }

                                    WorkInformation.DataBean.OccupationBean.BusinessBean business = occupation.getBusiness();
                                    BusinessCincome.setRightString(business.getBcincome());
                                    BusinessCompany.setRightString(business.getBcompany());
                                    BusinessEmail.setRightString(business.getBemail());

                                    if(!business.getBfixedline().isEmpty()){
                                        etBusinessFixedline.setRightString(business.getBpreFixedline() + "-" +business.getBfixedline());
                                    }

                                    BusinessLocation.setRightString( business.getBlocation());
                                    if (business.getByears().length() ==1) {
                                        int year1 = Integer.parseInt(business.getByears());
                                        BusinessYears.setRightString(list2[year1]);
                                    }

                                    String operations = business.getOperations();
                                    if (!operations.isEmpty()) {
                                        int tions = Integer.parseInt(business.getOperations());
                                        etOperations.setRightString(list2[tions]);
                                    }
                                    String license = business.getLicense();
                                    if ("0".equals(license)) {
                                        checkbox2.setChecked(false);
                                        checkbox1.setChecked(true);
                                    } else {
                                        checkbox1.setChecked(false);
                                        checkbox2.setChecked(true);
                                    }
                                    WorkInformation.DataBean.OccupationBean.FreelancerBean freelancer = occupation.getFreelancer();
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
                        }

                    }
                });

    }

    private void initBetter() {

        list2 = getResources().getStringArray(R.array.years);
        List<String> list = Arrays.asList(list2);


        etOperations.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                PickerViewUtils.setPickerView(etOperations, list, getActivity(), "经营年限");
            }
        });
        etYears.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                PickerViewUtils.setPickerView(etYears, list, getActivity(), "工作年限");
            }
        });
        BusinessYears.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                PickerViewUtils.setPickerView(BusinessYears, list, getActivity(), "工作年限");
            }
        });

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

        setSuperText(etSchool, Urls.DateChange.STUHENT_NAME);
        setSuperText(etSchoolAddress, Urls.DateChange.STUHENT_ADRESS);
        setSuperText(etTeacher, Urls.DateChange.STUHENT_TELEPHONE);

        setSuperText(etCompany, Urls.DateChange.COMPANY_NAME);
        setSuperText(etLocation, Urls.DateChange.COMPANY_ADRESS);
        setSuperText(etFixedline, Urls.DateChange.COMPANY_TELEPHONE);
        setSuperText(etEmail, Urls.DateChange.COMPANY_EMAIL);
        setSuperText(etCincome, Urls.DateChange.COMPANY_SALART);

        setSuperText(BusinessCompany, Urls.DateChange.BUSSINESS_NAME);
        setSuperText(BusinessLocation, Urls.DateChange.BUSSINESS_ADRESS);
        setSuperText(BusinessEmail, Urls.DateChange.BUSSINESS_EMAIL);
        setSuperText(etBusinessFixedline, Urls.DateChange.BUSSINESS_TELEPHONE);
        setSuperText(BusinessCincome, Urls.DateChange.BUSSINESS_SALART);


    }

    private void setSuperText(SuperTextView superText, int type) {
        superText.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                Intent intent = new Intent(getActivity(), DateChangeActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("hint",superText.getRightString());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Subscribe
    public void updateEvent(ProfessionalSelectEvent msg) {
        int message = msg.message;
        switch (message) {
            /*case STUDENT:
                title.setText("其他");
                setVisibilityProfession(layoutStudent);
                id = STUDENT;
                break;*/
            case COMPANY:
                title.setText("上班族");
                setVisibilityProfession(layoutCompany);
                id = COMPANY;
                break;
            case BUSINESS:
                title.setText("企业主");
                setVisibilityProfession(layoutBusiness);
                id = BUSINESS;
                break;
            case FREE:
                title.setText("自由职业");
                setVisibilityProfession(layoutFreelancer);
                id = FREE;
                break;
        }
    }

    private void saveInformation() {
        String token = (String) SPUtils.get(getActivity(), "token", "1");
        final WorkInformation.DataBean workBean = new WorkInformation.DataBean();
        workBean.setToken(token);
        WorkInformation.DataBean.OccupationBean occupationBean = new WorkInformation.DataBean.OccupationBean();
        WorkInformation.DataBean.OccupationBean.StudentBean studentBean = new WorkInformation.DataBean.OccupationBean.StudentBean();

        WorkInformation.DataBean.OccupationBean.CompanyBean companyBean = new WorkInformation.DataBean.OccupationBean.CompanyBean();
        WorkInformation.DataBean.OccupationBean.BusinessBean businessBean = new WorkInformation.DataBean.OccupationBean.BusinessBean();

        WorkInformation.DataBean.OccupationBean.FreelancerBean freelancerBean = new WorkInformation.DataBean.OccupationBean.FreelancerBean();
        String school = etSchool.getRightString();
        String teather = etTeacher.getRightString();
        String address = etSchoolAddress.getRightString();
        studentBean.setSchool(school);
        studentBean.setTeacherphone(teather);
        studentBean.setAddress(address);

        String cComPanyName = etCompany.getRightString();
        String cCompanyLocation = etLocation.getRightString();
        String cCompanyEmail = etEmail.getRightString();
        String cCincome = etCincome.getRightString();
        String cCompanyFixedline = etFixedline.getRightString();

        companyBean.setCompany(cComPanyName);
        companyBean.setLocation(cCompanyLocation);
        companyBean.setEmail(cCompanyEmail);
        String string = etYears.getRightString();
        companyBean.setYears("");
        for (int i = 0; i < list2.length; i++) {
            if (list2[i].equals(string)) {
                String s = String.valueOf(i);
                companyBean.setYears(s);
            }
        }
        companyBean.setCincome(cCincome);
        companyBean.setFixedline(cCompanyFixedline);

        String BusinessName = BusinessCompany.getRightString();
        String BusinessLocation = this.BusinessLocation.getRightString();
        String BusinessFixedLine = etBusinessFixedline.getRightString();
        String BusinessEmail = this.BusinessEmail.getRightString();
        String BusinessCincome = this.BusinessCincome.getRightString();

        String BusinessYears = this.BusinessYears.getRightString();

        businessBean.setBcompany(BusinessName);
        businessBean.setBlocation(BusinessLocation);
        businessBean.setBfixedline(BusinessFixedLine);

        businessBean.setByears("");
        for (int i = 0; i < list2.length; i++) {
            if (list2[i].equals(BusinessYears)) {
                String s = String.valueOf(i);
                businessBean.setByears(s);
            }
        }
        businessBean.setBemail(BusinessEmail);
        businessBean.setBcincome(BusinessCincome);


        occupationBean.setStudent(studentBean);
        occupationBean.setBusiness(businessBean);
        occupationBean.setCompany(companyBean);
        occupationBean.setFreelancer(freelancerBean);

        String toString = etOperations.getRightString();

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
        freelancerBean.setSource("");
        if (checkbox3.isChecked()) {
            freelancerBean.setSource("0");
        }
        if (checkbox4.isChecked()) {
            freelancerBean.setSource("1");
        }

        switch (id) {
            case STUDENT:

                if (!school.isEmpty() && !teather.isEmpty() && !address.isEmpty()) {
                    occupationBean.setStatus("1");
                } else {
                    occupationBean.setStatus("0");
                }
                workBean.setIdentity(String.valueOf(STUDENT));

                break;
            case COMPANY:

                if (!cComPanyName.isEmpty() && !cCompanyLocation.isEmpty() && !cCompanyEmail.isEmpty()
                        && !cCincome.isEmpty() && !cCompanyFixedline.isEmpty()
                        && !string.isEmpty()) {
                    occupationBean.setStatus("1");
                } else {
                    occupationBean.setStatus("0");
                }
                workBean.setIdentity(String.valueOf(COMPANY));
                break;
            case BUSINESS:

                if (!BusinessName.isEmpty() && !BusinessCincome.isEmpty() && !BusinessEmail.isEmpty()
                        && !BusinessFixedLine.isEmpty() && !BusinessLocation.isEmpty()
                        && !BusinessYears.isEmpty() && !businessBean.getLicense().isEmpty() && !businessBean.getOperations().isEmpty()) {
                    occupationBean.setStatus("1");
                } else {
                    occupationBean.setStatus("0");
                }
                workBean.setIdentity(String.valueOf(BUSINESS));

                break;
            case FREE:
                String freeCincome = etCincome.getRightString();
                if (!freeCincome.isEmpty()) {
                    occupationBean.setStatus("1");
                } else {
                    occupationBean.setStatus("0");
                }
                workBean.setIdentity(String.valueOf(FREE));
                break;
        }


        workBean.setOccupation(occupationBean);
        Gson gson = new Gson();
        String json = gson.toJson(workBean);
        OkGo.<String>post(Urls.Ip_url + Urls.Identity.AddOccupation)
                .tag(getActivity())
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            int isSuccess = object.getInt("error_code");
                            if (isSuccess == 0) {
                                work = workBean;
                                EventBus.getDefault().post(new InformationEvent("informationStatus"));
                                String data = object.getString("data");
                                JSONObject object1 = new JSONObject(data);
                                String msg = object1.getString("msg");
                                ToastUtils.showToast(getActivity(), msg);
                            } else {
                                String msg = object.getString("error_message");
                                ToastUtils.showToast(getActivity(), msg);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    private void setVisibilityProfession(View view) {
        layoutCompany.setVisibility(View.GONE);
        layoutBusiness.setVisibility(View.GONE);
        layoutFreelancer.setVisibility(View.GONE);
        layoutStudent.setVisibility(View.GONE);
        int visibility = view.getVisibility();
        if (visibility == View.GONE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);

    }

    @OnClick({R.id.bt_SelectProfession, R.id.save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_SelectProfession:
                EventBus.getDefault().post(new ProfessionalSelectEvent(0));
                break;
            case R.id.save:
                if(work!=null){
                    saveInformation();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            switch (resultCode){
                case Urls.DateChange.STUHENT_NAME:
                    if(data!=null){
                        String type = data.getStringExtra("type");
                        etSchool.setRightString(type);
                    }
                    break;
                case Urls.DateChange.STUHENT_ADRESS:
                    if(data!=null){
                        String type = data.getStringExtra("type");
                        etSchoolAddress.setRightString(type);
                    }
                    break;
                case Urls.DateChange.STUHENT_TELEPHONE:
                    if(data!=null){
                        Bundle type = data.getBundleExtra("type");
                        etTeacher.setRightString(type.getString("are")+"-"+type.getString("telephone"));
                    }
                    break;
                case Urls.DateChange.COMPANY_TELEPHONE:
                    if(data!=null){
                        Bundle type = data.getBundleExtra("type");
                        etFixedline.setRightString(type.getString("are")+"-"+type.getString("telephone"));
                    }
                    break;
                case Urls.DateChange.BUSSINESS_TELEPHONE:
                    if(data!=null){
                        Bundle type = data.getBundleExtra("type");
                        etBusinessFixedline.setRightString(type.getString("are")+"-"+type.getString("telephone"));
                    }
                    break;
                case Urls.DateChange.COMPANY_NAME:
                    if(data!=null){
                        String type = data.getStringExtra("type");
                        etCompany.setRightString(type);
                    }
                    break;
                case Urls.DateChange.BUSSINESS_NAME:
                    if(data!=null){
                        String type = data.getStringExtra("type");
                        BusinessCompany.setRightString(type);
                    }
                    break;
                case Urls.DateChange.COMPANY_ADRESS:
                    if(data!=null){
                        String type = data.getStringExtra("type");
                        etLocation.setRightString(type);
                    }
                    break;
                case Urls.DateChange.BUSSINESS_ADRESS:
                    if(data!=null){
                        String type = data.getStringExtra("type");
                        BusinessLocation.setRightString(type);
                    }
                    break;
                case Urls.DateChange.COMPANY_EMAIL:
                    if(data!=null){
                        String type = data.getStringExtra("type");
                        etEmail.setRightString(type);
                    }
                    break;
                case Urls.DateChange.BUSSINESS_EMAIL:
                    if(data!=null){
                        String type = data.getStringExtra("type");
                        BusinessEmail.setRightString(type);
                    }
                    break;
                case Urls.DateChange.COMPANY_SALART:
                    if(data!=null){
                        String type = data.getStringExtra("type");
                        etCincome.setRightString(type);
                    }
                    break;
                case Urls.DateChange.BUSSINESS_SALART:
                    if(data!=null){
                        String type = data.getStringExtra("type");
                        BusinessCincome.setRightString(type);
                    }
                    break;
            }
        }

    }
}