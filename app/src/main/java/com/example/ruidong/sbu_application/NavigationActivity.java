package com.example.ruidong.sbu_application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ruidong.common.tool.ApiConnector;
import com.ruidong.common.tool.GMapV2Direction;
import com.ruidong.common.tool.GetDirectionsAsyncTask;
import com.ruidong.dailylife.fragment.SbuCategoryResultFragment;
import com.ruidong.dailylife.fragment.SbuDailyLifeCategoryFragment;
import com.ruidong.dailylife.fragment.SbuDailySumView;
import com.ruidong.slidebutton.ViewPagerAdapter;
import com.ruidong.slidebutton.ViewPagerChangeListener;
import com.ruidong.slidingmenu.adapter.NavDrawerItem;
import com.ruidong.slidingmenu.adapter.NavDrawerListAdapter;
import com.ruidong.specific.service.CourseManagmentService;
import com.ruidong.specific.service.GeneralService;
import com.ruidong.specific.service.SbuDailyLifeService;




public class NavigationActivity extends FragmentActivity implements OnMarkerClickListener{



    private static final LatLng STARTINGPOINT = new LatLng(40.915823, -73.121903);
    private static final LatLng DESTINATION = new LatLng(40.915962, -73.126264);
    public static GoogleMap map;
    private SupportMapFragment fragment;
    private LatLngBounds latlngBounds;
    private ImageButton navigationImageButton;
    private ImageButton searchImageButton;
    private Polyline newPolyline;
    private boolean isTravelingToDestination = false;
    private int width, height;
    private LatLng usercurrentlocation;
    private LocationManager locationManager;
    LocationListener locationListener = new locationlistener();
    private EditText editText ;
    private static String location ;
    private static double destinationLat = 40.915962;
    private static double destinationLng = -73.126264;

    private CourseManagmentService courseService= new CourseManagmentService();
    private GeneralService genService= new GeneralService();
    public  static SbuDailyLifeService dailyService=new SbuDailyLifeService();
    public  static Collection<POI> myMarkerCollection;
    private static HashMap<Marker, POI> mMarkersHashMap1 = new HashMap<Marker, POI>();
    public  static HashMap<POI, Marker> mMarkersHashMap2 = new HashMap<POI, Marker>();
    public static boolean showResultListFlag = false;

    private boolean listViewFlag=false;


    public static BottomButton bottomFrag;


    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;


    public static Fragment MenuFragment;
    public static Fragment resultFragment;
    public static Fragment showListFragment;

    public static ViewPager pager;
    public static ViewPagerAdapter mAdapter;
    public static LinearLayout layoutPager;

    public static HashMap<POI, Integer> bottomHash1 = new HashMap<POI, Integer>();
    public static HashMap<Integer, POI> bottomHash2 = new HashMap<Integer, POI>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_navigation);
        getSreenDimanstions();
        InitializeMenu(savedInstanceState);


        layoutPager=(LinearLayout)findViewById(R.id.linear1);
        pager = (ViewPager)findViewById(R.id.bottomButton);
        pager.setOnPageChangeListener(new ViewPagerChangeListener());
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(),null);
        layoutPager.setVisibility(View.INVISIBLE);

                

        //map Initialize
        fragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        map = fragment.getMap();
        map.setMyLocationEnabled(true);
        map.setOnMarkerClickListener(this);




        showListFragment = new ShowListButton();
        FragmentTransaction showTran= getSupportFragmentManager().beginTransaction().add(R.id.showListButton, showListFragment);
        showTran.commit();
        FragmentTransaction hideTran= getSupportFragmentManager().beginTransaction().hide(showListFragment);
        hideTran.commit();

        // Create locationManager and get userlocatoin        
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria= new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location userlocation=locationManager.getLastKnownLocation(bestProvider);
        if(userlocation !=null ){
            locationListener.onLocationChanged(userlocation);
        }
        locationManager.requestLocationUpdates(bestProvider, 100, Criteria.ACCURACY_FINE,locationListener );



        //Main Interface View Initialize
        navigationImageButton = (ImageButton) findViewById(R.id.bNavigation);
        searchImageButton = (ImageButton) findViewById(R.id.bSearch);
        editText = (EditText) findViewById(R.id.et_location);


