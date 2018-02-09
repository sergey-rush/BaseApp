package ru.baseapp;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import ru.baseapp.core.AppContext;
import ru.baseapp.core.Section;
import ru.baseapp.data.DataAccess;
import ru.baseapp.views.MainActivity;
import ru.baseapp.web.SectionProvider;
import ru.baseapp.web.WebContext;

/**
 * Created by rash on 06.02.2018.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
