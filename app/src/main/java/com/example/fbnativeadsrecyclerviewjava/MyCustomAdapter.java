package com.example.fbnativeadsrecyclerviewjava;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;

import java.util.ArrayList;
import java.util.List;

public class MyCustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "MyCustomAdapter";
    private Context context;
    private List<MainModel> objectList;
    private static int ITEM_TYPE = 0;
    private static int ADS_TYPE = 1;
    //adapter consturctor
    public MyCustomAdapter(Context context, List<MainModel> objectList) {
        this.context = context;
        this.objectList = objectList;
    }
    //this is to ccheck the view type
    @Override
    public int getItemViewType(int position) {

        //her checking, if the list contains a null object,
        if (objectList.get(position) ==null) {

            //it conatins null, so we are returning the AD view
            return ADS_TYPE;
        } else {
            //here it doesn't contain null, so we are returning the regular item view type
            return ITEM_TYPE;
        }
    }
    @Override
    public int getItemCount() {

        //return the list size only. no need to do any steps....
        return objectList.size();
    }
    //adpater Oncreate method to check the view type
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //chec if it is a Ads types
        if (viewType == ADS_TYPE) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            //this view will be displayed when there is not ad available.
            View adLayoutOutline = inflater
                    .inflate(R.layout.item_facebook_native_ad_outline, parent, false);
            ViewGroup vg = adLayoutOutline.findViewById(R.id.ad_container);

            NativeAdLayout adLayoutContent = (NativeAdLayout) inflater
                    .inflate(R.layout.facebook_native_ad_item, parent, false);
            vg.addView(adLayoutContent);
            return new AdViewHolder(adLayoutOutline);
            //here it is ad type, so we are inflating the native ad item
