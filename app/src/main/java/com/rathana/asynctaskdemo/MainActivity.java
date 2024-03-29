package com.rathana.asynctaskdemo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rathana.asynctaskdemo.data.ServiceGenerator;
import com.rathana.asynctaskdemo.data.service.ArticleService;
import com.rathana.asynctaskdemo.model.Article;
import com.rathana.asynctaskdemo.model.ArticleResponse;
import com.rathana.asynctaskdemo.model.Author;
import com.rathana.asynctaskdemo.model.Category;
import com.rathana.asynctaskdemo.model.DeleteArticleResponse;
import com.rathana.asynctaskdemo.util.DownloadAsyncTask;
import com.rathana.asynctaskdemo.util.LoadMoreLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
 implements ArticleAdapter.ArticleCallback {


    ArticleAdapter articleAdapter;
    List<Article> articles=new ArrayList<>();
    RecyclerView rvArticle;

    ArticleService articleService;

    FloatingActionButton btnAdd;

    static  final int ADD_ARTICLE_CODE=1;
    static final int EDIT_CODE_REQUEST=2;
    int page=1;
    LoadMoreLinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        articleService = ServiceGenerator.createService(ArticleService.class);
        btnAdd=findViewById(R.id.btnAdd);
        initUI();
        //getArticles(1,20);


        btnAdd.setOnClickListener(v->{
            Intent intent =new Intent(this,AddArticleActivity.class);
            startActivityForResult(intent,ADD_ARTICLE_CODE);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==ADD_ARTICLE_CODE && resultCode==RESULT_OK){
            Article article= data.getParcelableExtra("article");
            Category category=data.getParcelableExtra("category");
            Author author= data.getParcelableExtra("author");
            try {
                if(article!=null && category!=null && author!=null){
                    article.setAuthor(author);
                    article.setCategory(category);
                    articleAdapter.addItem(article);
                    rvArticle.smoothScrollToPosition(0);
                }
            }catch (Exception e){

            }
        }
    }

    void initUI(){
        rvArticle=findViewById(R.id.rvArticle);
        //LinearLayoutManager manager=new LinearLayoutManager(this);
        getArticles(1,15);
        layoutManager=new LoadMoreLinearLayoutManager(this);
        articleAdapter=new ArticleAdapter(articles,this);
        articleAdapter.setCanLoadMore(false);
        rvArticle.setAdapter(articleAdapter);
        rvArticle.setLayoutManager(layoutManager);
        articleAdapter.setCallback(this);

        //scroll load more item
        layoutManager.setLoadMOreListener(new LoadMoreLinearLayoutManager.OnLoadMOreListener() {
            @Override
            public void onLoadMore() {
                page++;
                getMoreArticles(page,15);
            }
        });

    }

    //get first page
    private void getArticles(long page,long limit){
        articleService.getArticles(page,limit).enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                List<Article> articles= response.body().getArticle();
                //articleAdapter.addMoreItems(articles);
                MainActivity.this.articles.addAll(articles);
                articleAdapter.setCanLoadMore(true);
                articleAdapter.notifyDataSetChanged();
//                if(articles.size()>=limit){
//
//                }else{
//                    articleAdapter.setCanLoadMore(false);
//                }
                layoutManager.loadingFinished();
                Log.e(TAG, "onResponse: item Count "+articleAdapter.getItemCount() );
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.toString() );
            }
        });
    }

    private void getMoreArticles(long page,long limit){
        articleService.getArticles(page,limit).enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                List<Article> articles= response.body().getArticle();
                articleAdapter.addMoreItems(articles);
                if(articles.size()>=limit){
                    articleAdapter.setCanLoadMore(true);
                }else{
                    articleAdapter.setCanLoadMore(false);
                }
                layoutManager.loadingFinished();
                Log.e(TAG, "onResponse: item Count "+articleAdapter.getItemCount() );
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.toString() );
            }
        });
    }


    //call back of Article Adapter

    @Override
    public void onDelete(Article article, int pos) {
        articleService.deleteArticle(article.getId()).enqueue(new Callback<DeleteArticleResponse>() {
            @Override
            public void onResponse(Call<DeleteArticleResponse> call, Response<DeleteArticleResponse> response) {
                if(response.body().getCode().equals("0000")){
                    articleAdapter.remove(article,pos);
                }else{
                    Toast.makeText(MainActivity.this, "fail to delete.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteArticleResponse> call, Throwable t) {

            }
        });
    }

   int updateItemPos;
    @Override
    public void onEdit(Article article, int pos) {
        updateItemPos=pos;
        Intent intent=new Intent(this,EditArticleActivity.class);
        intent.putExtra("article",article);
        intent.putExtra("category",article.getCategory());
        intent.putExtra("author",article.getAuthor());
        startActivityForResult(intent,EDIT_CODE_REQUEST);

    }

    public void onDownload(View view){
        DownloadAsyncTask downloadAsyncTask=new DownloadAsyncTask(this);
        downloadAsyncTask.execute();
    }



    private static final String TAG = "MainActivity";
}
