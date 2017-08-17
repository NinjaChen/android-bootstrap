package rocks.ninjachen.exoplayer.core;

/**
 * Created by ninja on 3/31/17.
 */
public class UserNotLoginException extends RuntimeException {
    public UserNotLoginException(){
        super("User not login");
    }
}
