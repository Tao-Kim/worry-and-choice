package com.tao.wnc.model.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tao.wnc.model.domain.PostItem;
import com.tao.wnc.util.Constants;
import com.tao.wnc.util.SingleLiveEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostRepository {

    private static final String TAG = PostRepository.class.getName();
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private MutableLiveData<List<PostItem>> postsListLiveData;
    private List<PostItem> postsList;
    private MutableLiveData<List<PostItem>> myPostsListLiveData;
    private List<PostItem> myPostsList;
    private MutableLiveData<PostItem> postItemLiveData;
    private SingleLiveEvent<Boolean> doneLiveData;

    public PostRepository() {
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();
        postsListLiveData = new MutableLiveData<>();
        postsList = new ArrayList<>();
        myPostsListLiveData = new MutableLiveData<>();
        myPostsList = new ArrayList<>();
        postItemLiveData = new MutableLiveData<>();
        doneLiveData = new SingleLiveEvent<>();
    }

    public MutableLiveData<List<PostItem>> getPostsListLiveData() {
        return postsListLiveData;
    }

    public MutableLiveData<List<PostItem>> getMyPostsListLiveData() {
        return myPostsListLiveData;
    }

    public MutableLiveData<PostItem> getPostItemLiveData() {
        return postItemLiveData;
    }

    public MutableLiveData<Boolean> getDoneLiveData() {
        return doneLiveData;
    }

    public void readPostsList() {
        if (postsList != null) {
            postsList.clear();
        }
        ref.child("posts").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemDataSnapshot : dataSnapshot.getChildren()) {
                    PostItem item = itemDataSnapshot.getValue(PostItem.class);
                    item.setPostId(itemDataSnapshot.getKey());
                    Log.d(TAG, itemDataSnapshot.getKey());
                    postsList.add(item);
                }
                Log.d(TAG, Integer.toString(postsList.size()));
                if (postsList != null && postsList.size() != 0) {
                    Collections.reverse(postsList);
                    postsListLiveData.setValue(postsList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, databaseError.toString());
            }
        });
    }

    public void readMyPostsList() {

    }

    public void insertPost(PostItem item, String userName) {
        final String postId = ref.child("posts").push().getKey();
        ref.child("posts").child(postId).setValue(item);
        ref.child("users").orderByChild("name").equalTo(userName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ref.child("users").child(dataSnapshot.getKey()).child("posts").push().setValue(postId);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, databaseError.toString());
            }
        });
    }

    public void readPost(final String postId, final String userName) {
        ref.child("posts").child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PostItem item = dataSnapshot.getValue(PostItem.class);
                boolean hasSelected = false;

                for (DataSnapshot aDataSnapshot : dataSnapshot.child("selectA").child("selectUsers").getChildren()) {
                    if (userName.equals(aDataSnapshot.getValue())) {
                        hasSelected = true;
                        item.setMySelected(Constants.SELECTED.A_SELECTED);
                        break;
                    }
                }
                if (!hasSelected) {
                    for (DataSnapshot bDataSnapshot : dataSnapshot.child("selectB").child("selectUsers").getChildren()) {
                        if (userName.equals(bDataSnapshot.getValue())) {
                            hasSelected = true;
                            item.setMySelected((Constants.SELECTED.B_SELECTED));
                            break;
                        }
                    }
                }

                if (!hasSelected) {
                    item.setMySelected(Constants.SELECTED.NOT_SELECTED);
                }

                postItemLiveData.setValue(item);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.toString());
            }
        });
    }

    public void modifySelect(final short SELECT, final String postId, final String userName) {
        if (SELECT == Constants.SELECT.WRITER_A) {
            ref.child("posts").child(postId).child("selected").setValue(new Integer(Constants.SELECTED.A_SELECTED));
            doneLiveData.setValue(true);
        } else if (SELECT == Constants.SELECT.WRITER_B) {
            ref.child("posts").child(postId).child("selected").setValue(new Integer(Constants.SELECTED.B_SELECTED));
            doneLiveData.setValue(true);
        } else {
            ref.child("posts").child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long checkCounts = ((long) dataSnapshot.child("checkCounts").getValue()) + 1;
                    if (SELECT == Constants.SELECT.OTHER_A) {
                        long selectACounts = ((long) dataSnapshot.child("selectA").child("count").getValue()) + 1;
                        ref.child("posts").child(postId).child("selectA").child("selectUsers").push().setValue(userName);
                        ref.child("posts").child(postId).child("selectA").child("count").setValue(selectACounts);
                        ref.child("posts").child(postId).child("checkCounts").setValue(checkCounts);
                        doneLiveData.setValue(true);
                    } else if (SELECT == Constants.SELECT.OTHER_B) {
                        long selectBCounts = ((long) dataSnapshot.child("selectB").child("count").getValue()) + 1;
                        ref.child("posts").child(postId).child("selectB").child("selectUsers").push().setValue(userName);
                        ref.child("posts").child(postId).child("selectB").child("count").setValue(selectBCounts);
                        ref.child("posts").child(postId).child("checkCounts").setValue(checkCounts);
                        doneLiveData.setValue(true);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, databaseError.toString());
                }
            });

        }


    }

    public void modifyPost() {

    }

    public void deletePost() {

    }
}
