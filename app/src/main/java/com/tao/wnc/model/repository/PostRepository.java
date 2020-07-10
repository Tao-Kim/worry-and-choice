package com.tao.wnc.model.repository;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tao.wnc.model.domain.PostItem;

public class PostRepository {

    private static final String TAG = PostRepository.class.getName();
    private FirebaseDatabase db;
    private DatabaseReference ref;

    public PostRepository() {
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();
    }

    public void readPostsList(){

    }

    public void readMyPostsList(){

    }

    public void insertPost(PostItem item){
        ref.child("posts").push().setValue(item);
    }

    public void readPost(){

    }

    public void modifyPost(){

    }

    public void deletePost(){

    }
}
