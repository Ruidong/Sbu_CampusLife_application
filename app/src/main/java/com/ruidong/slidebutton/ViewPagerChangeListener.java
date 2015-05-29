package com.ruidong.slidebutton;

/**
 * Created by Ruidong on 5/27/2015.
 */
import com.example.ruidong.sbu_application.NavigationActivity;
import com.example.ruidong.sbu_application.POI;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


import android.support.v4.view.ViewPager.OnPageChangeListener;

public class ViewPagerChangeListener implements OnPageChangeListener {




    @Override
    public void onPageSelected(int position) {

        POI currentPoi= NavigationActivity.bottomHash2.get(position);

        LatLng  latlng =new LatLng (currentPoi.getmLatitude(),currentPoi.getmLongitude());
        NavigationActivity.map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 14));
        Marker marker=NavigationActivity.mMarkersHashMap2.get(currentPoi);

        marker.showInfoWindow();

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