package com.example.youp.Kuiper_Youp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.youp.Kuiper_Youp.Models.Authtoken;
import com.example.youp.Kuiper_Youp.Models.Result;
import com.example.youp.Kuiper_Youp.R;

import java.util.List;

/**
 * Created by youp on 9/29/2017.
 */

public class NewsItemsAdapter extends RecyclerView.Adapter<NewsItemsAdapter.NewsItemsViewHolder>{

    public void setmItems(List<Result> newItems) {
        this.mItems.addAll(newItems);
    }

    public void clearList(){
        this.mItems.clear();
    }

    private List<Result> mItems;
    private final Context mContext;
    private final NewsItemListener mListener;


    public interface NewsItemListener {
        void onItemClick(View view, Result content);
    }


    public NewsItemsAdapter(Context context, List<Result> items, NewsItemListener listener) {
        mContext = context;
        mItems = items;
        mListener = listener;
    }

    @Override
    public NewsItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsItemsViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final NewsItemsViewHolder holder, int position) {
        //get the item for the current position
        final Result node = getItem(position);

        //fill the views in the viewholder with the content for the current position
        holder.title.setText(node.getTitle());
        holder.description.setText(node.getSummary());
        Glide.with(mContext).load(node.getImage()).centerCrop().dontAnimate().into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(holder.itemView, node);
            }
        });


        if(Authtoken.getInstance().getAuthToken() == null){
            holder.liked.setVisibility(View.INVISIBLE);
        }else{
            if(node.getIsLiked() == true){
                holder.liked.setVisibility(View.VISIBLE);
            }else{
                holder.liked.setVisibility(View.INVISIBLE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * @param position of the item in the list
     * @return the item for the requested position
     */
    public Result getItem(int position) {
        return mItems.get(position);
    }


    static class NewsItemsViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;
        public TextView description;
        public ImageView liked;

        NewsItemsViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.listitem_image);
            title = itemView.findViewById(R.id.listitem_title);
            description = itemView.findViewById(R.id.listitem_description);
            liked = itemView.findViewById(R.id.liked_smile);
        }
    }
}
