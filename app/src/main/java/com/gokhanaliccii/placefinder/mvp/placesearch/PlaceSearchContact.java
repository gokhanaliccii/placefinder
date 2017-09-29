package com.gokhanaliccii.placefinder.mvp.placesearch;

import android.location.Location;

import com.gokhanaliccii.placefinder.model.ResponseVenues;
import com.gokhanaliccii.placefinder.mvp.BasePresenter;
import com.gokhanaliccii.placefinder.mvp.BaseView;

/**
 * Created by gokhan on 04/02/17.
 */

public interface PlaceSearchContact {

    interface View extends BaseView {

        void showSearchResult(String title, ResponseVenues response);

        void showPlaceSearchingPopup();

        void showWarnUserInput(String warnText);

        void showTypeLocationForcelyPopup();

        void showNeedGpsPopup();

        void showApiFailError();

    }

    interface Presenter<V extends BaseView> extends BasePresenter<View> {

        void searchPlace(String type, String where, Location location);
    }


}
