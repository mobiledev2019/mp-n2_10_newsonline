package com.garenalnews.adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.garenalnews.R;
import com.garenalnews.activity.DetailsActivity;
import com.garenalnews.activity.MainActivity;
import com.garenalnews.common.Config;
import com.garenalnews.common.Untils;
import com.garenalnews.model.News;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by ducth on 4/14/2018.
 */

public class NewsAdapter extends BaseRecyclerAdapter<News, NewsAdapter.ViewHolder> {
    private MainActivity mainActivity;

    public NewsAdapter(Context context, List<News> list, MainActivity mainActivity) {
        super(context, list);
        this.mainActivity = mainActivity;
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;
        private ImageView imNews;
        private TextView tvTitle;
        private TextView tvPutDate;
        private TextView tvContent;
        private View lo_main;
        private ImageView imMore;

        public ViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
            imNews = itemView.findViewById(R.id.imNews);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvPutDate = itemView.findViewById(R.id.tvPutDate);
            lo_main = itemView.findViewById(R.id.lo_main);
            imMore = itemView.findViewById(R.id.imMore);
        }

        public void binData(final News news, int position) {
            tvContent.setText(news.getContent().toString());
            tvTitle.setText(news.getTitle().toString());
            tvPutDate.setText(news.getDate().toString());
            String pathIamge = news.getImage();
            if (pathIamge == null || "".equals(pathIamge)) {
                progressBar.setVisibility(View.INVISIBLE);
                imNews.setVisibility(View.VISIBLE);
                Picasso.with(mContext).load(R.mipmap.icon_no_image).resize(Untils.dpToPx(80, mContext.getResources()),
                        Untils.dpToPx(80, mContext.getResources())).into(imNews);
            } else {
                Picasso.with(mContext).load(pathIamge).centerCrop().resize(Untils.dpToPx(80, mContext.getResources()),
                        Untils.dpToPx(80, mContext.getResources())).error(R.mipmap.icon_no_image).into(imNews, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.INVISIBLE);
                        imNews.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.INVISIBLE);
                        imNews.setVisibility(View.VISIBLE);
                        Picasso.with(mContext).load(R.mipmap.icon_no_image).into(imNews);
                    }
                });
            }
            lo_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DetailsActivity.class);
                    intent.putExtra(Config.SEND_NEWS, news);
                    mContext.startActivity(intent);
                }
            });


        }
    }
}