//            NativeAdLayout adLayoutContent = (NativeAdLayout) LayoutInflater.from(context)
//                    .inflate(R.layout.facebook_native_ad_item, parent, false);
//
//
//            return new AdViewHolder(adLayoutContent);
        } else {

            //here it is the item, so return regular item layout
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_item, parent, false));
        }
    }
    //here we set the data to the layout views
    //first check item view type
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // here checking if it is an item type
        if (getItemViewType(position)==ITEM_TYPE) {
            //so set data from list item
            MainModel mainModel = (MainModel) objectList.get(position);
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder .textView.setText(mainModel.getTitle());
            myViewHolder.textView2.setText(mainModel.getSubtitle());
            myViewHolder.textView3.setText(mainModel.getName());
            myViewHolder.textView4.setText(mainModel.getNumber());
        } else
        {
            //here it is not item, so set native ad to the ad item
            //below is the custom method to add date to the native, ad
            //you can do all the code, here,
            //but i am doing in a method, so code does not look complex for you
            onBindAdViewHolder(holder);
        }
    }
    //this is a custom method to add data to the ad, to reduce complexity in OnBind method
    //here add data to the layout views,
    public void onBindAdViewHolder(final RecyclerView.ViewHolder holder) {
        final AdViewHolder adHolder = (AdViewHolder) holder;

            //instantiate a NativeAd object, create a NativeAdListener, and call loadAd() with the ad listener:
            //here create a new native ad with your NAtive Ad id
            //see the official documentation


            //https://developers.facebook.com/docs/audience-network/guides/ad-formats/native/android/

            // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
            // now, while you are testing and replace it later when you have signed up.
            // While you are using this temporary code you will only get test ads and if you release
            // your code like this to the Google Play your users will not receive ads (you will get a no fill error).

            final NativeAd nativeAd = new NativeAd(adHolder.itemView.getContext(),"YOUR_PLACEMENT_ID " );
//            final NativeAd nativeAd = new NativeAd(adHolder.itemView.getContext(),"YOUR_PLACEMENT_ID" );


            //create the ad listeners here
            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                    // Native ad finished downloading all assets
                    Log.e(TAG, "Native ad finished downloading all assets.");
//                    adHolder.nativeAdContainer.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Native ad is loaded and ready to be displayed
                    Log.d(TAG, "Native ad is loaded and ready to be displayed!");

                    //here checking the ad, if not that type, we don not inflate the views
                    if (ad != nativeAd) {
                        return;
                    }

                    //here start inflate the views with data from native ads

                    // Add the AdOptionsView
                    AdOptionsView adChoicesView = new AdOptionsView(adHolder.itemView.getContext(), nativeAd, (NativeAdLayout) adHolder.nativeAdContainer);
                    adHolder.adChoicesContainer.removeAllViews();
                    adHolder.adChoicesContainer.addView(adChoicesView,0);

                    // Set the Text.
                    adHolder.nativeAdTitle.setText(nativeAd.getAdvertiserName());
                    adHolder.nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
                    adHolder.nativeAdBody.setText(nativeAd.getAdBodyText());
                    adHolder.nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
                    adHolder.nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
                    adHolder.nativeSponsoredLabel.setText(nativeAd.getSponsoredTranslation());

                    // Create a list of clickable views
                    List<View> clickableViews = new ArrayList<>();
                    clickableViews.add(adHolder.nativeAdTitle);
                    clickableViews.add(adHolder.nativeAdCallToAction);
                    nativeAd.registerViewForInteraction(
                            adHolder.nativeAdContainer,
                            adHolder.nativeAdMedia,
                            adHolder.nativeAdIcon,
                            clickableViews);
                    // Register the Title and CTA button to listen for clicks.
                    adHolder.nativeAdContainer.setVisibility(View.VISIBLE);
                    //here ad is loaded, change the boolean, for next ad
                 }

                @Override
                public void onAdClicked(Ad ad) {
                    // Native ad clicked
                    Log.d(TAG, "Native ad clicked!");
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Native ad impression
                    Log.d(TAG, "Native ad impression logged!");
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    // Native ad failed to load
                    Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());

                    //now, here do not show the container, so hide it
                    adHolder.nativeAdContainer.setVisibility(View.GONE);
                }
            };

            // Request an ad
            // here , load the add and add the listener to native ad
            nativeAd.loadAd(nativeAd.buildLoadAdConfig()
                    .withAdListener(nativeAdListener)
                    .build());


    }
    //you can have as many as view holders
    //this is our custom view holder for model class
    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView, textView2,textView3, textView4;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView= itemView.findViewById(R.id.textView);
            textView2= itemView.findViewById(R.id.textView2);
            textView3= itemView.findViewById(R.id.textView3);
            textView4= itemView.findViewById(R.id.textView4);
        }
    }

    //this the custom view holder for native ad
    private static class AdViewHolder extends RecyclerView.ViewHolder {
        MediaView nativeAdIcon;
        TextView nativeAdTitle;
        MediaView nativeAdMedia;
        TextView nativeAdSocialContext;
        TextView nativeAdBody;
        Button nativeAdCallToAction;
        LinearLayout adChoicesContainer;
        NativeAdLayout nativeAdContainer;
        TextView nativeSponsoredLabel;



        AdViewHolder(View view) {
            super(view);
            nativeAdContainer = (NativeAdLayout) view.findViewById(R.id.fb_native_ad_container);
            nativeAdIcon = (MediaView) view.findViewById(R.id.native_ad_icon);
            nativeAdTitle = (TextView) view.findViewById(R.id.native_ad_title);
            nativeAdMedia = (MediaView) view.findViewById(R.id.native_ad_media);
            nativeAdSocialContext = (TextView) view.findViewById(R.id.native_ad_social_context);
            nativeAdBody = (TextView) view.findViewById(R.id.native_ad_body);
            nativeAdCallToAction = (Button) view.findViewById(R.id.native_ad_call_to_action);
            adChoicesContainer = (LinearLayout) view.findViewById(R.id.ad_choices_container);
            nativeSponsoredLabel = (TextView) view.findViewById(R.id.sponsored_label);
         }


    }

}