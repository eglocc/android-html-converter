package com.ergizgizer.htmlconverter;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.nodes.Document;

/**
 * A simple {@link Fragment} subclass.
 */
public class HTMLConverterFragment extends Fragment implements LoaderManager.LoaderCallbacks<Document> {

    private static final String LOG_TAG = HTMLConverterFragment.class.getSimpleName();

    private static final int LOADER_ID = 1994;

    public HTMLConverterFragment() {
        // Required empty public constructor
    }

    public static HTMLConverterFragment newInstance() {

        Bundle args = new Bundle();

        HTMLConverterFragment fragment = new HTMLConverterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_htmlconverter, container, false);

        return view;
    }

    @Override
    public Loader<Document> onCreateLoader(int id, Bundle args) {
        return new AssetLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<Document> loader, Document document) {
        UIManager.getInstance().updateUI(getContext(), getView(), document);
    }

    @Override
    public void onLoaderReset(Loader<Document> loader) {

    }
}
