package com.pk.instaworld.fragmants;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.pk.instaworld.Post;
import com.pk.instaworld.PostsAdapter;
import com.pk.instaworld.R;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;


public class HomeFragment extends Fragment {
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> feedPosts;
    public static final String TAG = "HomeFragment";
    private SwipeRefreshLayout swipeContainer;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvPosts = view.findViewById(R.id.rvPosts);
        // super.onViewCreated(view, savedInstanceState);

        feedPosts = new ArrayList<>();
        // create the adapter
        adapter = new PostsAdapter(getContext(), feedPosts);

        // create the data source

        // set the adapter on the recycler view
        rvPosts.setAdapter(adapter);

        // set the layout manager on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                 adapter.clear();
                 queryPosts();
                 adapter.addAll(feedPosts);
                 swipeContainer.setRefreshing(false);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,

                android.R.color.holo_green_light,

                android.R.color.holo_orange_light,

                android.R.color.holo_red_light);


    }

    protected void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e != null) {
                    Log.i(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                feedPosts.addAll(objects);
                adapter.notifyDataSetChanged();

                for(int i = 0; i < objects.size(); i++) {
                    Post post = objects.get(i);
                    Log.d(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
            }
        });

    }
}
