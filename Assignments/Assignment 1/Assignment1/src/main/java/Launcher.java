import controller.LoginController;
import view.LoginView;
import controller.StartController;
import view.StartView;

public class Launcher {

    public static void main(String[] args) {
        ComponentFactory componentFactory = ComponentFactory.instance(false);
        //new LoginController(new LoginView(), componentFactory.getAuthenticationService());
        new StartController(new StartView(), componentFactory.getAuthenticationService());

    }
}
