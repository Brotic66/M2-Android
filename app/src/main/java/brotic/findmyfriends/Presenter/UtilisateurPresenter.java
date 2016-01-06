package brotic.findmyfriends.Presenter;

import brotic.findmyfriends.Service.BroticCommunication;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 06/01/2016
 */
public class UtilisateurPresenter {

    public static void getFriendDetails(int friendId) {

        if (friendId == 0)
            return;

        BroticCommunication com = new BroticCommunication("test");
    }
}
