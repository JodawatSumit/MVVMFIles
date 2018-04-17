package com.mvvm.demo.binding;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.mvvm.demo.R;
import com.mvvm.demo.support.widgets.SnappyRecyclerView;
import com.squareup.picasso.Picasso;

public class BindingHelper {

    @BindingAdapter("background_resource")
    public static void setBackgroundResource(Toolbar toolbar, boolean isToolBarWhite) {
        toolbar.setBackgroundResource(isToolBarWhite ?
                R.color.app_background_white : R.color.app_background_themed);
    }

    @BindingAdapter("setColor")
    public static void setColor(ImageView imageView, String color) {
        try {
            if (!TextUtils.isEmpty(color) && color.contains("#"))
                imageView.setBackgroundColor(Color.parseColor(color.trim()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter("imeListener")
    public static void setImeListener(EditText bottomNavigationView,
                                      TextView.OnEditorActionListener listener) {
        if (listener != null)
            bottomNavigationView.setOnEditorActionListener(listener);
    }

    @BindingAdapter(value = {"center_crop", "load_image", "place_holder"}, requireAll = false)
    public static void loadImage(ImageView view, boolean centerCrop, String imageUrl, Drawable placeHolder) {
        DrawableRequestBuilder<String> requestBuilder = Glide.with(view.getContext())
                .load(imageUrl)
                .placeholder(placeHolder)
                .dontAnimate();
        if (centerCrop) {
            requestBuilder
                    .centerCrop()
                    .into(view);
        } else {
            requestBuilder.into(view);
        }
    }

    @BindingAdapter("src")
    public static void setResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    @BindingAdapter("carousalAdapter")
    public static void bindRecyclerView(SnappyRecyclerView recyclerView,
                                        RecyclerView.Adapter adapter) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                recyclerView.getContext(),
                LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }


    @BindingAdapter("autoFitAdapter")
    public static void autoFitAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.addItemDecoration(new MarginDecoration(recyclerView.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter(value = {"vAdapter", "space"}, requireAll = false)
    public static void setVerticalAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter, final int space) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

                if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                    outRect.bottom = space;
                }

            }
        });
    }

    /**
     * @param recyclerView
     * @param setDivider
     */
    @BindingAdapter("setDivider")
    public static void setDivider(RecyclerView recyclerView, boolean setDivider) {
        if (setDivider) {
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);
        }
    }

    @BindingAdapter("hAdapter")
    public static void setHorizontalAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {

//        SnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


    }

    @BindingAdapter("canEdit")
    public static void canEdit(EditText editText, boolean canEdit) {
        editText.setCursorVisible(canEdit);
        editText.setFocusable(canEdit);
        editText.setFocusableInTouchMode(canEdit);
    }

    @BindingAdapter("showError")
    public static void showError(TextInputLayout textInputLayout, String error) {

        if (error == null)
            textInputLayout.setErrorEnabled(false);
        else {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.getParent().requestChildFocus(textInputLayout, textInputLayout);
        }

        textInputLayout.setError(error);
    }

    @BindingAdapter("imgUrl")
    public static void setImgUrl(final ImageView img, final String url) {

        if (url == null) {
            return;
        }
        Picasso.with(img.getContext()).load(url)
                .into(img);

    }

    @BindingAdapter(value = {"sbanner", "shouldFit"}, requireAll = false)
    public static void setSBanner(final ImageView img, final String url, boolean shouldFit) {

        if (url == null) {
            return;
        }

        if (shouldFit)
            Picasso.with(img.getContext()).load(url)
                    .fit().into(img);
        else
            Picasso.with(img.getContext()).load(url)
                    .into(img);

    }

    @BindingAdapter({"setWebChromeClient"})
    public static void setWebChromeClient(WebView view, WebChromeClient client) {
        view.setWebChromeClient(client);
    }

    @BindingAdapter({"setWebViewClient"})
    public static void setWebViewClient(WebView view, WebViewClient client) {
        view.setWebViewClient(client);
    }

    @BindingAdapter({"loadUrl"})
    public static void loadUrl(WebView view, String url) {
        view.loadUrl(url);
    }

    // TODO: 8/9/17 Added By Raghav Edit here
    @BindingAdapter({"webViewSetting"})
    public static void webViewSetting(WebView view, boolean b) {
        WebSettings settings = view.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    @BindingAdapter("editable")
    public static void editable(EditText editText, boolean editable) {
        editText.setCursorVisible(editable);
        editText.setFocusable(editable);
        editText.setFocusableInTouchMode(editable);
    }

    @BindingAdapter("viewPager")
    public static void viewPager(TabLayout tabLayout, ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager, true);
    }

    @BindingAdapter("select")
    public static void setSelection(View view, boolean isSelected) {

        view.setClickable(true);
        view.setSelected(isSelected);
    }
}
