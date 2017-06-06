package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.bean.ProductListBean;
import cn.com.stableloan.ui.adapter.Recycler_Classify_Adapter;

public class ProductClassifyActivity extends BaseActivity {

    @Bind(R.id.classify_recycl)
    RecyclerView classifyRecycl;
    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_save)
    TextView tvSave;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private Recycler_Classify_Adapter classify_recycler_adapter;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ProductClassifyActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_classify);
        ButterKnife.bind(this);
      /*  ImmersionBar.with(this)
                .navigationBarColor(R.color.mask)
                .barAlpha(0.2f)
                .init();*/
        titleName.setText("2000元以下");
        ivBack.setVisibility(View.VISIBLE);
        ImageView imageView = new ImageView(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(lp);
        initView(imageView);


    }

    private void initView(ImageView view) {
        List<ProductListBean.ProductBean> list2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProductListBean.ProductBean bean = new ProductListBean.ProductBean();
            bean.setProduct_introduction("人皆寻梦 梦里不分西东");
            bean.setMin_algorithm("0.5%");
            list2.add(bean);
        }
        classify_recycler_adapter = new Recycler_Classify_Adapter(list2);
        classifyRecycl.setLayoutManager(new LinearLayoutManager(this));
        view.setImageResource(R.mipmap.classfit_header);
        classify_recycler_adapter.addHeaderView(view, 0);
        classifyRecycl.setAdapter(classify_recycler_adapter);

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