//		    editText.setOnClickListener(new View.OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					//  Generate user input history listview
//
//				}
//
//		    });

        searchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction Tran= getSupportFragmentManager().beginTransaction().remove(showListFragment);
                Tran.commit();
                genService.genFlag=false;
                dailyService.dailyFlag=false;
                location = editText.getText().toString();

                // Determine which service's poi is relative with user input.
                if( location != null ) {

                    new GetPOIItem(location).execute(new ApiConnector());
                }
                else
                    myMarkerCollection = null;


            }
        });


        // Determine whether the bottomBar or detailedListView should be hidden when user click map.
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng arg0) {

                layoutPager.setVisibility(View.INVISIBLE);


                if(resultFragment!=null)
                {
                    FragmentTransaction hideTran= getSupportFragmentManager().beginTransaction().hide(resultFragment);
                    hideTran.commit();
                    FragmentTransaction showTran= getSupportFragmentManager().beginTransaction().show(showListFragment);
                    showTran.commit();
                }
            }
        });

        // Show the navigation route.
        navigationImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LatLng  DESTINATION =new LatLng (destinationLat,destinationLng);
                LatLng  STARTINGPOINT = usercurrentlocation;

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(STARTINGPOINT, 14));
                if (!isTravelingToDestination)
                {

                    findDirections(  STARTINGPOINT.latitude,  STARTINGPOINT.longitude,DESTINATION.latitude, DESTINATION.longitude, GMapV2Direction.MODE_WALKING);
                }

            }
        });
    }

    // Based on the POI collection got from server, add corresponding marker on the map   
    public void addMarker(Collection<POI> myMarkerCollection){
        
        map.clear();
        for(POI PoiElement: myMarkerCollection){
            MarkerOptions option= new MarkerOptions()
                    .position((new LatLng(PoiElement.getmLatitude(), PoiElement.getmLongitude())))
                    .title(PoiElement.getPoiLabel());
            Marker currentMarker = map.addMarker(option);
            //Put the Marker and corresponding poiElement in two hashmaps
            mMarkersHashMap1.put(currentMarker, PoiElement); 
            mMarkersHashMap2.put(PoiElement, currentMarker);

            LatLng  destination =new LatLng (destinationLat,destinationLng);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, 14));

        }
    }

    // Based on POI collection, set each BottomButton Fragment, two parameters needed for a ButtomButton are Text Inforamtion, which are defined in each service.
    // Then add these BottomButton into a list, initialize the ViewPager
    public  void setBottomButtonFragmentList(Collection<POI> myMarkerCollection){
        ArrayList<BottomButton> fragmentlists = new ArrayList<BottomButton>();
        for(POI poiElement : myMarkerCollection){
            BottomButton bottom = BottomButton.newInstance(dailyService.firstTextInfo(poiElement), dailyService.secondTextInfo(poiElement));
            fragmentlists.add(bottom);
            bottomHash1.put(poiElement, fragmentlists.indexOf(bottom));
            bottomHash2.put(fragmentlists.indexOf(bottom),poiElement );
            bottom.setPOI(poiElement);
        }
        mAdapter.bottomButtonList=fragmentlists;
        pager.setAdapter(mAdapter);
    }

    private void InitializeMenu(Bundle savedInstanceState){
        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
        // Pages


        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);



                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item

        }

    }

    private class SlideMenuClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            displayView(position);
        }
    }
    private void displayView(int position) {
        // update the main content by replacing fragments
        MenuFragment = null;
        switch (position) {
            case 0:

                MenuFragment = new SbuCategoryResultFragment();

                break;
            case 1:
                SbuDailyLifeCategoryFragment fragment=new SbuDailyLifeCategoryFragment();
                MenuFragment = fragment;

                fragment.categories.clear();

                break;
            case 2:
                //fragment = new EventsFragment();
                break;
            case 3:
                // operation about Platform
                break;

            default:
                break;
        }

        if (MenuFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.Category_Container, MenuFragment).commit();
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    // A locationlistener to get the Userlocation based on GPS.
    class locationlistener implements LocationListener{
        @Override
        public  void onLocationChanged(Location location) {
            // TODO Auto-generated method stub
            if(location != null)
            {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                usercurrentlocation = new LatLng(latitude, longitude);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }
    }
    // Define onMarkerClick method,show customized information on bottomBar
    public  boolean onMarkerClick(final Marker marker) {

        POI currentPOI= mMarkersHashMap1.get(marker);
        destinationLat=currentPOI.getmLatitude();
        destinationLng=currentPOI.getmLongitude();


        marker.showInfoWindow();

        setBottomButtonFragment(currentPOI);

        if(showResultListFlag == true)
        {
            FragmentTransaction hideTran= getSupportFragmentManager().beginTransaction().hide(resultFragment);
            hideTran.commit();
            FragmentTransaction tran = getSupportFragmentManager().beginTransaction().hide(showListFragment);
            tran.commit();
        }


//        if(genService.genFlag==true)
//        {
//
//        	bottomFrag.setbottomText1(genService.firstTextInfo(currentPOI));
//        	bottomFrag.setbottomText2(genService.secondTextInfo(currentPOI));
//
//        }
//
//        if(dailyService.dailyFlag==true)
//        {
//        	bottomFrag.setbottomText1(dailyService.firstTextInfo(currentPOI));
//        	bottomFrag.setbottomText2(dailyService.secondTextInfo(currentPOI));
//
//        }

        return false ;
    }

    //  When the list of BottomButton is set up, show these BottomButton.
    public void setBottomButtonFragment(POI currentPOI){
        Integer position = bottomHash1.get(currentPOI);
        pager.setCurrentItem(position);
        bottomFrag=(BottomButton) mAdapter.getItem(position);
        layoutPager.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.bNormal:
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.bSatellite:
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.bHybrid:
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.bTerrain:
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private void getSreenDimanstions()
    {
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
    }

    private LatLngBounds createLatLngBoundsObject(LatLng firstLocation, LatLng secondLocation)
    {
        if (firstLocation != null && secondLocation != null)
        {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(firstLocation).include(secondLocation);

            return builder.build();
        }
        return null;
    }

    public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);

        GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this);
        asyncTask.execute(map);
    }

    @Override
    protected void onResume() {

        super.onResume();
        latlngBounds = createLatLngBoundsObject(STARTINGPOINT, DESTINATION);
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));

    }

    public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints) {
        PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.BLUE);

        for(int i = 0 ; i < directionPoints.size() ; i++)
        {
            rectLine.add(directionPoints.get(i));
        }
        if (newPolyline != null)
        {
            newPolyline.remove();
        }
        newPolyline = map.addPolyline(rectLine);

        if (isTravelingToDestination)
        {
            latlngBounds = createLatLngBoundsObject(STARTINGPOINT, DESTINATION);
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 300));
        }

    }


    public void ObtainData(JSONArray jsonArray,String keyword){

        for(int i=0; i<jsonArray.length(); i++){

            JSONObject json = null;
            try{
                json = jsonArray.getJSONObject(i);


                dailyService.storeData(json,keyword);

                System.out.println(".............."+json.getString("POI_Name"));

            } catch (JSONException e){
                e.printStackTrace();
            }
        }


    }

    private class GetPOIItem extends AsyncTask<ApiConnector, Long, JSONArray>{

        private String keyword;

        public GetPOIItem(String str) {
            this.keyword = str;
        }

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            //  it is executed on Background thread

            return params[0].GetPOI(keyword);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray){
        //  After getting the Json data from server, show them in map
            ObtainData(jsonArray,keyword);

            dailyService.dailyFlag=true;
            showResultListFlag=false;

            myMarkerCollection=dailyService.getTargetPOI(keyword);
            setBottomButtonFragmentList(myMarkerCollection);

            //If there are more than one POI related to user keyword, create a list to show these poi.
            if(myMarkerCollection.size()>1){
                showResultListFlag=true;

                resultFragment=new SbuCategoryResultFragment();
                ((SbuCategoryResultFragment) resultFragment).setTargetList(myMarkerCollection);
                System.out.print("myMarkerCollection Size = " + myMarkerCollection.size());

                FragmentTransaction resultTran=getSupportFragmentManager().beginTransaction()
                        .add(R.id.Category_result_Container,resultFragment);
                resultTran.commit();

                showListFragment = new ShowListButton();
                FragmentTransaction showTran= getSupportFragmentManager().beginTransaction().add(R.id.showListButton, showListFragment);
                showTran.commit();
                FragmentTransaction hideTran= getSupportFragmentManager().beginTransaction().hide(showListFragment);
                hideTran.commit();

            }
            addMarker(myMarkerCollection);

            myMarkerCollection.clear();


        }

    }

}
