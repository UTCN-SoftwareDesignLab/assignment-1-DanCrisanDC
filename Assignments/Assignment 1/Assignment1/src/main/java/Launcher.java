import controller.LoginController;

public class Launcher {

    public static void main(String[] args) {

        ComponentFactory componentFactory = ComponentFactory.instance(false);

        new LoginController(componentFactory.getLoginView(),
                            componentFactory.getAuthenticationService(),
                            componentFactory.getAdminController(),
                            componentFactory.getEmployeeController(),
                            componentFactory.getUserRepository());
    }
}
