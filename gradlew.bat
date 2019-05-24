<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:card_view="http://schemas.android.com/apk/res-auto"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">



    <android.support.v7.widget.CardView
        android:layout_width="match_parent"
        android:elevation="2dp"

        card_view:cardUseCompatPadding="true"

        android:layout_height="wrap_content">
        <LinearLayout

            android:orientation="horizontal"
            android:layout_width="match_parent"
            android:weightSum="10"
            android:layout_height="wrap_content">
    
    <TextView

        android:textSize="20dp"
        android:layout_gravity="center"
        android:textStyle="bold"
        android:gravity="center"
        android:paddingTop="5dp"
        android:paddingBottom="5dp"
        android:layout_width="0dp"
        android:layout_weight="9"
        android:layout_height="wrap_content"
        android:id="@+id/list_title"/>
    
    <ImageView
        android:padding="6dp"
        android:src="@drawable/doublearrow"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />

        </LinearLayout>
    </android.support.v7.widget.CardView>

</LinearLayout>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           