package brotic.findmyfriends.Service.Builders;

import java.util.Objects;

/**
 * @author Brice VICO
 * @date 15/12/2015
 */
public abstract class AbstractBuilder implements IBuilder {

    protected Object obj;

    public Object getObj() {
        return this.obj;
    }
}
