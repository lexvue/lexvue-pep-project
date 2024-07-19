package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        AccountDAO accountDAO = new AccountDAO();
    }

    /**
     * Constructor for a AccountService when a AccountDAO is provided.
     * This is used for when a mock AccountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AccountService independently of AccountDAO.
     * There is no need to modify this constructor.
     * @param AccountDAO
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /**
     * TODO: Use the AccountDAO to add a new Account to the database.
     *
     * This method should also return the added Account. A distinction should be made between *transient* and
     * *persisted* objects - the *transient* Account Object given as the parameter will not contain the Account's id,
     * because it is not yet a database record. When this method is used, it should return the full persisted Account,
     * which will contain the Account's id. This way, any part of the application that uses this method has
     * all information about the new Account, because knowing the new Account's ID is necessary. This means that the
     * method should return the Account returned by the AccountDAO's insertAccount method, and not the Account provided by
     * the parameter 'Account'.
     *
     * @param Account an object representing a new Account.
     * @return the newly added Account if the add operation was successful, including the Account_id. We do this to
     *         inform our provide the front-end client with information about the added Account.
     */
    public Account addAccount(Account Account){
        //return accountDAO.insertAccount(Account);
    }

    /**
     * TODO: Use the AccountDAO to update an existing Account from the database.
     * You should first check that the Account ID already exists. To do this, you could use an if statement that checks
     * if AccountDAO.getAccountById returns null for the Account's ID, as this would indicate that the Account id does not
     * exist.
     *
     * @param Account_id the ID of the Account to be modified.
     * @param Account an object containing all data that should replace the values contained by the existing Account_id.
     *         the Account object does not contain a Account ID.
     * @return the newly updated Account if the update operation was successful. Return null if the update operation was
     *         unsuccessful. We do this to inform our application about successful/unsuccessful operations. (eg, the
     *         user should have some insight if they attempted to edit a nonexistent Account.)
     */
    public Account updateAccount(int Account_id, Account Account){
        AccountDAO.updateAccount(Account_id, Account);
        return AccountDAO.getAccountById(Account_id);
    }

    /**
     * TODO: Use the AccountDAO to retrieve a List containing all Accounts.
     * You could use the AccountDAO.getAllAccounts method.
     *
     * @return all Accounts in the database.
     */
    public List<Account> getAllAccounts() {
        return AccountDAO.getAllAccounts();
    }

    /**
     * TODO: Use the AccountDAO to retrieve a List containing all Accounts departing from a certain city and arriving at
     * some other city. You could use the AccountDAO.getAllAccountsFromCityToCity method.
     *
     * @param departure_city the departing city of the Account.
     * @param arrival_city the arriving city of the Account.
     * @return all Accounts departing from departure_city and arriving at arrival_city.
     */
    public List<Account> getAllAccountsFromCityToCity(String departure_city, String arrival_city) {
        return AccountDAO.getAllAccountsFromCityToCity(departure_city, arrival_city);
    }
}
