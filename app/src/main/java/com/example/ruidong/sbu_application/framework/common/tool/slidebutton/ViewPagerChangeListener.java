package com.example.ruidong.sbu_application.framework.common.tool.slidebutton;

/**
 * Created by Ruidong on 5/27/2015.
 */
import com.example.ruidong.sbu_application.framework.NavigationActivity;
import com.example.ruidong.sbu_application.framework.POI;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


import android.support.v4.view.ViewPager.OnPageChangeListener;

public class ViewPagerChangeListener implements OnPageChangeListener {
    private NavigationActivity activity;

    public ViewPagerChangeListener(NavigationActivity activity){
        this.activity=activity;
    }


    @Override
    public void onPageSelected(int position) {

        POI currentPoi = NavigationActivity.bottomHash2.get(position);

        activity.setBottomFragWhenSlide(position);

        LatLng latlng = currentPoi.getPosition();
        NavigationActivity.map.animateCamera(CameraUpdateFactory.newLatLng(currentPoi.getPosition()));
        Marker marker = NavigationActivity.mMarkersHashMap2.get(currentPoi);

        if (marker != null) {
            activity.setClickedClusterItem(currentPoi);
            marker.showInfoWindow();

        }

    }




    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }


}