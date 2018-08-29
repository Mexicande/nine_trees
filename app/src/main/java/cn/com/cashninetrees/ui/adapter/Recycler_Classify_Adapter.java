package cn.com.cashninetrees.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coorchice.library.SuperTextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.cashninetrees.R;
import cn.com.cashninetrees.model.Class_ListProductBean;
import cn.com.cashninetrees.model.clsaa_special.Class_Special;


/**
 * Created by apple on 2017/4/11.
 */

public class Recycler_Classify_Adapter extends BaseQuickAdapter<Class_Special.DataBean.ProductBean,BaseViewHolder> {


    public Recycler_Classify_Adapter(int layoutResId,ArrayList<Class_Special.DataBean.ProductBean> data) {
        super(layoutResId, data);

    }
    @Override
    protected void convert(BaseViewHolder helper,Class_Special.DataBean.ProductBean item) {

        SuperTextView view = (SuperTextView) helper.getView(R.id.label1_Special);
        SuperTextView view1 = (SuperTextView) helper.getView(R.id.label2_Special);
        SuperTextView view2 = (SuperTextView) helper.getView(R.id.label3_Special);
        SuperTextView view3 = (SuperTextView) helper.getView(R.id.label4_Special);
        TextView view4 = (TextView) helper.getView(R.id.shengluehao_Special);
        View news = helper.getView(R.id.special_news);
        View hots = helper.getView(R.id.special_hots);
        if(item.getActivity()==1){
            hots.setVisibility(View.VISIBLE);
        }else {
            hots.setVisibility(View.GONE);
        }
        if(item.getOnline()==1){
            news.setVisibility(View.VISIBLE);
        }else {
            news.setVisibility(View.GONE);

        }
        if(item.getLabels()!=null&&item.getLabels().size()>0){
            int size = item.getLabels().size();
            List<Class_Special.DataBean.ProductBean.LabelsBean> lables = item.getLabels();
            switch (size){
                case 0:
                    break;
                case 1:
                    view.setVisibility(View.VISIBLE);
                    view.setTextColor(Color.parseColor(lables.get(0).getFont()));
                    view.setStrokeColor(Color.parseColor(lables.get(0).getFont()));
                    view.setText(lables.get(0).getName());
                    break;
                case 2:
                    view.setVisibility(View.VISIBLE);
                    view.setTextColor(Color.parseColor(lables.get(0).getFont()));
                    view.setStrokeColor(Color.parseColor(lables.get(0).getFont()));
                    view.setText(lables.get(0).getName());

                    view1.setVisibility(View.VISIBLE);
                    view1.setTextColor(Color.parseColor(lables.get(1).getFont()));
                    view1.setStrokeColor(Color.parseColor(lables.get(1).getFont()));
                    view1.setText(lables.get(1).getName());

                    break;
                case 3:
                    view.setVisibility(View.VISIBLE);
                    view.setTextColor(Color.parseColor(lables.get(0).getFont()));
                    view.setStrokeColor(Color.parseColor(lables.get(0).getFont()));
                    view.setText(lables.get(0).getName());
                    view1.setVisibility(View.VISIBLE);
                    view1.setTextColor(Color.parseColor(lables.get(1).getFont()));
                    view1.setStrokeColor(Color.parseColor(lables.get(1).getFont()));
                    view1.setText(lables.get(1).getName());
                    view2.setVisibility(View.VISIBLE);
                    view2.setTextColor(Color.parseColor(lables.get(2).getFont()));
                    view2.setStrokeColor(Color.parseColor(lables.get(2).getFont()));
                    view2.setText(lables.get(2).getName());
                    break;
                case 4:
                    view.setVisibility(View.VISIBLE);
                    view.setTextColor(Color.parseColor(lables.get(0).getFont()));
                    view.setStrokeColor(Color.parseColor(lables.get(0).getFont()));
                    view.setText(lables.get(0).getName());

                    view1.setVisibility(View.VISIBLE);
                    view1.setTextColor(Color.parseColor(lables.get(1).getFont()));
                    view1.setStrokeColor(Color.parseColor(lables.get(1).getFont()));
                    view1.setText(lables.get(1).getName());


                    view2.setVisibility(View.VISIBLE);
                    view2.setTextColor(Color.parseColor(lables.get(2).getFont()));
                    view2.setStrokeColor(Color.parseColor(lables.get(2).getFont()));
                    view2.setText(lables.get(2).getName());

                    view3.setVisibility(View.VISIBLE);
                    view3.setTextColor(Color.parseColor(lables.get(3).getFont()));
                    view3.setStrokeColor(Color.parseColor(lables.get(3).getFont()));
                    view3.setText(lables.get(3).getName());
                    break;
                default:
                    view.setVisibility(View.VISIBLE);
                    view.setTextColor(Color.parseColor(lables.get(0).getFont()));
                    view.setStrokeColor(Color.parseColor(lables.get(0).getFont()));
                    view.setText(lables.get(0).getName());

                    view1.setVisibility(View.VISIBLE);
                    view1.setTextColor(Color.parseColor(lables.get(1).getFont()));
                    view1.setStrokeColor(Color.parseColor(lables.get(1).getFont()));
                    view1.setText(lables.get(1).getName());


                    view2.setVisibility(View.VISIBLE);
                    view2.setTextColor(Color.parseColor(lables.get(2).getFont()));
                    view2.setStrokeColor(Color.parseColor(lables.get(2).getFont()));
                    view2.setText(lables.get(2).getName());

                    view3.setVisibility(View.VISIBLE);
                    view3.setTextColor(Color.parseColor(lables.get(3).getFont()));
                    view3.setStrokeColor(Color.parseColor(lables.get(3).getFont()));
                    view3.setText(lables.get(3).getName());

                    view4.setVisibility(View.VISIBLE);
                    break;
            }
        }else {
            view.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
            view3.setVisibility(View.GONE);
            view4.setVisibility(View.GONE);
        }
        int interest_algorithm = item.getInterest_algorithm();
        String maximum_amount = item.getMaximum_amount();
        if(maximum_amount.length()>4){
            String substring = maximum_amount.substring(0, maximum_amount.length() - 4);
            helper.setText(R.id.min_max_Special,item.getMinimum_amount()+"-"+substring+"万");
        }else {
            helper.setText(R.id.min_max_Special,item.getMinimum_amount()+"-"+maximum_amount);
        }

        helper.setText(R.id.special__desc,item.getProduct_introduction())
                .setText(R.id.special_Pname,item.getPname())
                .setText(R.id.min_algorithm_Special,item.getMin_algorithm()+"%")
                .setText(R.id.average_time_Special,item.getFastest_time());
        if(interest_algorithm==0){
            helper.setText(R.id.special_rate,"参考日利率");
        }else {
            helper.setText(R.id.special_rate,"参考月利率");

        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext).load(item.getProduct_logo()).apply(options).into((ImageView) helper.getView(R.id.ic_logoSpecial));

    }
}
