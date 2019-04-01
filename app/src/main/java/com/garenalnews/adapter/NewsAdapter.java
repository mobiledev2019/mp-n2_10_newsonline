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

//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.messenger.MessengerUtils;
//import com.facebook.messenger.ShareToMessengerParams;
//import com.facebook.share.Sharer;
//import com.facebook.share.model.ShareLinkContent;
//import com.facebook.share.model.ShareMessengerGenericTemplateContent;
//import com.facebook.share.model.ShareMessengerGenericTemplateElement;
//import com.facebook.share.model.ShareMessengerURLActionButton;
//import com.facebook.share.widget.MessageDialog;
//import com.facebook.share.widget.ShareDialog;
import com.garenalnews.R;
import com.garenalnews.activity.DetailsActivity;
import com.garenalnews.activity.MainActivity;
import com.garenalnews.common.Config;
import com.garenalnews.common.Untils;
import com.garenalnews.dialog.DialogShare;
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

//            imMore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    DialogShare dialogShare = DialogShare.newInstance(mContext, new DialogShare.OnResult() {
//                        @Override
//                        public void facebook() {
//                            CallbackManager callbackManager = CallbackManager.Factory.create();
//                            ShareDialog shareDialog = new ShareDialog((Activity) mContext);
//                            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
//                                @Override
//                                public void onSuccess(Sharer.Result result) {
//                                    Toast.makeText(mContext, "Đã chia sẻ lên dòng thời gian của bạn",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                                @Override
//                                public void onCancel() {
//                                    Toast.makeText(mContext, "Chia sẻ thất bại", Toast.LENGTH_SHORT).show();
//                                }
//                                @Override
//                                public void onError(FacebookException error) {
//                                    Toast.makeText(mContext, "Chia sẻ thất bại", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                            if (ShareDialog.canShow(ShareLinkContent.class)) {
//                                ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                                        .setContentUrl(Uri.parse(news.getLink()))
//                                        .build();
//                                shareDialog.show(linkContent);
//                            }
//                        }
//
//                        @Override
//                        public void gmail() {
//                            Intent intent = new Intent(Intent.ACTION_SENDTO);
//                            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//                            intent.putExtra(Intent.EXTRA_TEXT, news.getLink());
//                            intent.putExtra(Intent.EXTRA_SUBJECT, news.getContent());
//                            if (intent.resolveActivity(mContext.getPackageManager()) != null) {
//                                mContext.startActivity(intent);
//                            } else {
//                                Toast.makeText(mContext, "Xảy ra lỗi, vui lòng thử lại sau.",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void zalo() {
//
//                        }
//
//                        @Override
//                        public void messenger() {
//                            if (mainActivity.getUser().getId() == null ||
//                                    mainActivity.getUser().getId().equals("")) {
//                                Toast.makeText(mContext, "Bạn phải đăng nhập facebook để tiếp tục.",
//                                        Toast.LENGTH_LONG).show();
//                                return;
//                            }
//                            ShareMessengerURLActionButton actionButton =
//                                    new ShareMessengerURLActionButton.Builder()
//                                            .setTitle(news.getTitle())
//                                            .setUrl(Uri.parse(news.getLink()))
//                                            .build();
//                            ShareMessengerGenericTemplateElement genericTemplateElement =
//                                    new ShareMessengerGenericTemplateElement.Builder()
//                                            .setTitle(news.getTitle())
//                                            .setSubtitle(news.getContent())
//                                            .setImageUrl(Uri.parse(news.getImage()))
//                                            .setButton(actionButton)
//                                            .build();
//                            ShareMessengerGenericTemplateContent genericTemplateContent =
//                                    new ShareMessengerGenericTemplateContent.Builder()
//                                            .setPageId(mainActivity.getUser().getId()) // Your page ID, required
//                                            .setGenericTemplateElement(genericTemplateElement)
//                                            .build();
//                            try {
//                                MessageDialog.show(mainActivity, genericTemplateContent);
//                            } catch (Exception e) {
//                                Toast.makeText(mContext, "Xảy ra lỗi, vui lòng thử lại sau.",
//                                        Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    });
//                    dialogShare.show();
//                }
//            });
        }
    }
}
