package com.pentavalue.tomato.listeneres;

/**
 * @author Mahmoud Turki
 */
public interface OnEntityReceivedListener<T> extends UiListener{
    public void onSuccess(T obj);
}
