package com.garenalnews.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.garenalnews.R;
import com.garenalnews.activity.MainActivity;
import com.garenalnews.adapter.NewsAdapter;
import com.garenalnews.common.CRecyclerView;
import com.garenalnews.model.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by ducth on 4/15/2018.
 */

@SuppressLint("ValidFragment")
public class FramgentNewsDetail extends Fragment {
    private CRecyclerView lvNews;
    private NewsAdapter newsAdapter;
    private String path;
    private loadDataAsyncTask loadDataAsyncTask;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private MainActivity mainActivity;

    public FramgentNewsDetail(String path) {
        this.path = path;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_news_details, container, false);
        mainActivity = (MainActivity) getActivity();
        initView(view);
        initData();
        initControl();
        return view;
    }

    private void initView(View view) {
        lvNews = view.findViewById(R.id.lvNews);
        swipeRefreshLayout = view.findViewById(R.id.SwipeRefreshLayout);
        progressBar = view.findViewById(R.id.progressBar);
    }

    private void initData() {
        newsAdapter = new NewsAdapter(getActivity(), new ArrayList<News>(),mainActivity);
        lvNews.setAdapter(newsAdapter);
        loadData();
    }

    public void loadData() {
        if (loadDataAsyncTask != null) {
            loadDataAsyncTask.cancel(true);
        }
        loadDataAsyncTask = new loadDataAsyncTask(path);
        loadDataAsyncTask.execute();
    }

    private void initControl() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                newsAdapter.clear();
                loadData();
            }
        });
    }

    private class loadDataAsyncTask extends AsyncTask<Void, Void, ArrayList<News>> {
        private String path;

        public loadDataAsyncTask(String path) {
            this.path = path;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            lvNews.setVisibility(View.INVISIBLE);
        }

        @Override
        protected ArrayList<News> doInBackground(Void... voids) {
            ArrayList<News> list = new ArrayList<>();
            try {
                Document document = Jsoup.connect(path).get();
                Elements element = document.getElementsByTag("item");
                for (int i = 0; i < element.size(); i++) {
                    Element elements = element.get(i);
                    String title = element.get(i).getElementsByTag("title").first().text();
                    String putDate = elements.getElementsByTag("pubDate").first().text();
                    String links = elements.getElementsByTag("link").first().text();
                    String image = elements.getElementsByTag("image").first().text();
                    Element element1 = elements.getElementsByTag("description").first();
                    News news = new News();
                    news.setTitle(title);
                    news.setContent(getContent(element1.text()));
                    news.setLink(links);
                    news.setImage(image);
                    news.setDate(putDate);
                    list.add(news);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<News> news) {
            super.onPostExecute(news);
            progressBar.setVisibility(View.INVISIBLE);
            lvNews.setVisibility(View.VISIBLE);
            newsAdapter.addAll(news);
        }
    }


    public String getContent(String description) {
        return String.valueOf(Html.fromHtml(description));
    }

    public String getImage(String description) {
        int indexStart = description.indexOf("src=");
        int endStart = description.indexOf(".jpg", indexStart);
        if (indexStart != -1 && endStart != -1) {
            return description.substring(indexStart + 5, endStart + 4);
        } else {
            return "";
        }
    }
}
