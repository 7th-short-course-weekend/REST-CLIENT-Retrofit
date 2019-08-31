package com.rathana.asynctaskdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.rathana.asynctaskdemo.data.ServiceGenerator;
import com.rathana.asynctaskdemo.data.service.ArticleService;
import com.rathana.asynctaskdemo.data.service.CategoryService;
import com.rathana.asynctaskdemo.model.Article;
import com.rathana.asynctaskdemo.model.ArticleForm;
import com.rathana.asynctaskdemo.model.ArticleFormResponse;
import com.rathana.asynctaskdemo.model.Author;
import com.rathana.asynctaskdemo.model.AuthorResponse;
import com.rathana.asynctaskdemo.model.Category;
import com.rathana.asynctaskdemo.model.CategoryResponse;
import com.rathana.asynctaskdemo.model.UploadImageResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditArticleActivity extends AppCompatActivity {

    EditText title,desc;
    Spinner spCategory,spAuthor;
    Button btnSave;

    ImageView thumbnail;
    ProgressBar progressBar;
    List<Author> authors=new ArrayList<>();
    List<String> authorArray=new ArrayList<>();
    List<Category> categories=new ArrayList<>();
    List<String> categoriesArray=new ArrayList<>();

    ArrayAdapter<String> authorAdapter;
    ArrayAdapter<String> categoryAdapter;

    CategoryService categoryService;
    ArticleService articleService;

    private int categoryId;
    private int authorId;


    static final int PICK_IMAGE_CODE=2;
    Article article;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_article);

        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        thumbnail =findViewById(R.id.thumb);
        spAuthor=findViewById(R.id.spAuthor);
        spCategory=findViewById(R.id.spCategory);
        btnSave=findViewById(R.id.btnSave);

        progressBar=findViewById(R.id.progressBar);

        categoryService= ServiceGenerator.createService(CategoryService.class);
        articleService=ServiceGenerator.createService(ArticleService.class);


        //bind data to from
        if(getIntent()!=null){
            article=getIntent().getParcelableExtra("article");
            Log.e(TAG, "onCreate: "+article);
            if(article!=null){
                article.setCategory(getIntent().getParcelableExtra("category"));
                article.setAuthor(getIntent().getParcelableExtra("author"));

                title.setText(article.getTitle());
                desc.setText(article.getDescription());

            }
        }

        setupUI();

        requestExternalStorage();
        thumbnail.setOnClickListener(v->{
            //pick Image for gallery
            Intent intent =new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/**");
            startActivityForResult(intent,PICK_IMAGE_CODE);

        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_CODE && resultCode==RESULT_OK){
            try {
                Uri uri=data.getData();
                String[] columnInfo={MediaStore.Images.Media.DATA};
                Cursor cursor= getContentResolver().query(
                    uri,
                    columnInfo,
                    null,
                    null,
                    null
                );
                cursor.moveToFirst();
                int columnIndex= cursor.getColumnIndex(columnInfo[0]);
                String imagePath= cursor.getString(columnIndex);
                cursor.close();
                Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
                thumbnail.setImageBitmap(bitmap);

                //todo upload image to server
                uploadImage(imagePath);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    private  String imageUrl;
    private void uploadImage(String image){
        progressBar.setVisibility(View.VISIBLE);
        File file = new File(image);
        RequestBody requestBody= RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part part= MultipartBody.Part
                .createFormData("FILE",file.getAbsolutePath(),requestBody);

        articleService.uploadImage(part).enqueue(new Callback<UploadImageResponse>() {
            @Override
            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                //get Image url
                imageUrl = response.body().getUrl();
                Log.e(TAG, "onResponse: "+imageUrl);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.toString() );
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setupUI(){

        //categorySpinner
        getCategories();
        getAuthors();

        authorAdapter=new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                authorArray
        );
        categoryAdapter=new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                categoriesArray
        );

        spAuthor.setAdapter(authorAdapter);
        spCategory.setAdapter(categoryAdapter);

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryId=categories.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spAuthor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                authorId=authors.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //save article
        btnSave.setOnClickListener(v->{
            ArticleForm articleForm=new ArticleForm();
            articleForm.setTitle(title.getText().toString());
            articleForm.setDescription(desc.getText().toString());
            if(imageUrl!=null)
                articleForm.setImage(imageUrl);
            else
                articleForm.setImage("http://api-ams.me:80/image-thumbnails/thumbnail-3eba00b6-e40f-425d-93be-9177c0c6f31b.jpg");
            articleForm.setAuthor(authorId);
            articleForm.setCategoryId(categoryId);
            articleForm.setStatus("true");
            //save article to api
            articleService.create(articleForm).enqueue(new Callback<ArticleFormResponse>() {
                @Override
                public void onResponse(Call<ArticleFormResponse> call, Response<ArticleFormResponse> response) {
                    try {
                        Article article= response.body().getArticle();
                        if(article!=null){
                            Intent intent=new Intent();
                            intent.putExtra("article",article);
                            intent.putExtra("category",article.getCategory());
                            intent.putExtra("author",article.getAuthor());
                            setResult(RESULT_OK,intent);
                            finish();
                        }

                    }catch (Exception e){

                    }
                }

                @Override
                public void onFailure(Call<ArticleFormResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: "+t.toString() );
                }
            });

        });
    }

    private void getCategories(){
        categoryService.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                try{
                    categories.addAll(response.body().getCategories());
                    for(Category category: categories)
                        categoriesArray.add(category.getName());

                    categoryAdapter.notifyDataSetChanged();

                    for(int i=0;i<categoriesArray.size();i++){
                        Log.e(TAG, "setupUI: "+categoriesArray.get(i) );
                        if(categoriesArray.get(i).equals(article.getCategory().getName())){
                            //Log.e(TAG, "setupUI: "+categoriesArray.get(i) );
                            spCategory.setSelection(i);
                            break;
                        }
                    }
                    categoryId=categories.get(0).getId();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.toString() );
            }
        });
    }

    private void getAuthors(){
        Map<String,Long> params=new HashMap<>();
        params.put("page",1l);
        params.put("limit",100l);
        categoryService.getAuthors(params).enqueue(new Callback<AuthorResponse>() {
            @Override
            public void onResponse(Call<AuthorResponse> call, Response<AuthorResponse> response) {
                try {
                    authors.addAll(response.body().getAuthors());
                    for (Author author: authors)
                        authorArray.add(author.getName());
                    authorAdapter.notifyDataSetChanged();
                    authorId=authors.get(0).getId();

                    for(int i=0;i<authorArray.size();i++){
                        Log.e(TAG, "setupUI: "+authorArray.get(i) );
                        if(authors.get(i).getId()==article.getAuthor().getId()){
                            //Log.e(TAG, "setupUI: "+categoriesArray.get(i) );
                            spAuthor.setSelection(i);
                            break;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AuthorResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.toString() );
            }
        });
    }

    private static final String TAG = "AddArticleActivity";

    private void requestExternalStorage(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},99);
        }

    }

}


