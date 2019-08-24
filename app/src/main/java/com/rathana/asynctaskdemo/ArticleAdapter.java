package com.rathana.asynctaskdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rathana.asynctaskdemo.model.Article;
import com.rathana.asynctaskdemo.model.Author;
import com.rathana.asynctaskdemo.model.Category;
import com.rathana.asynctaskdemo.util.DateFormatter;

import org.w3c.dom.Text;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private List<Article> articles;
    private Context context;

    public ArticleAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Article article= articles.get(i);
        Category category=article.getCategory();
        Author author= article.getAuthor();

        viewHolder.title.setText(article.getTitle()!=null ? article.getTitle(): "");
        viewHolder.author.setText(author.getName()!=null? author.getName():"");
        viewHolder.date.setText(DateFormatter.format(article.getCreatedDate()));
        viewHolder.category.setText(category.getName()!=null ? category.getName():"");
        //image
        //todo bind image to image View
        if(article.getImage()!=null){
            Glide.with(context)
                    .load(article.getImage())
                    .override(250,160)
                    .error(R.drawable.ic_picture)
                    .placeholder(R.drawable.ic_picture)
                    .into(viewHolder.thumb);
        }else{
            viewHolder.thumb.setImageResource(R.drawable.ic_picture);
        }

        //setEvent
        viewHolder.btnDel.setOnClickListener(v-> {
            if(callback!=null)
                callback.onDelete(article,viewHolder.getAdapterPosition());
        });

        viewHolder.btnEdit.setOnClickListener(v->{
            callback.onEdit(article,viewHolder.getAdapterPosition());
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context)
                .inflate(R.layout.article_item_layout,viewGroup,false);

        return new ViewHolder(view);
    }

    public void addMoreItems(List<Article> articles){
        int previousSize= this.articles.size();
        this.articles.addAll(articles);
        notifyItemRangeInserted(previousSize,articles.size());
    }

    public void remove(Article article,int pos){
        this.articles.remove(article);
        notifyItemRemoved(pos);
    }
    public void update(Article article,int pos){
        this.articles.set(pos,article);
        notifyItemChanged(pos);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title ,date,author,category;
        ImageView thumb,btnFavorite,btnDel,btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            date=itemView.findViewById(R.id.date);
            author=itemView.findViewById(R.id.author);
            category=itemView.findViewById(R.id.category);
            thumb=itemView.findViewById(R.id.thumb);
            btnFavorite=itemView.findViewById(R.id.favorite);

            btnDel=itemView.findViewById(R.id.btnDel);
            btnEdit=itemView.findViewById(R.id.btnedit);
        }
    }

    private ArticleCallback callback;

    public void setCallback(ArticleCallback callback) {
        this.callback = callback;
    }

    public interface ArticleCallback{
        void onDelete(Article article, int pos);
        void onEdit(Article article, int pos);
    }
}
