package com.gokhanaliccii.placefinder.mvp.placesearch;

import android.location.Location;

import com.gokhanaliccii.placefinder.model.ResponseVenues;
import com.gokhanaliccii.placefinder.mvp.BasePresenter;
import com.gokhanaliccii.placefinder.mvp.BaseView;

/**
 * Created by gokhan on 04/02/17.
 */

public interface PlaceSearchContact {

    public interface View extends BaseView {

        public void showSearchResult(String title,ResponseVenues response);

        public void showPlaceSearchingPopup();

        public void showWarnUserInput(String warnText);

        public void showTypeLocationForcelyPopup();

        public void showNeedGpsPopup();
        public void showApiFailError();

    }

    public interface Presenter extends BasePresenter {

        public void searchPlace(String type, String where, Location location);
    }


}
