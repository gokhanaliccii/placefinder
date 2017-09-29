package com.gokhanaliccii.placefinder.mvp;

import android.os.Bundle;

/**
 * Created by gokhan on 04/02/17.
 */

public interface BasePresenter<View extends BaseView> {

    void attachView(View view);

    void detachView();

    void storeValues(Bundle bundle);
}