/*
 * Copyright (c) 2014. William Mora
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gamestudio24.cityescape;

import android.app.Application;
import com.gamestudio24.cityescape.android.R;
import com.google.android.gms.analytics.GoogleAnalytics;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GoogleAnalytics.getInstance(this).newTracker(R.xml.app_tracker_config);
    }

}
