package com.example.googlemapstest;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//OnMapClickListener 를 사용하기위한 import
//MarkerOption들을 모두 사용할 수 있는 import


public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback {
    //구글 맵을 사용할 mMap 객체
    GoogleMap mMap;
    //위도 경도 텍스트 박스
    TextView Lat;
    TextView Lng;
    Button write;
    //onCreate 메서드
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        write = (Button)findViewById(R.id.write);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SaveActivity.class);
                startActivity(intent);
            }
        });
    }

    //화면에 보여지는 지도 표시
    @Override
    public void onMapReady(GoogleMap map) {
        //구글 맵 객체화
        mMap = map;
        //TextView id값 가져 오기
        Lat = (TextView)findViewById(R.id.Lat);
        Lng = (TextView)findViewById(R.id.Lng);
        //구글 맵을 노멀 사이즈로 들고화면에 표시
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //임의의 좌표 줌
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.53753 , 126.96558),14));
        //★★ 화면 클릭 했을때 리스너 설정
        mMap.setOnMapClickListener(new OnMapClickListener() {
            @Override
            //클릭 했을때...
            public void onMapClick(LatLng latLng){
                //SEOUL 변수에 좌표값 초기화 하기
                //latLng.latitude  = double 형 위도
                //latLng.longitude  = double 형 경도
                LatLng SEOUL = new LatLng(latLng.latitude , latLng.longitude);

                //클릭한 좌표에 마커 표시하기
                //마커를 표시할 객체 생성
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(SEOUL);
                //마커의 좌표값 가져와 포지션값 text박스에 넣기
                Lat.setText(""+String.format("%.2f",markerOptions.getPosition().latitude));
                Lng.setText(""+String.format("%.2f",markerOptions.getPosition().longitude));

                //클릭한 곳에 마커 view하기
                mMap.addMarker(markerOptions);
                //클릭한 곳에 위치 이동하기
                mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
                //zoom 메서드로 매개변수는 줌 배수를 의미한다.
                mMap.animateCamera(CameraUpdateFactory.zoomTo(9));

            }
        });
    }

}