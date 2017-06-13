package com.example.moryta.boundservices;

import android.os.Binder;

/**
 * Created by logonrm on 12/06/2017.
 */

public class ServiceBinder extends Binder {
    private BoundService boundService;

    public ServiceBinder(BoundService boundService) {
        this.boundService = boundService;
    }

    public BoundService getService() {
        return this.boundService;
    }
}
