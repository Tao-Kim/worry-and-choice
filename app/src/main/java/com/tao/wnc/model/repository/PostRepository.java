package com.tao.wnc.model.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tao.wnc.model.domain.CommentItem;
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
    private List<CommentItem> commentList;
    private MutableLiveData<List<CommentItem>> commentListLiveData;
    private SingleLiveEvent<Short> doneLiveData;

    public PostRepository() {
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();
        postsListLiveData = new MutableLiveData<>();
        postsList = new ArrayList<>();
        myPostsListLiveData = new MutableLiveData<>();
        myPostsList = new ArrayList<>();
        postItemLiveData = new MutableLiveData<>();
        commentList = new ArrayList<>();
        commentListLiveData = new MutableLiveData<>();
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

    public MutableLiveData<List<CommentItem>> getCommentListLiveData() {
        return commentListLiveData;
    }

    public MutableLiveData<Short> getDoneLiveData() {
        return doneLiveData;
    }

    public void readPostsList() {
        if (postsList.size() != 0) {
            postsList.clear();
        }
        ref.child("posts").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemDataSnapshot : dataSnapshot.getChildren()) {
                    PostItem item = itemDataSnapshot.getValue(PostItem.class);
                    item.setPostId(itemDataSnapshot.getKey());
                    postsList.add(item);
                }
                if (postsList != null && postsList.size() != 0) {
                    Collections.reverse(postsList);
                    postsListLiveData.setValue(postsList);
                } else {
                    postsListLiveData.setValue(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, databaseError.toString());
            }
        });
    }

    public void readMyPostsList(String userName) {
        if (myPostsList.size() != 0) {
            myPostsList.clear();
        }
        ref.child("posts").orderByChild("writer").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemDataSnapshot : dataSnapshot.getChildren()) {
                    PostItem item = itemDataSnapshot.getValue(PostItem.class);
                    item.setPostId(itemDataSnapshot.getKey());
                    myPostsList.add(item);
                }
                if (myPostsList != null && myPostsList.size() != 0) {
                    Collections.reverse(myPostsList);
                    myPostsListLiveData.setValue(myPostsList);
                } else {
                    myPostsListLiveData.setValue(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, databaseError.toString());
            }
        });

    }

    public void insertPost(PostItem item) {
        ref.child("posts").push().setValue(item);
    }

    public void modifyPost(String postId, String title, String description, String selectAString, String selectBString) {
        ref.child("posts").child(postId).child("title").setValue(title);
        ref.child("posts").child(postId).child("description").setValue(description);
        ref.child("posts").child(postId).child("selectA").child("name").setValue(selectAString);
        ref.child("posts").child(postId).child("selectB").child("name").setValue(selectBString);
    }

    public void deletePost(String postId) {
        ref.child("posts").child(postId).setValue(null);
    }

    public void readPost(final String postId) {
        ref.child("posts").child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PostItem item = dataSnapshot.getValue(PostItem.class);
                postItemLiveData.setValue(item);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.toString());
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
                Log.w(TAG, databaseError.toString());
            }
        });
    }

    public void modifySelect(final short SELECT, final String postId, final String userName) {
        if (SELECT == Constants.SELECT.WRITER_A) {
            ref.child("posts").child(postId).child("selected").setValue(new Integer(Constants.SELECTED.A_SELECTED));
            doneLiveData.setValue(Constants.DB.DONE_SELECT);
        } else if (SELECT == Constants.SELECT.WRITER_B) {
            ref.child("posts").child(postId).child("selected").setValue(new Integer(Constants.SELECTED.B_SELECTED));
            doneLiveData.setValue(Constants.DB.DONE_SELECT);
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
                        doneLiveData.setValue(Constants.DB.DONE_SELECT);
                    } else if (SELECT == Constants.SELECT.OTHER_B) {
                        long selectBCounts = ((long) dataSnapshot.child("selectB").child("count").getValue()) + 1;
                        ref.child("posts").child(postId).child("selectB").child("selectUsers").push().setValue(userName);
                        ref.child("posts").child(postId).child("selectB").child("count").setValue(selectBCounts);
                        ref.child("posts").child(postId).child("checkCounts").setValue(checkCounts);
                        doneLiveData.setValue(Constants.DB.DONE_SELECT);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, databaseError.toString());
                }
            });
        }
    }

    public void readCommentList(String postId) {
        if (commentList.size() != 0) {
            commentList.clear();
        }
        ref.child("posts").child(postId).child("comments").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot commentDataSnapshot : dataSnapshot.getChildren()) {
                    CommentItem item = commentDataSnapshot.getValue(CommentItem.class);
                    commentList.add(item);
                }
                if (commentList != null && commentList.size() != 0) {
                    commentListLiveData.setValue(commentList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, databaseError.toString());
            }
        });
    }

    public void insertComment(String postId, CommentItem item) {
        ref.child("posts").child(postId).child("comments").push().setValue(item);
        ref.child("posts").child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long commentCounts = ((long) dataSnapshot.child("commentCounts").getValue()) + 1;
                ref.child("posts").child(postId).child("commentCounts").setValue(commentCounts);
                doneLiveData.setValue(Constants.DB.DONE_SEND_COMMENT);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, databaseError.toString());
            }
        });
    }

}
