package cn.com.stableloan.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.bean.ProductListBean;

/**
 * Created by apple on 2017/5/22.
 */

public class ListProductAdapter  extends BaseQuickAdapter<ProductListBean.ProductBean,BaseViewHolder>{

    public ListProductAdapter(int layoutResId, List<ProductListBean.ProductBean> data) {
        super(R.layout.product_trem, data);
    }

        @Override
    protected void convert(BaseViewHolder helper, ProductListBean.ProductBean item) {

    }
}
