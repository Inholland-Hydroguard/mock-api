package hydroguard.api.mock.models.account;

import java.util.List;

public class AccountsDTO {
    private List<AccountDTO> accounts;
    private int total;
    private int page;

    public AccountsDTO(List<AccountDTO> accounts, int total, int page) {
        this.accounts = accounts;
        this.total = total;
        this.page = page;
    }

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDTO> accounts) {
        this.accounts = accounts;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}