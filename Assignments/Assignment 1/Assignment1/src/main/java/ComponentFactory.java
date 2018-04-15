import controller.AdminController;
import controller.EmployeeController;
import controller.LoginController;
import database.DBConnectionFactory;
import repository.report.ReportRepository;
import repository.report.ReportRepositoryMySQL;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.report.ReportService;
import service.report.ReportServiceImpl;
import service.user.AdminService;
import service.user.AdminServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;

public class ComponentFactory {
    private final AuthenticationService authenticationService;
    private final AdminService adminService;
    private final ReportService reportService;
    private final ClientService clientService;
    private final AccountService accountService;

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    private final AdminView adminView;
    private final EmployeeView employeeView;
    private final LoginView loginView;

    private final AdminController adminController;
    private final EmployeeController employeeController;
    private final LoginController loginController;

    private static ComponentFactory instance;

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();

        this.reportRepository = new ReportRepositoryMySQL(connection);
        this.clientRepository = new ClientRepositoryMySQL(connection);
        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection);

        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.reportService = new ReportServiceImpl(this.reportRepository);
        this.adminService = new AdminServiceImpl(this.userRepository, this.authenticationService);
        this.clientService = new ClientServiceImpl(this.clientRepository);
        this.accountService = new AccountServiceImpl(this.accountRepository);

        this.adminView = new AdminView();
        this.employeeView = new EmployeeView();
        this.loginView = new LoginView();

        this.adminController = new AdminController(this.adminView,this.adminService, this.authenticationService, this.reportService);
        this.employeeController = new EmployeeController(this.employeeView, this.accountService, this.clientService, this.reportService);
        this.loginController = new LoginController(this.loginView,this.authenticationService,this.adminController,this.employeeController,this.userRepository);

    }

    public AdminController getAdminController() {
        return adminController;
    }

    public EmployeeController getEmployeeController() {
        return employeeController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public AdminView getAdminView() {
        return adminView;
    }

    public EmployeeView getEmployeeView() {
        return employeeView;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public ReportService getReportService() {
        return reportService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public ReportRepository getReportRepository() {
        return reportRepository;
    }

    public static ComponentFactory getInstance() {
        return instance;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }
}
