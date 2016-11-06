package com.pentavalue.tomato.listeneres;


import com.pentavalue.tomato.model.User;

/**
 * Listener to provide callbacks with the result of any server method that return void.
 *
 * @author Mahmoud Turki
 */
public interface OnProfileListener extends OnEntityReceivedListener<User> {
}
