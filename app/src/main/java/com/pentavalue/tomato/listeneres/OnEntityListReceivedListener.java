package com.pentavalue.tomato.listeneres;

import java.util.List;

/**
 * @author Mahmoud Turki
 */
public interface OnEntityListReceivedListener<T> extends UiListener {
    public void onSuccess(List<T> obj);
}
