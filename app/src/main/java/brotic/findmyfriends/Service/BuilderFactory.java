package brotic.findmyfriends.Service;

import android.content.Intent;

import java.util.ArrayList;

import brotic.findmyfriends.Service.Builders.DemandeBuilder;
import brotic.findmyfriends.Service.Builders.IBuilder;
import brotic.findmyfriends.Service.Builders.UserBuilder;


/**
 * @author Brice VICO
 * @date 15/12/2015
 */
public class BuilderFactory {

    public static IBuilder create(String name) {
        IBuilder builder = null;

        switch (name) {
            case "user":
                builder =  new UserBuilder();
                break;
            case "demand":
                builder = new DemandeBuilder();
                break;
        }

        return builder;
    }
}
