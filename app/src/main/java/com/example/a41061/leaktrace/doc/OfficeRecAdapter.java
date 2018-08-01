package com.example.a41061.leaktrace.doc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a41061.leaktrace.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * @author FanHongyu.
 * @since 18/3/28 15:52.
 * email fanhongyu@hrsoft.net.
 */

public class OfficeRecAdapter extends RecyclerView.Adapter<OfficeRecAdapter.ItemHolder> {


    private List<OfficeModel> mOfficeModelList;
    private Context mContext;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public OfficeRecAdapter(Context context, List<OfficeModel> officeModelList) {
        mOfficeModelList = officeModelList;
        mContext = context;
        mInflater = LayoutInflater.from(context);

    }



    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_office, parent, false);
        return new ItemHolder(view);
    }




    @Override
    public void onBindViewHolder(ItemHolder holder, final int position) {

        holder.mTxtOfficeName.setText(mOfficeModelList.get(position).getName());

        if(mOnItemClickListener!=null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mOnItemClickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mOfficeModelList.size();
    }





    class ItemHolder extends RecyclerView.ViewHolder {

        TextView mTxtOfficeName;

        private ItemHolder(View itemView) {
            super(itemView);
            mTxtOfficeName = (TextView) itemView.findViewById(R.id.txt_file_name);
        }
    }




    public interface OnItemClickListener {

        void onItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }


}
