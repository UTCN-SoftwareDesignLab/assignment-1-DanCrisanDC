import controller.AdminController;
import controller.EmployeeController;
import controller.LoginController;
import database.DBConnectionFactory;
import repository.ReportRepositoryMySQL;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepositoryMySQL;
import service.account.AccountServiceImpl;
import service.client.ClientServiceImpl;
import service.report.ReportServiceImpl;
import service.user.AdminServices;
import service.user.AuthenticationServiceMySQL;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;

public class Launcher {

    public static void main(String[] args) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(false).getConnection();
        ComponentFactory componentFactory = ComponentFactory.instance(false);
        LoginView loginView = new LoginView();
        new LoginController(loginView, componentFactory.getAuthenticationService(),
                            new AdminController(new AdminView(),
                                                new UserRepositoryMySQL(connection),
                                                new AdminServices(new UserRepositoryMySQL(connection)),
                                                new AuthenticationServiceMySQL(new UserRepositoryMySQL(connection),
                                                                               new RightsRolesRepositoryMySQL(connection)),
                                                new ReportRepositoryMySQL(connection)),
                            new EmployeeController(new EmployeeView(),
                                                   new AccountServiceImpl(new AccountRepositoryMySQL(connection)),
                                                   new ClientServiceImpl(new ClientRepositoryMySQL(connection)),
                                                   new ReportServiceImpl(new ReportRepositoryMySQL(connection)),
                                                   loginView),
                            new UserRepositoryMySQL(connection));
    }
}
