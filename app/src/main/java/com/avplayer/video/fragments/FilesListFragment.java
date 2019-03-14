package com.avplayer.video.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avplayer.R;
import com.avplayer.video.adapters.FileListAdapter;
import com.avplayer.video.models.FileInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by shivappar.b on 08-03-2019
 */
public class FilesListFragment extends Fragment {
    public static final String TAG = "FilesListFragment";
    RecyclerView recyclerView;
    FileListAdapter adapter;

    public FilesListFragment() {

    }

    public static FilesListFragment getInstance(String folderPath) {
        FilesListFragment filesFragment = new FilesListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", folderPath);
        filesFragment.setArguments(bundle);
        return filesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folder_list, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String folderName = bundle.getString("path");
            adapter = new FileListAdapter(getVideoList(folderName), getActivity());
            recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    private List<FileInfo> getVideoList(String folderName) {
        List<FileInfo> myList = new ArrayList<>();
        File file = new File(folderName);
        File[] list = file.listFiles();
        for (File aList : list) {
            if (aList.getName().toLowerCase(Locale.getDefault()).endsWith(".mp4")) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileName(aList.getName());
                fileInfo.setFilePath(aList.getPath());
                myList.add(fileInfo);
            }
        }

        Collections.sort(myList, new Comparator<FileInfo>() {
            @Override
            public int compare(FileInfo o1, FileInfo o2) {
                return o1.getFileName().compareToIgnoreCase(o2.getFileName());
            }
        });

        return myList;
    }
}
